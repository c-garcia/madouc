(ns madouc.services.events
  (:require [madouc.db :as db]
            [clj-time.core :as t]
            [clj-http.client :as http]
            [taoensso.timbre :as log]
            [cheshire.core :as json]))

(def results (agent {}))

(defn add-authorization-header
  [headers token]
  (assoc headers :authorization (format "token %s" token)))

(defn add-if-none-match-header
  [headers etag]
  (if etag
    (assoc headers :etag etag)
    headers))

(defn github-events
  [token etag]
  (letfn [(next-query-time [hold]
            (log/infof "Holding for %d" hold)
            (t/plus (t/now) (t/seconds hold)))]
    (let [headers (-> {}
                      (add-authorization-header token)
                      (add-if-none-match-header etag))
          query-res (http/get "https://api.github.com/events" {:accept :json
                                                               :headers headers})]
      {:status (:status query-res)
       :body (json/parse-string (:body query-res))
       :etag (get-in query-res [:headers :etag])
       :hold-until (some->
                    (get-in query-res [:headers :x-poll-interval] "60")
                    Integer/parseInt
                    next-query-time)})))
    
(defn get-and-update-events!
  [res username token]
  (let [hold-until (get-in res [username :hold-until] (t/epoch))
        etag (get res :etag)]
    (log/infof "Holding until: %s" (str hold-until))
    (if (t/after? (t/now) hold-until)
      (do 
        (log/infof "Not holding because %s is later than %s " (str (t/now)) (str hold-until))
        (let [github-res (github-events token etag)]
          (case (:status github-res)
            200 (-> res
                    (assoc-in [username :events] (:body github-res))
                    (assoc-in [username :etag] (:etag github-res))
                    (assoc-in [username :hold-until] (:hold-until github-res)))
            304 (-> res
                    (assoc-in [username :events] [])
                    (assoc-in [username :hold-until] (:hold-until github-res))))))
      (assoc-in res [username :events] []))))

(defn push-event?
  [ev]
  (= "PushEvent" (get ev "type")))
  
(defn xform-event
  [ev]
  (letfn [(find-head [commits sha]
            (first (filter (fn [x] (= sha (get x "sha"))) commits)))]
    (let [commits (get-in ev ["payload" "commits"])
          head-sha (get-in ev ["payload" "head"])
          head-commit (find-head commits head-sha)
          message (get head-commit "message" "Unknown message")
          author (get-in head-commit ["author" "email"] "Unknown author")]
      {:timestamp (get ev "created_at")
       :message message
       :repo (get-in ev ["repo" "name"])
       :author author
       :files[]})))
  
(defn get-batch
  [username]
  (if-let [token (some-> (db/find-user-by-username (db/main-db) {:username username}) :token)]
    (->> (get-in @(send-off results get-and-update-events! username token) [username :events] [])
         (filter push-event?)
         (mapv xform-event))
    (throw (ex-info "User has no token" {:reason :no-token}))))

(ns madouc.services.user
  (:require [madouc.db :as db]
            [madouc.config :refer [env]]))


(defn get-info
  [username]
  (let [user (db/find-user-by-username (db/main-db) {:username username})]
    (some-> user
            (assoc :authorized (not (nil? (:token user))))
            (dissoc :token)
            (dissoc :password)
            (dissoc :id))))
            
(defn get-all-info
  [username]
  (let [user (db/find-user-by-username (db/main-db) {:username username})]
    (some-> user
            (assoc :authorized (not (nil? (:token user)))))))

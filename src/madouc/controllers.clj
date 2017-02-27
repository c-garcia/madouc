(ns madouc.controllers
  (:require [ring.util.http-response :as http-resp]
            [taoensso.timbre :as log]
            [madouc.services.login :refer [valid-user?]]
            [madouc.services.token :as token]
            [madouc.services.user :as user]
            [madouc.services.events :as events]
            [clj-time.core :as t]))

(defn login-controller
  [login-req]
  (let [username (:username login-req)
        password (:password login-req)]
    (if (valid-user? username password)
      (http-resp/ok {:token (token/new username (t/now))})
      (http-resp/unauthorized {:error "Unauthorized"}))))

(defn user-controller
  [req]
  (let [username (get-in req [:identity :sub])]
    (if username
      (http-resp/ok (user/get-info username))
      (http-resp/unauthorized {:error "Unauthorized"}))))

(defn events-controller
  [req]
  (let [username (get-in req [:identity :sub])]
    (if username
      (try
        (http-resp/ok (events/get-batch username))
        (catch Exception e
          (case (-> (ex-data e) :reason)
            :no-token (http-resp/not-found "Token not found")
            (http-resp/internal-server-error (.getMessage e)))))
      (http-resp/unauthorized {:error "Unauthorized"}))))

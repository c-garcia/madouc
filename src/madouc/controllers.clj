(ns madouc.controllers
  (:require [ring.util.http-response :as http-resp]
            [taoensso.timbre :as log]
            [madouc.services.login :refer [valid-user?]]
            [madouc.services.token :as token]
            [clj-time.core :as t]))

(defn login-controller
  [login-req]
  (let [username (:username login-req)
        password (:password login-req)]
    (if (valid-user? username password)
      (http-resp/ok {:token (token/new username (t/now))})
      (http-resp/unauthorized {:error "Unauthorized"}))))
    


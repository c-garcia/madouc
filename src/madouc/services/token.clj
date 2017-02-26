(ns madouc.services.token
  (:require [buddy.sign.jwt :as jwt]
            [clj-time.core :as t]
            [madouc.config :refer [env]]))

(defn new
  [username start-time]
  (let [resp-token {:sub username :exp (t/plus (t/now)(t/hours 5))}]
    (jwt/sign resp-token (env :jwt-secret))))
  

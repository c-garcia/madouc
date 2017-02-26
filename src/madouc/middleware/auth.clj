(ns madouc.middleware.auth
  (:require [mount.core :refer [defstate]]
            [buddy.auth.backends :as backends]
            [madouc.config :refer [env]]))

(defstate auth-backend :start (backends/jws {:secret (env :jwt-secret) :token-name "Bearer"}))

(ns madouc.middleware
  (:require [madouc.middleware.auth :refer [auth-backend]]))

(defn make-auth-middleware
  []
  auth-backend)

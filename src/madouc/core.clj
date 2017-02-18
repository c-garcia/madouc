(ns madouc.core
  (:require [immutant.web :as server]
            [madouc.handlers :as app]
            [environ.core :refer [env]]
            [taoensso.timbre :as timbre])
  (:gen-class))

(defn -main 
  [& args]
  (timbre/info "Starting server...")
  (server/run app/entrypoint))

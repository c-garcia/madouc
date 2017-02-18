(ns madouc.core
  (:require [immutant.web :as server]
            [madouc.handlers :as app]
            [environ.core :refer [env]]
            [taoensso.timbre :as timbre])
  (:gen-class))


(defn stop-server
  []
  (timbre/info "Stopping server...")
  (server/stop)
  (timbre/info "Server stopped"))

(defn -main 
  [& args]
  (.addShutdownHook (Runtime/getRuntime) (Thread. #(stop-server)))
  (timbre/info "Starting server...")
  (server/run app/entrypoint))

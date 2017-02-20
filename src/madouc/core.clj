(ns madouc.core
  (:require [ring.logger.timbre :as logger]
            [immutant.web :as server]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.stacktrace :refer [wrap-stacktrace]]
            [madouc.handlers :as app]
            [environ.core :refer [env]]
            [taoensso.timbre :as timbre]
            [selmer.parser :refer [set-resource-path!]])
  (:gen-class))


(defn stop-server
  []
  (timbre/info "Stopping server...")
  (server/stop)
  (timbre/info "Server stopped"))

(defn wrap-with-dev
  [orig-ep]
  (if (= (env :madouc-env "dev"))
    (do
      (timbre/info "Server started with *development* configuration")
      (-> orig-ep
          wrap-reload
          wrap-stacktrace))
    orig-ep))

(defn make-entrypoint
  [handler]
  (-> handler
      logger/wrap-with-logger
      wrap-with-dev))

(defn -main 
  [& args]
  (.addShutdownHook (Runtime/getRuntime) (Thread. #(stop-server)))
  (timbre/info "Starting server...")
  (set-resource-path! (clojure.java.io/resource "templates"))
  (let [ep (make-entrypoint app/main-handler)]
    (server/run ep)))

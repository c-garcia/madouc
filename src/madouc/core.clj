(ns madouc.core
  (:require [ring.logger.timbre :as logger]
            [immutant.web :as server]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.stacktrace :refer [wrap-stacktrace]]
            [taoensso.timbre :as timbre]
            [selmer.parser :refer [set-resource-path!]]
            [mount.core :as mount :refer [defstate]]
            [cprop.core :refer [load-config]]
            [madouc.handlers :as app]
            [madouc.config :refer [env]])
  (:gen-class))


(defn- wrap-with-dev
  [orig-ep]
  (if (= (env :madouc-env "dev"))
    (do
      (timbre/info "Server started with *development* configuration")
      (-> orig-ep
          wrap-reload
          wrap-stacktrace))
    orig-ep))

(defn- make-entrypoint
  [handler]
  (-> handler
      logger/wrap-with-logger
      wrap-with-dev))

(defn- start-server
  []
  (timbre/info "Starting server...")
  (let [ep (make-entrypoint app/main-handler)]
    (server/run ep)))

(defn- stop-server
  []
  (timbre/info "Stopping server...")
  (server/stop)
  (timbre/info "Server stopped"))

(defstate ^{:on-reload :noop} http-server
  :start (start-server)
  :stop (stop-server))

(defn -main 
  [& args]
  (.addShutdownHook (Runtime/getRuntime) (Thread. #(mount/stop)))

  (set-resource-path! (clojure.java.io/resource "templates"))
  (mount/start))

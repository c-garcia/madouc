(ns madouc.handlers
  (:require [ring.logger.timbre :as logger]))


(defn main-handler
  [request]
  (let [client (:remote-addr request)
        msg (format "Hello, there %s!!!", client)]
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body msg}))

(def entrypoint (logger/wrap-with-logger main-handler))

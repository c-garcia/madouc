(ns madouc.handlers)

(defn entrypoint
  [request]
  (let [client (:remote-addr request)
        msg (format "Hello, there %s!!!", client)]
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body msg}))

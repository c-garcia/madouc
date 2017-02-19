(ns madouc.handlers
  (:require [ring.logger.timbre :as logger]
            [madouc.handlers.content :as content]
            [compojure.core :refer :all]))


(def main-handler (routes content/handler))

(def entrypoint (logger/wrap-with-logger main-handler))

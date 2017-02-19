(ns madouc.handlers
  (:require [ring.logger.timbre :as logger]
            [madouc.handlers.content :as content]
            [madouc.handlers.api :as api]
            [compojure.core :refer :all]))


(def main-handler (routes api/handler content/handler))

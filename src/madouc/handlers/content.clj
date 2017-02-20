(ns madouc.handlers.content
  (:require [selmer.parser :refer [render-file]]
            [compojure.core :refer :all]
            [compojure.route :as route]))

(defroutes handler
  (GET "/" [] (render-file "index.html" {}))
  (route/resources "/")
  (route/not-found "404 Not found"))

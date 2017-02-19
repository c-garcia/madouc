(ns madouc.handlers.content
  (:require [ring.util.response :as response]
            [compojure.core :refer :all]
            [compojure.route :as route]))

(defroutes handler
  (GET "/" [] (response/redirect "index.html"))
  (route/resources "/")
  (route/not-found "404 Not found"))

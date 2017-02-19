(ns madouc.handlers.api
  (:require [ring.util.http-response :as http-resp]
            [compojure.api.sweet :refer :all]))

(defapi handler
  (context "/api" []
           (GET "/status" [] (http-resp/ok {:api-status "ok"}))))

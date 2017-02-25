(ns madouc.handlers.api
  (:require [ring.util.http-response :as http-resp]
            [compojure.api.sweet :refer :all]
            [madouc.db :as db]))

(defapi handler-old
  (context "/api" []
    (GET "/status" [] (http-resp/ok {:api-status "ok"}))))


(def handler
  (api
   {:swagger {:ui "/api-docs/"
              :spec "/api/swagger.json"
              :data {:info {:title "Madouc API"
                            :description "API for the Madouc service"
                            :version "0.1.3"}}}}
   (context "/api" []
    (GET "/status" [] (http-resp/ok {:api-status "ok"})))))
            
   

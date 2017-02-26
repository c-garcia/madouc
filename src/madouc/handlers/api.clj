(ns madouc.handlers.api
  (:require [ring.util.http-response :as http-resp]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [madouc.db :as db]
            [madouc.controllers :refer :all]))

(defapi handler-old
  (context "/api" []
    (GET "/status" [] (http-resp/ok {:api-status "ok"}))))

(s/defschema LoginReq {:username String :password String})
(s/defschema Token {:token String})
(s/defschema APIError {:error String})

(def handler
  (api
   {:swagger {:ui "/api-docs/"
              :spec "/api/swagger.json"
              :data {:info {:title "Madouc API"
                            :description "API for the Madouc service"
                            :version "0.1.3"}}}}
   (context "/api" []
     (GET "/status" [] (http-resp/ok {:api-status "ok"}))
     (POST "/login" []
       :responses {401 {:schema APIError :description "Unauthorized"}}
       :return Token
       :body [req LoginReq]
       (login-controller req)))))
            
   

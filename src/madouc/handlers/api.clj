(ns madouc.handlers.api
  (:require [ring.util.http-response :as http-resp]
            [schema.core :as s]
            [compojure.api.sweet :refer :all]
            [compojure.api.meta :refer [restructure-param]]
            [buddy.auth.backends :as backends]
            [buddy.auth.middleware :refer [wrap-authentication]]
            [taoensso.timbre :as log]
            [mount.core :refer [defstate]]
            [madouc.config :refer [env]]
            [madouc.db :as db]
            [madouc.controllers :refer :all]
            [madouc.middleware :refer [make-auth-middleware]])
 (:import [org.joda.time DateTime]))

(s/defschema LoginReq {:username String :password String})
(s/defschema Token {:token String})
(s/defschema APIError {:error String})
(s/defschema UserInfo {:username String :personal_name String :authorized Boolean})
(s/defschema EventInfo {:timestamp DateTime
                        :message String
                        :repo String
                        :files [String]
                        :author String})
(s/defschema EventList [EventInfo])


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
       (login-controller req))
     (GET "/user" req
       :middleware [[wrap-authentication (make-auth-middleware)]]
       :responses {401 {:schema APIError :description "Unauthorized"}}
       :return UserInfo
       (user-controller req))
     (GET "/events" req
       :middleware [[wrap-authentication (make-auth-middleware)]]
       :responses {401 {:schema APIError :description "Unauthorized"}
                   404 {:schema APIError :description "User has no token"}}
       :return EventList
       (events-controller req)))))

(ns madouc.db
  (:require [mount.core :refer [defstate]]
            [hikari-cp.core :refer [make-datasource close-datasource]]
            [hugsql.core :as hugsql]
            [madouc.config :refer [env]]))

(defn- make-pool-spec
  [host db user pass]
  {:jdbc-url (format "jdbc:postgresql://%s/%s" host db)
   :username user
   :password pass})

(defstate con
  :start (make-datasource (make-pool-spec
                           (env :db-host)
                           (env :db-name)
                           (env :db-user)
                           (env :db-password)))
  :stop (close-datasource con))

(defn main-db
  []
  {:datasource con})
  
(hugsql/def-db-fns "sql/queries.sql")

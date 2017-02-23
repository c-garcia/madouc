(ns madouc.db
  (:require [mount.core :refer [defstate]]
            [conman.core :as conman]
            [madouc.config :refer [env]]))

(defn- make-pool-spec
  [host db user pass]
  {:jdbc-url (format "jdbc:postgresql://%s/%s" host db)
   :username user
   :password pass})

(defstate con
  :start (conman/connect! (make-pool-spec
                           (env :db-host)
                           (env :db-name)
                           (env :db-user)
                           (env :db-password)))
  :stop (conman/disconnect! con))

(conman/bind-connection con "sql/queries.sql")
  


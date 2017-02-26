(ns madouc.db.migrator
  (:require [migratus.core :as migratus]
            [madouc.config :refer [env]]))


(defn- config->migratus-params
  [config]
  {:store :database
   :migration-dir "migrations/"
   :db {:classname "org.postgresql.Driver"
        :subprotocol "postgresql"
        :subname (format "//%s/%s" (:db-host config) (:db-name config))
        :user (:db-user config)
        :password (:db-password config)}})
  
(defn migrate!
  []
  (migratus/migrate (config->migratus-params env)))

(defn rollback!
  []
  (migratus/rollback (config->migratus-params env)))

(defn pending-list
  []
  (migratus/pending-list (config->migratus-params env)))

(defn debug-config
  []
  (config->migratus-params env))

(defn create!
  [migration-name]
  (migratus/create (config->migratus-params env) migration-name))
  

(ns madouc.config
  (:require [mount.core :refer [defstate]]
            [cprop.core :refer [load-config]]))

(defstate env :start (load-config))

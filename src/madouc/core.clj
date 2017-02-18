(ns madouc.core
  (:require [immutant.web :as server]
            [madouc.handlers :as app])
  (:gen-class))

(defn -main 
  [& args]
  (server/run app/entrypoint))

(ns madouc.api
  (:require
   [re-frame.core :as rf]))

(defn simulate-events-load
  "Simulates a load of events from external source"
  []
  (rf/dispatch [:simulate-event-load]))

(def interval-id nil)

(defn start-event-pooling []
  (let [id (js/setInterval simulate-events-load 2000)]
    (set! interval-id id)))

(defn stop-event-pooling []
  (js/clearInterval interval-id)
  (set! interval-id nil))

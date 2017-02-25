(ns madouc.api
  (:require
   [re-frame.core :as rf]))

(defn simulate-events-load
  "Simulates a load of events from external source"
  []
  (rf/dispatch [:simulate-event-load]))

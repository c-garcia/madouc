(ns madouc.api
  (:require
   [re-frame.core :as rf]
   [madouc.mock :as mm]))

(defn ^:export simulate-events-load
  "Simulates a load of events from external source"
  []
  (.info js/console "Simulating event")
  (let [events (mm/get-rand-list 20)]
    (rf/dispatch [:events-changed events])))

(def interval-id nil)

(defn fetching? [id]
  (not (nil? id)))

(defn ^:export start-event-fetching []
  (when (nil? interval-id)
    (let [id (js/setInterval simulate-events-load (* 10 1000))
          fetching (fetching? (set! interval-id id))]
      (rf/dispatch [:fetching-changed fetching]))))

(defn ^:export stop-event-fetching []
  (when (not (nil? interval-id))
    (do
      (js/clearInterval interval-id)
      (let [fetching (fetching? (set! interval-id nil))]
        (rf/dispatch [:fetching-changed fetching])))))

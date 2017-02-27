(ns madouc.events
  (:require
   [madouc.api :as api]
   [madouc.mock :as mm]
   [re-frame.core :as rf]))

(rf/reg-event-db
 :initialize
 (fn [_ _]
   (.info js/console "Initialize the db (ratom)")
   {:events []
    :fetching nil}))

(rf/reg-event-db
 :events-changed
 (fn [db [_ events]]
   (.info js/console "Events changed")
   (assoc db :events events)))

(rf/reg-event-db
 :fetching-changed
 (fn [db [_ fetching]]
   (.info js/console "Fetching changed")
   (assoc db :fetching fetching)))

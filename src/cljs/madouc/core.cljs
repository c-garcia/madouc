(ns madouc.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [madouc.mock :as mm]
            [madouc.api]
            [cljs.pprint :refer [pprint]]))

(rf/reg-event-db
 :initialize
 (fn [_ _]
   (.info js/console "Initialize handler")
   (.info js/console "This is working, then")
   {:events (mm/get-rand-list 10)}))

(rf/reg-event-db
 :simulate-event-load
 (fn [_ _]
   {:events (mm/get-rand-list (int (* 10 (rand))))}))

(defn state-inspector
  "Pretty prints the ratom to a string wrapped into a pre tag"
  []
  [:pre (with-out-str (pprint @re-frame.db/app-db))])

(rf/reg-sub
 :events-count
 (fn [db _]
   (count (:events db))))

(rf/reg-sub
 :events-list
 (fn [db _] (:events db)))

(defn events-counter []
  [:span @(rf/subscribe [:events-count])])

(defn event-detail [evt]
  ^{:key (:timestamp evt)}[:li.list-group-item (:message evt)])

(defn events-list []
  [:ul.list-group
   (map event-detail @(rf/subscribe [:events-list]))])

(defn ui []
  [:div.container-fluid
   [:h1 "Events " [events-counter]]
   [:div
    [:button.btn.btn-primary
     {:on-click #(rf/dispatch [:simulate-event-load])}
     "Load events"]]
   [events-list]
   #_[state-inspector]])

(defn ^:export run
  []
  (rf/dispatch-sync [:initialize])
  (reagent/render [ui]
                  (.getElementById js/document "app")))

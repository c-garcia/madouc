(ns madouc.views
  (:require
   [re-frame.core :as rf]
   [cljs.pprint :refer [pprint]]))

(defn state-inspector
  "Pretty prints the ratom to a string wrapped into a pre tag"
  []
  [:pre (with-out-str (pprint @re-frame.db/app-db))])

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
   [:h3 "Fetching? " @(rf/subscribe [:fetching])]
   [:div
    [:button.btn.btn-primary
     {:on-click #(rf/dispatch [:simulate-event-load])}
     "Load events"]
    [:button.btn.btn-default
     {:on-click #(rf/dispatch [:start-fetching])} "Start fetching"]
    [:button.btn.btn-default
     {:on-click #(rf/dispatch [:stop-fetching])} "End fetching"]]
   [events-list]
   #_[state-inspector]])

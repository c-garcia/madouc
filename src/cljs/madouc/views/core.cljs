(ns madouc.views.core
  (:require
   [re-frame.core :as rf]
   [cljs.pprint :refer [pprint]]
   [madouc.views.toggle :refer [fetching-toggle]]
   [madouc.views.inspector :refer [state-inspector]]
   [madouc.views.list :refer [events-list]]))

(defn events-counter []
  (let [cont @(rf/subscribe [:events-count])]
    [:text (str cont)]))

(defn big-counter []
  [:p
   [:span {:style {:font-size "82px"}}
    @(rf/subscribe [:events-count])]
   " events"])

(defn ui []
  [:div.container-fluid
   [:h1 "Github Events"]
   [:div.row
    [:div.col-xs-4
     [big-counter]]]
   [:div.row
    [:div.col-xs-12
     [fetching-toggle]]]
   [:div.row
    [:div.col-xs-12
     [events-list]]]])

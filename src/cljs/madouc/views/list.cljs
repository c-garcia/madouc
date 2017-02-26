(ns madouc.views.list
  (:require [re-frame.core :as rf]
            [cljs.pprint :refer [pprint]]
            [madouc.views.table :refer [commits-table]]))

(defn actor-info [evt]
  [:div
   [:div.row
    [:img {:src (:avatar_url (:actor evt))
           :width "80px"
           :height "80px"}]]
   [:div.row
    [:strong (:login (:actor evt))]]
   [:div.row
    [:span (:created_at evt)]]])

(defn event-list-item [evt]
  (let [repo-name (:name (:repo evt))
        repo-url (str "https://github.com/" repo-name)
        type (:type evt)
        n-commits (count (:commits (:payload evt)))]
    ^{:key (:id evt)}
    [:div.list-group-item
     [:a {:href repo-url
          :target "_blank"}
      [:h5.list-group-item-heading repo-name
       [:span.label.label-default.pull-right type]]]
     [:div.row
      [:div.col-xs-4
       [actor-info evt]]
      [:div.col-xs-8
       [commits-table evt]
       [:p
        [:span.badge n-commits] " commits"]]]]))

(defn events-list []
  [:div.panel.panel-default
   [:div.panel-heading "Events list"]
   [:div.panel-body
    [:div.list-group
     (map event-list-item @(rf/subscribe [:events-list]))]]])

(ns madouc.views
  (:require
   [re-frame.core :as rf]
   [cljs.pprint :refer [pprint]]))


(defn state-inspector
  "Pretty prints the ratom to a string wrapped into a pre tag"
  []
  [:pre (with-out-str (pprint @re-frame.db/app-db))])

(defn events-counter []
  (let [cont @(rf/subscribe [:events-count])]
    [:span.label.label-default (str cont " events")]))

#_(defn event-detail [evt]
  ^{:key (:id evt)}
  [:div.panel.panel-default
   [:div.panel-body
    [:h3 "Event detail"]
    [:ul.list-group
     (map (fn [[k,v]] [:li.list-group-item (str k " -> " v)]) evt)]]])

(defn event-detail [evt]
  ^{:key (:id evt)}
  [:div.list-group-item
   [:span (:id evt)]
   [:span (:type evt)]
   [:span (:name (:repo evt))]
   [:pre (str evt)]])

(defn event-list-item [evt]
  ^{:key (:id evt)}
  [:div.list-group-item
   [:a {:href (str "https://github.com/" (:name (:repo evt)))
        :target "_blank"}
    [:h5.list-group-item-heading (:name (:repo evt))
     [:span.label.label-default.pull-right (:type evt)]]]
   [:div.row
    [:div.col-xs-4
     [:div.row
      [:img {:src (:avatar_url (:actor evt))
             :width "40px"
             :height "40px"}]]
     [:div.row
      [:strong (:login (:actor evt))]]
     [:div.row
      [:span (:created_at evt)]]]
    [:div.col-xs-8
     [:table.table.table-condensed.table-stripped
      [:thead
       [:tr
        [:th "commit"]
        [:th "message"]
        [:th "author"]]]
      [:tbody
       (map (fn [commit] [:tr
                          [:td [:a {:href (:url commit) :target "_blank"} (subs (:sha commit) 0 8)]]
                          [:td (:message commit)]
                          [:td [:a {:href (str "mailto:" (:email (:author commit)))} (:name (:author commit))]]])
            (:commits (:payload evt)))]]
     [:p
      [:span.badge (count (:commits (:payload evt)))]
      " commits"]]]])

(defn events-list []
  [:div.panel.panel-default
   [:div.panel-heading "Events list"]
   [:div.panel-body
    [:div.list-group
     #_(map event-detail @(rf/subscribe [:events-list]))
     (map event-list-item @(rf/subscribe [:events-list]))]]
   [:div.panel-footer
    [events-counter]]])

(defn toggle-on []
  [:div.toggle.btn.btn-primary {:on-click #(rf/dispatch [:start-fetching])
                                :style {:width "172px"}}
   [:div.toggle-group
    [:label.btn.btn-primary.toggle-on "Start fetching events"]
    [:label.btn.btn-default.active.toggle-off "Stop fetching events"]
    [:span.toggle-handle.btn.btn-default]]])

(defn toggle-off []
  [:div.toggle.btn.btn-primary.off {:on-click #(rf/dispatch [:stop-fetching])
                                    :style {:width "172px"}}
   [:div.toggle-group
    [:label.btn.btn-primary.toggle-on "Start fetching events"]
    [:label.btn.btn-default.active.toggle-off "Stop fetching events"]
    [:span.toggle-handle.btn.btn-default]]])

(defn fetching-toggle []
  (fn []
    (let [fetching @(rf/subscribe [:fetching])
          on (not fetching)]
      (if on
        [toggle-on]
        [toggle-off]))))

(defn ui []
  [:div.container-fluid
   [:h1 "Github Events"]
   [fetching-toggle]
   [:div.row
    [:div.col-xs-12
     [events-list]]
    #_[:div.col-xs-12
     [event-detail @(rf/subscribe [:selected-event])]]]
   #_[state-inspector]])

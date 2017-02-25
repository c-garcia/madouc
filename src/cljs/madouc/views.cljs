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

(defn event-detail [evt]
  ^{:key (:timestamp evt)}[:li.list-group-item (:message evt)])

(defn events-list []
  [:div.panel.panel-default
   [:div.panel-heading "Events"]
   [:div.panel-body
    [:ul.list-group
     (map event-detail @(rf/subscribe [:events-list]))]]
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
   [events-list]
   #_[state-inspector]])

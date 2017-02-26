(ns madouc.views.toggle
  (:require
   [re-frame.core :as rf]))

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

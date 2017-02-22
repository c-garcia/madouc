(ns madouc.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]))

(.info js/console "This is working, then")

(rf/reg-event-db
 :initialize
 (fn [_ _]
   (.info js/console "Initialize handler")))

(defn ui []
  [:div
   [:h1 "Hello, world!"]])

(defn ^:export run
  []
  (rf/dispatch-sync [:initialize])
  (reagent/render [ui]
                  (.getElementById js/document "app")))

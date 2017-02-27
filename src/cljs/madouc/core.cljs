(ns madouc.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [madouc.mock :as mm]
            [madouc.api :as api]
            [madouc.subs]
            [madouc.events]
            [madouc.views.core :as views]))

(defn ^:export main
  []
  (rf/dispatch-sync [:initialize])
  (reagent/render [views/ui]
                  (.getElementById js/document "app")))

(main)

(defn ^:export initialize [] (rf/dispatch [:initialize]))

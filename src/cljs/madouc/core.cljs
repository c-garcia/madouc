(ns madouc.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [madouc.mock :as mm]
            [madouc.api :as api]
            [madouc.subs]
            [madouc.events]
            [madouc.views.core :as views]))


(defn ^:export run
  []
  (rf/dispatch-sync [:initialize])
  (reagent/render [views/ui]
                  (.getElementById js/document "app")))

(defn initialize [] (rf/dispatch [:initialize]))

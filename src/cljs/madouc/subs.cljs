(ns madouc.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 :events-count
 (fn [db _]
   (count (:events db))))

(rf/reg-sub
 :events-list
 (fn [db _] (:events db)))

(rf/reg-sub
 :fetching
 (fn [db _] (:fetching db)))

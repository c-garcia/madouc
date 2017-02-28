(ns madouc.views.inspector
  (:require
   [re-frame.core :as rf]
   [cljs.pprint :refer [pprint]]))

(defn state-inspector
  "Pretty prints the ratom to a string wrapped into a pre tag"
  []
  [:pre (with-out-str (pprint @re-frame.db/app-db))])

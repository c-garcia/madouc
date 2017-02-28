(ns madouc.views.table)

(defn table-row [commit]
  (let [sha (:sha commit)
        sha-abbr (subs sha 0 8)
        url (:url commit)
        message (:message commit)
        email (str "mailto:" (:email (:author commit)))
        name (:name (:author commit))]
    ^{:key sha}
    [:tr
     [:td [:a {:href url :target "_blank"} sha-abbr]]
     [:td message]
     [:td [:a {:href email} name]]]))

(defn commits-table [evt]
  (let [commits (:commits (:payload evt))]
    [:table.table.table-condensed.table-stripped
     [:thead
      [:tr
       [:th "commit"]
       [:th "message"]
       [:th "author"]]]
     [:tbody
      (map table-row (:commits (:payload evt)))]]))

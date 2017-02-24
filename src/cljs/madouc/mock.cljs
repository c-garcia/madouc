(ns madouc.mock)

(defn rand-files [n]
  [(str "a-" n)
   (str "b-" n)
   (str "c-" n)
   (str "d-" n)])

(defn rand-event [n]
  {:timestamp n
   :message (str "message " n)
   :repo (str "repo n")
   :files (rand-files n)})

(defn get-rand-list
  [n]
  (map rand-event (range n)))


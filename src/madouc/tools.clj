(ns madouc.tools
  (:require [madouc.db :as db]
            [buddy.core.hash :as hash]
            [buddy.core.codecs :refer :all]))

(defn make-user!
  "Designed to be called from a REPL. Creates a user."
  [username personal-name password]
  (let [hashed-password (-> password hash/sha256 bytes->hex)]
    (db/save-user! (db/main-db) {:username username
                                 :personal-name personal-name
                                 :hashed-password hashed-password})))
    

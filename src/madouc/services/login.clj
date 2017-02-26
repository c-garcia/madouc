(ns madouc.services.login
  (:require [madouc.db :as db]
            [taoensso.timbre :as log]
            [buddy.core.hash :as hash]
            [buddy.core.codecs :refer :all]
            [buddy.core.codecs :refer :all]))


(defn valid-user?
  [username password]
  (let [hashed-password (bytes->hex (hash/sha256 password))
        found-users (db/count-users-with-credentials
                     (db/main-db)
                     {:username username :password hashed-password})]
    (= 1 (:count found-users))))

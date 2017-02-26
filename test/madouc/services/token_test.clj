(ns madouc.services.token-test
  (:require [clojure.test :refer :all]
            [mount.core :as mount]
            [buddy.sign.jwt :as jwt]
            [clj-time.core :as t]
            [madouc.config :refer [env]]
            [madouc.services.token :as token]))

(defn mount-fixture
  [f]
  (mount/start-with-states {#'madouc.config/env {:start (fn [] {:jwt-secret "secret"})}})
  (f)
  (mount/stop))

(use-fixtures :each mount-fixture)

(deftest token-creation-test
  (testing "when a token is created"
    (let [sut (token/new "user" (t/now))
          unsigned (jwt/unsign sut (env :jwt-secret))]
      (is (= "user" (:sub unsigned)) "Contains the correct sub claim")
      (is (contains? unsigned :exp) "Contains an expired claim"))))

(ns poli-auth.user-test
  (:require [clojure.test :refer :all]
            [poli-auth.logic.user :as l-u]
            [poli-auth.db.users :as db]))


(deftest create-user
  (db/install-schema)
  (testing "we can create a new user"
    (is (= (nil? (l-u/create-user {:user/name "Teste" :user/email "user@email.com" :user/id 1 :user/password "123456"})) false)))
  (testing "we can login the created user"
    (is (= (:user/name (l-u/login-user "user@email.com" "123456")) "Teste"))))

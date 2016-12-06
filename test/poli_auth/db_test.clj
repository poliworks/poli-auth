(ns poli-auth.db-test
  (:require [clojure.test :refer :all]
            [poli-auth.db.users :as db]))

(def user {:user/name "Fulano"
           :user/email "fulano@mail.com"
           :user/id 2
           :user/hashed-password "1234"})

(deftest create-user
  (db/install-schema!)
  (testing "we can create and login an user"
    (db/insert-user! user)
    (is (= "fulano@mail.com" (:user/email (db/get-user-by-email "fulano@mail.com"))))))

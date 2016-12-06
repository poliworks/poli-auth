(ns poli-auth.token-test
  (:require [clojure.test :refer :all]
            [poli-auth.logic.token :as token]))

(def user {:id 1
           :name "user"
           :email "user@teste.com"
           :password "1234"})

(deftest create-token
  (testing "we can create a new token given an user and then decode it"
    (let [token (token/create-token user)
          decoded-token (token/decode-token token)]
      (is (= (:user-id decoded-token) 1))
      (is (= (:iss decoded-token) "poli-auth")))))

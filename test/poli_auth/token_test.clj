(ns poli-auth.token-test
  (:require [clojure.test :refer :all]
            [poli-auth.logic.token :as token]))

(def user {:user/id 1
           :user/name "user"
           :user/email "user@teste.com"
           :user/password "1234"})

(deftest create-token
  (testing "we can create a new token given an user and then decode it"
    (let [token (token/create-token user)
          decoded-token (token/decode-token token)]
      (println token)
      (println decoded-token)
      (is (= (:user-id decoded-token) 1))
      (is (= (:iss decoded-token) "poli-auth")))))

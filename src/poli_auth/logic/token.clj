(ns poli-auth.logic.token
  (:require [clj-jwt.core  :refer :all]
            [clj-jwt.key   :refer [private-key public-key]]
            [clj-time.core :refer [now plus days]]))

(def rsa-private-key (private-key "jwtKey.key"))
(def rsa-public-key (public-key "jwtKey.key.pub"))

(defn- create-claim [user]
  {:iss "poli-auth"
   :exp (plus (now) (days 1))
   :iat (now)
   :user-id (:id user)})

(defn create-token [user]
  (-> user create-claim jwt (sign :RS256 rsa-private-key) to-str))

(defn decode-token [str-token]
  (let [decoded-token (-> str-token str->jwt)]
    (and (verify decoded-token :RS256 rsa-public-key) (:claims decoded-token))))

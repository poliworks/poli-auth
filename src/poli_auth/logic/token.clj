(ns poli-auth.logic.token
  (:require [clj-jwt.core :refer :all]
            [clj-jwt.key :refer [private-key public-key]]
            [schema.core :as s]
            [clojure.java.io :as io]
            [clj-time.core :refer [now plus days]]
            [poli-auth.model.user :as m]))

(def rsa-private-key (private-key (io/resource "jwtKey.key")))
(def rsa-public-key (public-key (io/resource "jwtKey.key.pub")))

(s/defn ^:private create-claim :- (s/pred map?)
  [user :- m/User]
  {:iss "poli-auth"
   :exp (plus (now) (days 1))
   :iat (now)
   :user-id (:user/id user)})

(s/defn create-token :- s/Str
  [user :- m/User]
  (-> user create-claim jwt (sign :RS256 rsa-private-key) to-str))

(s/defn decode-token :- (s/maybe (s/pred map?))
  [str-token :- s/Str]
  (let [decoded-token (-> str-token str->jwt)]
    (when (verify decoded-token :RS256 rsa-public-key)
      (:claims decoded-token))))

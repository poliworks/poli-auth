(ns poli-auth.db.users
  (:require [datomic.api :as d]
            [poli-auth.model.user :as m]
            [io.rkn.conformity :as c]
            [schema.core :as s]))

(def prod-uri "datomic:free://localhost:4334/poli-auth")
(def test-uri "datomic:mem://test")

(def datomic-uri (or (System/getenv "datomic_uri") prod-uri))                                  ;; Change here the datomic URI

(d/create-database datomic-uri)
(def conn (d/connect datomic-uri))

(defn install-schema! []
  (let [schema (c/read-resource "schema.edn")]
    (c/ensure-conforms conn schema)))

(s/defn get-user-by-email :- (s/maybe m/User)
  [email :- s/Str]
  (->> (d/q '[:find  ?u
              :in $ ?email
              :where [$ ?u :user/email ?email]] (d/db conn) email)
       ffirst
       (d/pull (d/db conn) '[*])))

(s/defn insert-user! [user :- m/User]
  (try
    @(d/transact conn [(assoc user :db/id (d/tempid :db.part/user))])
    (catch Exception e nil)))

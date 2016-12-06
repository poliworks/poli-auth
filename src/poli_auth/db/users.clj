(ns poli-auth.db.users
  (:require [datomic.api :as d]
            [poli-auth.model.user :as m]
            [schema.core :as s]))

(def prod-uri "datomic:free://localhost:4334/poli-auth")
(def test-uri "datomic:mem://test")

(def datomic-uri test-uri)

(d/create-database datomic-uri)
(def conn (d/connect datomic-uri))

(defn install-schema []
  (->> (slurp "res/schema.edn")
       read-string
       (d/transact conn)
       deref))

(s/defn get-user-by-email :- (s/maybe m/User)
  [email :- s/Str]
  (->> (d/q '[:find  ?u
              :in $ ?email
              :where [$ ?u :user/email ?email]] (d/db conn) email)
       ffirst
       (d/pull (d/db conn) '[*])))

(s/defn insert-user [user :- m/User]
  @(d/transact conn [(assoc user :db/id (d/tempid :db.part/user))]))
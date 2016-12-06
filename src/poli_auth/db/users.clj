(ns poli-auth.db.users
  (:require [datomic.api :as d]))

(def datomic-uri "datomic:free://localhost:4334/poli-auth")
(d/create-database datomic-uri)
(def conn (d/connect datomic-uri))

(defn install-schema []
  (->> (slurp "res/schema.edn")
       read-string
       (d/transact conn)
       deref))

(defn get-user-by-email [email]
  (->> (d/q '[:find ?u
              :where [?u :user/email email]] (d/db conn))
       ffirst
       (d/pull (d/db conn) [*])))

(defn insert-user [user]
  )
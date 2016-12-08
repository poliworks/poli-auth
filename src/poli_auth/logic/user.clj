(ns poli-auth.logic.user
  (:require [crypto.password.bcrypt :as crp]
            [poli-auth.db.users :as db]
            [poli-auth.logic.token :as token]
            [poli-auth.model.user :as m]
            [schema.core :as s]))

(s/defn ^:private hash-user :- m/User
  [user :- m/User]
  (-> (->> user :user/password crp/encrypt (assoc user :user/hashed-password))
      (dissoc :user/password)))

(s/defn ^:private add-token :- m/User
  [user :- m/User]
  (-> (assoc user :user/token (token/create-token user))
      (dissoc :user/hashed-password)
      (dissoc :user/password)))

(s/defn create-user :- m/User
  [user :- m/User]
  (let [hashed-user (hash-user user)]
    (db/insert-user! hashed-user)
    (add-token hashed-user)))

(s/defn login-user :- m/User
  [email :- s/Str password :- s/Str]
  (let [user (db/get-user-by-email email)]
    (when (and (some? (:user/hashed-password user)) (crp/check password (:user/hashed-password user)))
      (add-token user))))
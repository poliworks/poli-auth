(ns poli-auth.logic.user
  (:require [crypto.password.bcrypt :as crp]
            [poli-auth.db.users :as db]
            [poli-auth.logic.token :as token]
            [poli-auth.model.user :as m]
            [schema.core :as s]))

(defn- prepare-user [user-data]
  (-> (->> user-data :user/password crp/encrypt (assoc user-data :user/hashed-password))
      (dissoc :user/password)))

(s/defn create-user :- s/Str
  [user-data :- m/User]
  (let [prepared-user (prepare-user user-data)]
    (db/insert-user prepared-user)
    (-> (assoc prepared-user :user/token (token/create-token prepared-user))
        (dissoc :user/hashed-password))))

(s/defn login-user :- m/User
  [email :- s/Str password :- s/Str]
  (let [user (db/get-user-by-email email)]
    (and (crp/check password (:user/hashed-password user)) (dissoc user :user/hashed-password))))
(ns poli-auth.handlers.auth
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [poli-auth.logic.user :as l-u]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [poli-auth.model.user :as m]
            [schema.core :as s]))

(defn model->external [user]
  (->> (map (fn [[k v]] [(name k) v]) user)
       (into {})))

(defn external->model [user]
  (->> user
       (map (fn [[k v]] [(keyword "user" (name k)) v]))
       (into {})))

(defn get-response-or-error [user error-code error-message]
  (if (nil? user)
    {:status error-code :body {:error error-message}}
    {:status 200 :body (model->external user)}))

(defn login [{:keys [body] :as request}]
  (-> (l-u/login-user (:email body) (:password body))
      (get-response-or-error 403 "Invalid login")))

(defn register [{:keys [body] :as request}]
  (-> (external->model body)
      (select-keys [:user/password :user/email :user/type :user/id])
      (m/coerce-to-user)
      (l-u/create-user)
      (get-response-or-error 400 "user with same email already exists")))

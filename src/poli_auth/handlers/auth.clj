(ns poli-auth.handlers.auth
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [poli-auth.logic.user :as l-u]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [poli-auth.model.user :as m]
            [schema.core :as s]))

(defn model->external [user]
  (if (nil? user)
    {:status 403 :body {:error "Incorrect email or password"}}
    {:status 200 :body (->> (map (fn [[k v]] [(name k) v]) user)
                            (into {}))}))

(defn external->model [user]
  (->> user
       (map (fn [[k v]] [(keyword "user" (name k)) v]))
       (into {})))

(defn login [{:keys [body] :as request}]
  (-> (l-u/login-user (:email body) (:password body))
      (model->external)))

(defn register [{:keys [body] :as request}]
  (-> (external->model body)
      (m/coerce-to-user)
      (l-u/create-user)
      (model->external)))

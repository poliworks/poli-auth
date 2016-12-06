(ns poli-auth.handlers.auth
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [poli-auth.logic.user :as l-u]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [poli-auth.model.user :as m]
            [schema.core :as s]))

(defn debug [val]
  (println val)
  val)

(defn model->external [user]
  (->> (map (fn [[k v]] [(name k) v]) user)
       (into {})))

(defn external->model [user]
  (->> (map (fn [[k v]] [(keyword "user" (name k)) v]) user)
       (into {})))

(defn login [{:keys [body] :as request}]
  {:status 200
   :body   (-> (l-u/login-user (:email body) (:password body))
               model->external)})

(defn register [{:keys [body] :as request}]
  {:body   (-> (external->model body)
               (l-u/create-user)
               (model->external))
   :status 200})

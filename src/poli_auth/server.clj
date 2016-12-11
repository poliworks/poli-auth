(ns poli-auth.server
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [poli-auth.handlers.auth :as h-auth]
            [poli-auth.db.users :as d-u]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [poli-auth.logic.token :as token]))

(defroutes app-routes
  (POST "/login" request (h-auth/login request))
  (POST "/register" request (h-auth/register request))
  (GET  "/pub-key" [] {:body token/rsa-public-key :status 200})
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-json-body {:keywords? true})
      (wrap-json-response)
      (wrap-defaults api-defaults)))

(defn bootstrap! []
  (d-u/install-schema!))

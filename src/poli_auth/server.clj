(ns poli-auth.server
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [poli-auth.handlers.auth :as h-auth]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(defroutes app-routes
  (GET "/" [] h-auth/render-main)
  (POST "/login" request (h-auth/login request))
  (POST "/register" [] "Registre-se aqui")
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-json-body {:keywords? true})
      (wrap-json-response)
      (wrap-defaults api-defaults)))

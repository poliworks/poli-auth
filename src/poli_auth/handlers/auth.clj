(ns poli-auth.handlers.auth
	(:require [compojure.core :refer :all]
           	  [compojure.route :as route]
           	  [ring.util.response :refer [resource-response response]]
              [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def render-main {:status 200
				  :body {:teste "teste, pau no seu cu"}})

(def render-pau (response {:comprimento "50 cm" :raio "20 cm" :local "seu cu"}))

(defn pau-nele [usuario]
	{:status 200 :body {:message (str "Pau No Cu do " usuario)}})


(defn login [request]
	{:status 200 :body request})
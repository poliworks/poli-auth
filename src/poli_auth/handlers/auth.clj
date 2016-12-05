(ns poli-auth.handlers.auth
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def render-main {:status 200
                  :body   {:teste "string here"}})

(defn login [request] {:status 200
                       :body   request})
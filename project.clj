(defproject poli-auth "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring/ring-json "0.4.0"]
                 [com.datomic/datomic-free "0.9.5407"]
                 [ring/ring-defaults "0.2.1"]
                 [clj-jwt "0.1.1"]]
  :plugins [[lein-ring "0.9.7"]]
  :resources-paths ["res/"]
  :ring {:handler poli-auth.server/app
         :init poli-auth.server/setup}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})

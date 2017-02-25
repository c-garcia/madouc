(defproject madouc "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-core "1.5.1"]
                 [ring/ring-devel "1.5.1"]
                 [ring-logger "0.7.7"]
                 [ring-logger-timbre "0.7.5"]
                 [com.taoensso/timbre "4.8.0"]
                 [com.fzakaria/slf4j-timbre "0.3.4"]
                 [compojure "1.5.2"]
                 [metosin/compojure-api "1.1.10"]
                 [cprop "0.1.10"]
                 [org.immutant/web "2.1.6"
                  :exclusions [ch.qos.logback/logback-classic]]
                 [org.clojure/clojurescript  "1.9.227"]
                 [reagent  "0.6.0-rc"]
                 [re-frame "0.9.0"]
                 [selmer "1.10.6"]
                 [mount "0.1.11"]
                 [hikari-cp "1.7.5"]
                 [com.layerware/hugsql "0.4.7"]
                 [migratus "0.8.32"]
                 [org.postgresql/postgresql "9.4.1212"]
                 [buddy/buddy-core "1.2.0"]
                 [buddy/buddy-sign "1.4.0"]]

  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-figwheel  "0.5.4-7"]]

  :cljsbuild
  {:builds [ {:id "dev"
              :source-paths ["src/cljs"]
              :figwheel {:on-jsload madouc.core/run }
              :compiler {:main madouc.core
                         :asset-path "js/out"
                         :output-to "resources/public/js/app.js"
                         :output-dir "resources/public/js/out" }}]
   }
  :main madouc.core
  :profiles {:dev {:resource-paths ["config/dev"]}
             :prod {:resource-paths ["config/prod"]}
             :uberjar {:aot :all}})

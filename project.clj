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
                 [yogthos/config "0.8"]
                 [org.immutant/web "2.1.6"
                  :exclusions [ch.qos.logback/logback-classic]]
                 [selmer "1.10.6"]
                 [mount "0.1.11"]
                 [conman "0.6.3"]
                 [mysql/mysql-connector-java "5.1.40"]]
  :main madouc.core
  :profiles {:dev {:resource-paths ["config/dev"]}
             :prod {:resource-paths ["config/prod"]}
             :uberjar {:aot :all}})

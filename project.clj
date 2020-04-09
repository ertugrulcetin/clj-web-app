(defproject clj-web-app "0.1.0-SNAPSHOT"
  :description "Clojure ile örnek web uygulama"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [ring "1.8.0"]
                 [patika "0.1.10"]
                 [amalloy/ring-gzip-middleware "0.1.4"]]
  :main ^:skip-aot clj-web-app.core
  :repl-options {:init-ns clj-web-app.core})
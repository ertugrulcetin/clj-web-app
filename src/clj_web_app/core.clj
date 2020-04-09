;; https://medium.com/@ertu.ctn/clojure-ile-%C3%B6rnek-web-uygulamas%C4%B1-b%C3%B6l%C3%BCm-1-f7c8fce7757a
(ns clj-web-app.core
  (:require [patika.core :refer [resource get-routes]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.cookies :refer [wrap-cookies]]
            [ring.middleware.gzip :refer [wrap-gzip]]
            [compojure.core :as c]
            [compojure.route :as r]))


;; HTML formatında cevap dönüyoruz
(resource home
          :get ["/"]
          :content-type :html
          :handle-ok (fn [ctx]
                       "<html>
                                <body>
                                Merhaba Clojure!
                                <ul>
                                <li><a href=\"/users\">Users</a></li>
                                <li><a href=\"/print-request\">Pretty Print Request</a></li>
                                </ul>
                                </body>
                                </html>"))


;; Kullanıcıları JSON formatında cevap döndürüyoruz
(resource users
          :get ["/users"]
          :content-type :json
          :handle-ok (fn [ctx] [{:name "Ertuğrul" :age 28}
                                {:name "Çetin" :age 22}]))



;; Gelen istek hash map veri yapısı şeklindedir, yukarıdaki her bir kullanıcı gibi
(resource print-request
          :get ["/print-request"]
          :content-type :text
          :handle-ok (fn [ctx] (with-out-str (clojure.pprint/pprint ctx))))


(c/defroutes not-found (r/not-found "404!"))


(defn wrap-some-data
  [handler]
  (fn [request]
    (let [updated-request (assoc request :site-owner "Ertuğrul")]
      ;; ▼▼▼ returns response ▼▼▼
      (handler updated-request))))


;; :resource-ns-path -> src/clj_web_app dizini altındaki namespace'leri tarar ve tüm `resource`'ları bulup döndürür
(def app-routes (get-routes {:resource-ns-path "clj-web-app."
                             :not-found-route  'clj-web-app.core/not-found}))

(def handler (-> #'app-routes
                 wrap-reload
                 wrap-some-data
                 wrap-cookies
                 wrap-gzip))


(defn -main
  [& args]
  (run-jetty handler {:port 3000}))
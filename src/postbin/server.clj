(ns postbin.server
  (:require [com.stuartsierra.component :as component]
            [bidi.ring :refer [make-handler]]
            [aleph.http :as http]
            [ring.util.response :as res]
            [ring.util.request :as req]
            [ring.middleware.params :refer [wrap-params]]
            [postbin.view :as view]
            [postbin.store :as store]))

(defn handle-post
  "This handles creating a new paste, based on the POST data"
  [store request]
  (let [content (get (:form-params request) "content")
    uuid (store/add-new-paste store content)]
    (res/redirect (str "/" uuid) :see-other)))

(defn handle-index
  "We get there when we are displaying the index page, prompting for a new paste"
  [request]
  (res/response (view/render-form)))

(defn index-handler
  "Handle requests sent to our root URL
  They can be either GET requests (the user is looking at the form), or POST
  requests (the user just POSTed a new paste)."
  [store request]
  (if (= (:requests-method request) :post)
    (handle-post store request)
    (handle-index request)))

(defn paste-handler
  [store request]
  (let [paste (store/get-paste-by-uuid store (:uuid (:route-params request)))]
    (res/response (view/render-paste paste))))

(defn handler
  "Get the handler function for our routes"
  [store]
  (make-handler ["/" {"" (partial index-handler store)
                      [:uuid] (partial paste-handler store )}]))

(defn app
  [store]
  (-> (handler store)
      wrap-params))


(defrecord HttpServer [server]

  component/Lifecycle

  (start [this]
    (assoc this :server (http/start-server (handler (:store this)) {:port 8080})))

  (stop [this]
    (assoc this :server nil)))

(defn make-server
  []
  (map->HttpServer {}))

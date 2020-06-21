(defrecord HttpServer [server]

  component/Lifecycle

  (start [this]
    (assoc this :server (http/start-server (handler (:store this)) {:port 8080})))

  (stop [this]
    (assoc this :server nil)))

(defn make-server
  []
  (map->HttpServer {}))

(defn paste-handler
  [store request]
  (let [paste (store/get-paste-by-uuid store (:uuid (:route-params request)))]
    (res/response (view/render-paste paste))))

(defn handler
  "Get the handler function for our routes"
  [store]
  (make-handler ["/" {"" (partial index-handler store)
                      [:uuid] (partial paste-handler store)}]))

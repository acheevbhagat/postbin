(ns postbin.main
  (:require [postbin.system :refer [init-system start!]])
  (:gen-class))

(defn -main [& args]
  (init-system)
  (start!))

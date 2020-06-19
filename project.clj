(defproject postbin "0.1.0-SNAPSHOT"
  :description "A pastebin clone storing data in-memory"
  :url "https://github.com/acheevbhagat/postbin"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.stuartsierra/component "0.3.2"]
                 [aleph "0.4.6"]
                 [hiccup "1.0.5"]
                 [bidi "2.1.3"]]
  :main ^:skip-aot postbin.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

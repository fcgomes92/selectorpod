(defproject selectorpod "0.1.0-SNAPSHOT"
  :description "Twitter bot to get a list of episodes from a podcast and tweets it"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [clj-spotify "0.1.10"]
                 [lynxeyes/dotenv "1.1.0"]
                 [org.slf4j/slf4j-log4j12 "1.7.30"]
                 [twitter-api "1.8.0"]]
  :main ^:skip-aot selectorpod.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})

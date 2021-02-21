(ns selectorpod.core
  (:require  [clojure.set]
             [clojure.string :as s]
             [twitter.api.restful :as twitter]
             [twitter.oauth :as twitter-oauth]
             [dotenv :refer [env]]
             [clj-spotify.core :as spotify]
             [clj-spotify.util :refer [get-access-token]])
  (:gen-class))

(def episodes [])

(def spotify-access-token (get-access-token (env "SPOTIFY_CLIENT_ID") (env "SPOTIFY_CLIENT_SECRET")))

(def twitter-credentials (twitter-oauth/make-oauth-creds (env "TWITTER_APP_CONSUMER_KEY")
                                                         (env "TWITTER_APP_CONSUMER_SECRET")
                                                         (env "TWITTER_USER_ACCESS_TOKEN")
                                                         (env "TWITTER_USER_ACCESS_TOKEN_SECRET")))

(defn tweet [text]
  (when (and (not-empty text) (<= (count text) 400))
    (try (twitter/statuses-update :oauth-creds twitter-credentials
                                  :params {:status text})
         (catch Exception e (println "Something went wrong: " (.getMessage e))))))

(def last-episodes ((spotify/api-get "shows/id/episodes") {:id "6IAVb4s0xkX9l9Ym9hZjM5" :market "ES" :limit 50} spotify-access-token))
(def selected-episodes (take 5 (shuffle (:items last-episodes))))
(def tweet-text (str "Playlist para dormir hoje\n\n" (s/join "\n" (map #(str (:name %) ": " (get-in % [:external_urls :spotify])) selected-episodes)) "\n\n ---"))

(println tweet-text)
(defn -main
  [& args]
  (tweet tweet-text))

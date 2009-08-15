(ns kvbot.commands
  (:use [clojure.contrib str-utils]))

(defn retrieve
  "Retrieve a key-value pair from the datastore"
  [key]
  (println "Retrieving" key))

(def commands {"GET" retrieve})

;; Command handling

(defn execute
  "Execute a command from the remote client"
  [input]
  (let [input-words (re-split #"\s+" input)
        command (first input-words)
        args (rest input-words)]
    (apply (commands command) args)))
(ns kvbot.commands
  (:use [clojure.contrib str-utils]))

(def data_store {"1" "ASDF"})

(defn retrieve_key
  "Retrieve a key-value pair from the datastore"
  [key]
  (get data_store key))

(defn set_key
  [k v]
  (dosync
    (alter data_store (assoc data_store k v))))

(def commands {"GET" retrieve_key
               "SET" set_key})

(defn execute
  "Execute a command from the remote client"
  [input]
  (let [input-words (re-split #"\s+" input)
        command (first input-words)
        args (rest input-words)]
    (apply (commands command) args)))
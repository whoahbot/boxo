(ns boxo.commands
  (:use [clojure.contrib str-utils duck-streams]))

(def *data_store* (ref {}))

(defn retrieve-key
  "Retrieve a key-value pair from the datastore"
  [key args]
  (get @*data_store* key))

(defn set-key
  "Set a key-value pair in the datastore"
  [key args]
  (dosync
    (alter *data_store* conj {key args}))
    "+OK")

(defn increment-key
  "Increment a serial key"
  ; TODO: catch if this key isn't an integer
  [key args]
  (dosync
    (if (get @*data_store* key)
      (alter *data_store* conj {key (inc (get @*data_store* key))})
      (alter *data_store* conj {key 1}))
      (get @*data_store* key)))

(defn serialize-datastore
  "Serialize the datastore to a string"
  []
  (binding [*print-dup* true]
    (pr-str @*data_store*)))

(defn cleardb
  "Empty the datastore"
  []
  (dosync
   alter *data_store* {}))

(defn bgsave
  "write the serialized datastore out to a file"
  []
  (clojure.contrib.duck-streams/spit "output.txt" serialize-datastore))

(def commands
  {"GET"    retrieve-key
   "SET"    set-key
   "INCR"   increment-key
   "BGSAVE" bgsave})

(defn execute
  "Execute a command from the remote client"
  [input]
  (try (let [input-words (re-find #"(\w+)\s+(\w+)\s*(.*)$" input)
             command (get input-words 1)
             key (get input-words 2)
             args (get input-words 3)]
    ((commands command) key args))
    (catch Exception e
      (.printStackTrace e *err*)
       "-ERR")))

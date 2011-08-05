(ns boxo.commands
  (:use [clojure.contrib str-utils duck-streams except]))

(def *data_store* (ref {}))

(defn retrieve-key
  "Retrieve a key-value pair from the datastore"
  [args]
  (get @*data_store* (first args)))

(defn set-key
  "Set a key-value pair in the datastore"
  [args]
  (dosync
   (alter *data_store* conj {(first args) (rest args)}))
    "+OK")

(defn increment-key
  "Increment a serial key"
  ; TODO: catch if this key isn't an integer
  [args]
  (dosync
   (let [key (first args)]
     (if (get @*data_store* key)
       (alter *data_store* conj {key (inc (get @*data_store* key))})
       (alter *data_store* conj {key 1}))
     (get @*data_store* key))))

(defn serialize-datastore
  "Serialize the datastore to a string"
  []
  (binding [*print-dup* true]
    (pr-str @*data_store*)))

(defn cleardb
  "Empty the datastore"
  [_]
  (dosync
   ref-set *data_store* {}))

(defn bgsave
  "write the serialized datastore out to a file"
  [_]
  (clojure.contrib.duck-streams/spit "output.txt" serialize-datastore))

(def commands
  {"GET"     retrieve-key
   "SET"     set-key
   "INCR"    increment-key
   "CLEARDB" cleardb
   "BGSAVE"  bgsave})

(defn execute
  "Execute a command from the remote client"
  [input]
  (try (let [input-words (re-seq #"\w+" input)
             command_str (first input-words)
             args (rest input-words)
             command (commands command_str)]
         (throw-if (nil? command)
                   IllegalArgumentException (str "No command named " command_str))
         (command args))
       (catch IllegalArgumentException e
         (.printStackTrace e *err*)
         "-ERR")))

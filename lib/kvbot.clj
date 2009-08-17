#!/usr/bin/env clj

(ns kvbot
  (:use [kvbot commands])
  (:use [clojure.contrib server-socket duck-streams]))

(def port 2323)

(defn- kvbot-client [in out]
  (binding [*in* (reader in) 
            *out* (writer out)]
    (loop [input (read-line)]
      (println (execute input))
      (recur (read-line)))))

(def server (create-server port kvbot-client))
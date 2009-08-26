#!/usr/bin/env clj

(ns boxo.server
  (:use [boxo commands])
  (:use [clojure.contrib server-socket duck-streams]))


(defn- boxo-handle-client [in out]
  (binding [*in* (reader in) 
            *out* (writer out)]
    (try (loop [input (read-line)]
      (when input
        (println (execute input))
        (recur (read-line)))))))
      
(defn -main
  ([port]
     (defonce server (create-server (Integer. port) boxo-handle-client))
     (println "Launching boxo server on port" port))
  ([] (-main 2323)))
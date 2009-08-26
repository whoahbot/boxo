#!/usr/bin/env clj

(ns kvbot.server
  (:use [kvbot commands])
  (:use [clojure.contrib server-socket duck-streams]))


(defn- kvbot-handle-client [in out]
  (binding [*in* (reader in) 
            *out* (writer out)]
    (try (loop [input (read-line)]
      (when input
        (println (execute input))
        (recur (read-line)))))))
      
(defn -main
  ([port]
     (defonce server (create-server (Integer. port) kvbot-handle-client))
     (println "Launching Kvbot server on port" port))
  ([] (-main 2323)))
#!/bin/sh

java -cp ~/src/clojure/clojure.jar:~/src/clojure/clojure-contrib.jar:lib/ clojure.main -e "(use 'kvbot.server) (-main)"
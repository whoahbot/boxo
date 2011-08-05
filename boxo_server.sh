#!/bin/sh

CLASSPATH=lib/clojure-1.1.0.jar:lib/clojure-contrib-1.1.0.jar

java -server -Xmx1024m -cp $CLASSPATH clojure.main -e "(use 'boxo.server) (-main)"

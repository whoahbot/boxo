#!/bin/sh

CLASSPATH=jars/clojure.jar:jars/clojure-contrib.jar:lib/

java -cp $CLASSPATH clojure.main lib/kvbot/test/all.clj
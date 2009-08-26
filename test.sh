#!/bin/sh

CLASSPATH=~/src/clojure/clojure.jar:~/src/clojure/clojure-contrib.jar:lib/

java -cp $CLASSPATH clojure.main lib/boxo/test/all.clj
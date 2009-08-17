(ns kvbot.test.all
  (:use [kvbot.test commands])
  (:use [clojure.contrib test-is]))

(run-tests 'kvbot.test.commands)
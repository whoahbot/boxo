(ns boxo.test.all
  (:use [boxo.test commands])
  (:use [clojure.contrib test-is]))

(run-tests 'boxo.test.commands)
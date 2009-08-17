(ns kvbot.test.commands
  (:use [kvbot commands])
  (:use [clojure.contrib test-is seq-utils]))
  
(deftest test-get
  (is (= "ASDF" (execute "GET 1"))))
(ns kvbot.test.commands
  (:use [kvbot commands])
  (:use [clojure.contrib test-is seq-utils]))
  
(deftest test-get-existing-key
  (execute "SET 1 key-one")
  (is (= "key-one" (execute "GET 1"))))
  
(deftest test-set-new-key
  (execute "SET 2 key-two")
  (is (= "key-two" (execute "GET 2"))))

(deftest test-set-existing-key
  (execute "SET 2 two-key")
  (is (= "two-key" (execute "GET 2")) "Setting the new value should replace the old"))
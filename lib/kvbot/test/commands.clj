(ns kvbot.test.commands
  (:use [kvbot commands])
  (:use [clojure.contrib test-is seq-utils duck-streams]))
  
(deftest test-command-returns-ok
  (is (= "+OK" (execute "SET 1 microbeaver"))))

(deftest test-command-doesnt-exists-returns-err
  (binding [*err* (writer "/dev/null")]
    (is (= "-ERR" (execute "BEERCHUG 1 microbeaver")))))
  
(deftest test-get-existing-key
  (execute "SET 1 turboweasel")
  (is (= "turboweasel" (execute "GET 1"))))
  
(deftest test-set-new-key
  (execute "SET 2 laser")
  (is (= "laser" (execute "GET 2"))))

(deftest test-set-existing-key
  (execute "SET 2 laser")
  (is (= "laser" (execute "GET 2")) "Setting the new value should replace the old"))

(deftest test-increment-key-that-doesnt-exist
  (is (= 1 (execute "INCR fishbutter"))))
  
(deftest test-increment-key-that-does-exist
  (is (= 2 (execute "INCR fishbutter")))
  (is (= 3 (execute "INCR fishbutter"))))
  
(deftest test-string-value-with-spaces)
  (execute "SET 3 thurgood marshall and his amazing armchair")
  (is (= "thurgood marshall and his amazing armchair" (execute "GET 3")))
(ns semantic-service.core-test
  (:require [clojure.test :refer :all]
            [semantic-service.sparql :refer :all]))

(deftest sparql-query-test 
  (testing " sparql-query"
  (let [concept1 {:Concept "http://www.openlinksw.com/schemas/virtrdf#QuadMapFormat"}
        concept2 {:Concept "http://www.openlinksw.com/schemas/virtrdf#QuadStorage"}
        concept-details (conj '() concept2 concept1)]
  (is (= concept-details (sparql-query {:endpoint "http://dbpedia.org/sparql"}
                         "select distinct ?Concept where {[] a ?Concept} LIMIT 2"))))))

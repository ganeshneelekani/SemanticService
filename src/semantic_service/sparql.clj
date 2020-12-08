(ns semantic-service.sparql
  (:gen-class)
  (:import (org.apache.jena.query Query
                                  QueryExecution
                                  QueryExecutionFactory)))

(defn convert-to-rdf
  [input]
  (cond (.isLiteral input) 
        (.getValue (.asLiteral input))
   :else (.toString input)))

(defn sparql-query
    "Run SPARQL query against a particular endpoint configuration, 
    returns list of maps where SPARQL vars are keywords"
   [service-config query-string]
   (let [{:keys [endpoint]} service-config
         query-result (QueryExecutionFactory/sparqlService endpoint query-string)]
     (map
      (fn [as]
        (into  {}
          (mapcat
             (fn [name]
                 {(keyword name) (convert-to-rdf (.get as name))}) 
                  (iterator-seq (.varNames as)))))
      (iterator-seq (.execSelect query-result)))))


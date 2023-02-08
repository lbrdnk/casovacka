(ns casovacka.dev
  #_(:require [shadow.cljs.devtools.api :as shadow])
  (:require [clojure.walk :as walk]))

(defonce navigation-state (atom nil))

(comment
  ;; following must be called from cljs repl
  shadow/with-runtime
  shadow/watch-set-autobuild!
  )

(comment
  casovacka.db/empty-db
  )

;; db initialization should fill in indices used
(comment
  ;; unable to walk on, postwalk ok
  (walk/walk
   identity
   #_(fn [elm]
       (if (sequential? elm)
         (reduce (fn [acc e-in]
                   (let [id (:id e-in)]
                     (assoc acc id e-in)))
                 {}
                 elm)
         elm))
   (fn [elm]
     (if (map? elm)
       (assoc elm :id (str (random-uuid)))
       elm))
   #_(fn [elm]
       (if (sequential? elm)
         (reduce (fn [acc e-in]
                   (let [id (:id e-in)]
                     (assoc acc id e-in)))
                 {}
                 elm)
         elm))
   casovacka.db/empty-db)

  (walk/postwalk
   (fn [elm]
     (if (map? elm)
       (assoc elm :id (str (random-uuid)))
       elm))
   casovacka.db/empty-db))
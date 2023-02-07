(ns casovacka.util)

(defn conjv [coll & xs]
  (reduce (fn [acc x] (conj acc x)) (vec coll) xs))

(defn conjs [coll & xs]
  (reduce (fn [acc x] (conj acc x)) (set coll) xs))

(defn prepv [coll e]
  (vec (mapcat #(vector %1 %2) (repeat e) coll)))

#_(defn select-ns-keys [m ns-kw]
  (let [all-keys (keys m)
        
        matching-ns-kw (filter all-keys)])
  (-> ns-kw namespace)
  )

(comment
  
  #_(select-ns-keys {:a.b/c 1 :d.e/f 2 :d #:g.h{:i 1 :j 2}} :g.h/i)
  
  (keys {:a.b/c 1 :d.e/f 2 :d #:g.h{:i 1 :j 2}})
  
  ;; make it nested -- for all paths in m
  )

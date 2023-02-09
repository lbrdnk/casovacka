(ns casovacka.util)

(defn conjv [coll & xs]
  (reduce (fn [acc x] (conj acc x)) (vec coll) xs))

(defn conjs [coll & xs]
  (reduce (fn [acc x] (conj acc x)) (set coll) xs))

(defn popv [coll]
  (if (not-empty coll)
    (pop (vec coll))
    []))
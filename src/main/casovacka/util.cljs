(ns casovacka.util)

(defn conjv [coll & xs]
  (reduce (fn [acc x] (conj acc x)) (vec coll) xs))

(defn conjs [coll & xs]
  (reduce (fn [acc x] (conj acc x)) (set coll) xs))

(defn popv [coll]
  (if (not-empty coll)
    (pop (vec coll))
    []))

(defn avail-uuid [used]
  (loop [i 0
         uuid (str (random-uuid))]
    (cond (>= i 5)
          (js/Error. "Unable to generate unique uuid")

          ;; TODO -- valid condition for intent?
          (get used uuid)
          (recur (inc i) (str (random-uuid)))

          :else
          uuid)))
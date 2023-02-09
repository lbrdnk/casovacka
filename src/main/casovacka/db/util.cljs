(ns casovacka.db.util
  (:require [goog.string :as gstr]))


;; TODO variable length, hiding hn, hiding h, ...
(defn ms->timer-str [ms]
  (let [[h hr] [(quot ms 3600000) (rem ms 3600000)]
        [m mr] [(quot hr 60000) (rem hr 60000)]
        [s sr] [(quot mr 1000) (rem mr 1000)]
        ;; hn hundredth
        hn (quot sr 10)]
    (str (gstr/format #_"%d:%02d:%02d" "%d:%02d:%02d.%02d" h m s hn))))

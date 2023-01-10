(ns casovacka.components.hud
  (:require ["react-native" :as rn]))

(def HUDImpl (-> (js/require "../src/main/casovacka/components/js/HUD.js") .-default))

(defn hud []
  (let []
    (fn []
      [:> HUDImpl]
      #_[:> rn/View])))
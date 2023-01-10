(ns casovacka.core
  (:require
   [casovacka.screens.main :refer [main]]
   ["react-native" :as rn]
   [reagent.core :as r]
   [shadow.expo :as expo]))

(def styles
  ^js (-> {:container
           {:flex 1
            :backgroundColor "#fff"
            :alignItems "center"
            :justifyContent "center"}
           :title
           {:fontWeight "bold"
            :fontSize 24
            :color "blue"}}
          (clj->js)
          (rn/StyleSheet.create)))

(defn root []
  [main]
  #_[:> rn/View {:style (.-container styles)}
   [:> rn/Text {:style (.-title styles)} "Hello friend!"]])

(defn start
  {:dev/after-load true}
  []
  (expo/render-root (r/as-element [root])))

(defn init []
  (start))
(ns casovacka.core
  (:require
  ;;  [casovacka.screens.main :refer [main]]
   #_["/components/HUD" :as Hud]
  ;;  ["/components/Simple" :as s]
   [casovacka.db :as db]
   [goog.object :as gobj]
   ["react-native" :as rn]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [shadow.expo :as expo]
   
   ;; dev
   [casovacka.navigation :refer [navigation]]
   
   ))

#_(def Simple (-> (js/require "../src/gen/components/Simple.js") ))

(def Hud (-> (js/require "../src/gen/components/HUD.js") (goog.object.get "default")))

(comment
  Hud
  (goog.object.get Hud "default")
)

(defn root []
  #_[:> rn/View {:style {:flex 1 :justify-content "center" :align-items "center"}}
  ;;  [#'main]
  ;;  [:> s/Simple]
   [:> Hud]
   #_[:> (js/goog.object.get Simple "Simple")]]
  
  #_[home]
  
  [navigation])

(defn start
  {:dev/after-load true}
  []
  (expo/render-root (r/as-element [#'root])))

(defn init []
  (rf/dispatch-sync [::db/initialize])
  (start))

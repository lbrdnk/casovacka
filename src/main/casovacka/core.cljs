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
   [casovacka.storybook :as sb]
   
   ))

(goog-define STORYBOOK false)

(comment
  STORYBOOK
  )


;;; TODO following will be needed later probably
#_(defonce functional-compiler (reagent.core/create-compiler {:function-components true}))
#_(reagent.core/set-default-compiler! functional-compiler)

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
  
  (if STORYBOOK
    [sb/with-storybook]
    [navigation]))

(defn start
  {:dev/after-load true}
  []
  (expo/render-root (r/as-element [#'root])))

(defn init []
  (rf/dispatch-sync [::db/initialize])
  (start))

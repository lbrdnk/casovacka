(ns casovacka.view
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            ["react-native" :as rn]
            ["/EditScreen$default" :as EditScreen]
            ["/IntervalScreen$default" :as IntervalScreen]
            ["/HomeScreen$default" :as HomeScreen]
            
            ))

#_(def HomeScreen (-> (js/require "../src/gen/HomeScreen.js") .-default))
#_(def IntervalScreen (-> (js/require "../src/gen/IntervalScreen.js") .-default))
#_(def EditScreen (-> (js/require "../src/gen/EditScreen.js") .-default))

(defn edit-screen [props]
  (let [props (merge props
                     {})]
    [:<>
     [:> EditScreen props]
     #_[:> rn/View [:> rn/Text "xixx"]]]
    ))

(defn home-screen
  [props]
  (def p props)
  (let [interval-list-items @(rf/subscribe [:home-screen/interval-list-items (:navigation props)])
        props (merge props
                     {:home-screen/interval-list-items interval-list-items
                      :newPressedHandler #(rf/dispatch [:home-screen/new-pressed (:navigation props)])})]
    #_(def p2 props)
    [:> HomeScreen props]))



(defn interval-screen
  [props]
  (let [props (merge props
                     {:title @(rf/subscribe [:selected-timer.sub/title])
                      :timeStr @(rf/subscribe [:selected-timer.sub/time-str])
                      :running @(rf/subscribe [:selected-timer.sub/running])
                      ;; 
                      :startPressedHandler #(rf/dispatch [:interval-screen/start-pressed])
                      :stopPressedHandler #(rf/dispatch [:interval-screen/stop-pressed])
                      :resetPressedHandler #(rf/dispatch [:interval-screen/reset-pressed])})]
    #_(def p props)
    [:> IntervalScreen props]))

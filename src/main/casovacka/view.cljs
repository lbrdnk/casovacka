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
  (let [props (merge

               ;; orig props
               props

               ;; db data map
               ;; id, title, duration, repeat
               @(rf/subscribe [:edit-screen/data])

               ;; handlers fns
               ;; TODO
               ;;   - missing navigation
               {:edit-screen/newPressedHandler #(#_#_prn "xixi" rf/dispatch [:edit-screen/new-pressed (:navigation props)])
                :edit-screen/savePressedHandler #(#_#_prn "save" rf/dispatch [:edit-screen/save-pressed %])
                :edit-screen/deletePressedHandler #(prn "delete" #_#_rf/dispatch [:edit-screen/delete-pressed])
                :edit-screen/cancelPressedHandler #(prn "cancel" #_#_rf/dispatch [:edit-screen/cancel-pressed])})]
    (def p props)
    [:<>
     [:> EditScreen props]
     #_[:> rn/View [:> rn/Text "xixx"]]]
    ))

(defn home-screen
  [props]
  #_(def p props)
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

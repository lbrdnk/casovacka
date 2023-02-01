(ns casovacka.view
  (:require ["@react-navigation/bottom-tabs" :refer [createBottomTabNavigator]]
            ["@react-navigation/native" :refer [NavigationContainer]]
            [re-frame.core :as rf]
            [reagent.core :as r]
            
            ;; tmp
            ;; [casovacka.navigation :refer [navigation]]
            ))

(def HomeScreen (-> (js/require "../src/gen/HomeScreen.js") .-default))
(def IntervalScreen (-> (js/require "../src/gen/IntervalScreen.js") .-default))

#_(defn EditScreen [props]
    [:> rn/View {:style {:flex 1 :justify-content "center" :align-items "center"}}
     [:> rn/Text "edit"]])

(defn home-screen
  [props]
  #_(def p props)
  (let [interval-list-items @(rf/subscribe [:home-screen/interval-list-items (:navigation props)])
        props (merge props
                     {:home-screen/interval-list-items interval-list-items})]
    #_(def p2 props)
    [:> HomeScreen props]))



(defn interval-screen
  [props]
  (let [title @(rf/subscribe [:selected-timer.sub/title])
        time-str @(rf/subscribe [:selected-timer.sub/time-str])
        running @(rf/subscribe [:selected-timer.sub/running])
        props (merge props
                     {:title title
                      :timeStr time-str
                      :running running
                      :startPressedHandler #(rf/dispatch [:interval-screen/start-pressed])
                      :stopPressedHandler #(rf/dispatch [:interval-screen/stop-pressed])
                      :resetPressedHandler #(rf/dispatch [:interval-screen/reset-pressed])})]
    #_(def p props)
    [:> IntervalScreen props]))

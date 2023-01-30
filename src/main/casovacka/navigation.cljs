(ns casovacka.navigation
  (:require ["@react-navigation/native" :refer [NavigationContainer]]
            ["@react-navigation/native-stack" :refer [createNativeStackNavigator]]

            ;; dev
            ["react-native" :as rn]
            [reagent.core :as r]))

(def HomeScreen (-> (js/require "../src/gen/HomeScreen.js") .-default))
(def IntervalScreen (-> (js/require "../src/gen/IntervalScreen.js") .-default))

(defonce Stack (createNativeStackNavigator))

#_(defn EditScreen [props]
  [:> rn/View {:style {:flex 1 :justify-content "center" :align-items "center"}}
   [:> rn/Text "edit"]])

(defn navigation []
  [:> NavigationContainer
   [:> (.-Navigator Stack)
    [:> (.-Screen Stack) {:name "home" :component HomeScreen}]
    [:> (.-Screen Stack) {:name "interval" :component IntervalScreen}]
    #_[:> (.-Screen Stack) {:name "edit" :component (r/reactify-component #'EditScreen)}]]])
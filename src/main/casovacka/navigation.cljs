(ns casovacka.navigation
  (:require [casovacka.view :as view]
            ["@react-navigation/native" :refer [NavigationContainer]]
            ["@react-navigation/native-stack" :refer [createNativeStackNavigator]]

            ;; dev
            ["react-native" :as rn]
            [reagent.core :as r]))

(defonce Stack (createNativeStackNavigator))

(defn navigation []
  [:> NavigationContainer
   [:> (.-Navigator Stack)
    [:> (.-Screen Stack) {:name "home" :component (r/reactify-component view/home-screen)}]
    [:> (.-Screen Stack) {:name "interval" :component (r/reactify-component view/interval-screen)}]
    #_[:> (.-Screen Stack) {:name "edit" :component (r/reactify-component #'EditScreen)}]]])
(ns casovacka.navigation
  (:require [casovacka.view :as view]
            ["@react-navigation/native" :refer [NavigationContainer]]
            ["@react-navigation/native-stack" :refer [createNativeStackNavigator]]

            ;; dev
            ["react-native" :as rn]
            [reagent.core :as r]))

;;; (defonce Stack (createNativeStackNavigator))
(def Stack (createNativeStackNavigator))

(defn navigation []
  ;; independent because of storybook nesting
  ;; TODO verify if this wont cause problems
  [:> NavigationContainer {:independent true}
   [:> (.-Navigator Stack)
    [:> (.-Screen Stack) {:name "home"
                          :component (r/reactify-component (with-meta view/home-screen
                                                             {:displayName "HomeScreen"}))}]
    [:> (.-Screen Stack) {:name "interval"
                          :component (r/reactify-component (with-meta view/interval-screen
                                                             {:displayName "IntervalScreen"}))}]
    #_[:> (.-Screen Stack) {:name "edit" :component (r/reactify-component #'EditScreen)}]]])
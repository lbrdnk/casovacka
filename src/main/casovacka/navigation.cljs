(ns casovacka.navigation
  (:require [casovacka.view :as view]
            ["@react-navigation/native" :refer [NavigationContainer]]
            ["@react-navigation/native-stack" :refer [createNativeStackNavigator]]

            ;; dev
            ["react-native" :as rn]
            [reagent.core :as r]
            [casovacka.dev :as dev]))

;;; (defonce Stack (createNativeStackNavigator))
(def Stack (createNativeStackNavigator))

(defn navigation []
  ;; independent because of storybook nesting
  
  ;; TODO verify if this wont cause problems
  [:> NavigationContainer 
   {:independent true
    
    ;; #_#_#_#_
    ;; check if nil ok
    :initialState @dev/navigation-state
    :onStateChange #(reset! dev/navigation-state %)}
   [:> (.-Navigator Stack)
    [:> (.-Screen Stack) {:name "home"
                          :component (r/reactify-component (with-meta view/home-screen
                                                             {:displayName "HomeScreen"}))}]
    [:> (.-Screen Stack) {:name "interval"
                          :component (r/reactify-component (with-meta view/interval-screen
                                                             {:displayName "IntervalScreen"}))}]
    [:> (.-Screen Stack) {:name "edit"
                          :component (r/reactify-component (with-meta view/edit-screen
                                                             {:displayName "EditScreen"}))}]]])
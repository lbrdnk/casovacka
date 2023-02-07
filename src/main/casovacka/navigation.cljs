(ns casovacka.navigation
  (:require [casovacka.view :as view]
            ["@react-navigation/native" :refer [NavigationContainer]]
            ["@react-navigation/native-stack" :refer [createNativeStackNavigator]]
            
            ;; header button "onPress"
            ["@react-navigation/elements" :refer [HeaderBackButton]]

            [goog.object :as gobj]

            [re-frame.core :as rf]
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
    [:> (.-Group Stack) {:screenOptions (fn [^js props]
                                          (let [nav (gobj/get props "navigation")]
                                            (clj->js {:headerLeft (fn [^js y]
                                                                    (def pp props)
                                                                    (def yy y)
                                                                    (r/as-element [:> HeaderBackButton
                                                                                   {:label "back"
                                                                                    :labelVisible true
                                                                                    :onPress (fn []
                                                                                               (.goBack nav)
                                                                                               (rf/dispatch [:edit-screen.header/back-pressed])
                                                                                               (prn "PRESSED BACK"))}]))
                                                      :headerBackTitle "back"})))}
     [:> (.-Screen Stack) {:name "edit"
                           :component (r/reactify-component (with-meta view/edit-screen
                                                              {:displayName "EditScreen"}))}]]]])
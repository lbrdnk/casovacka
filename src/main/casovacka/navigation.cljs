(ns casovacka.navigation
  (:require [casovacka.view :as view]
            ["@react-navigation/native" :refer [NavigationContainer]]
            ["@react-navigation/native-stack" :refer [createNativeStackNavigator]]
            
            ;; header button "onPress"
            ["@react-navigation/elements" :refer [HeaderBackButton]]

            [goog.object :as gobj]

            [re-frame.core :as rf]
            [reagent.core :as r]
            [casovacka.dev :as dev]))

(def Stack (createNativeStackNavigator))

(defn navigation []
  ;; independent because of storybook nesting
  [:> NavigationContainer 
   {;; dev purposes, TODO mask with closure defines
    :independent true
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
                                                                    (r/as-element [:> HeaderBackButton
                                                                                   {:label "back"
                                                                                    :labelVisible true
                                                                                    :onPress (fn []
                                                                                               (rf/dispatch [:e.edit-screen/back-pressed nav]))}]))
                                                      :headerBackTitle "back"
                                                      :headerRight (fn [^js y]
                                                                     (r/as-element [:> HeaderBackButton
                                                                                    {:label "delete"
                                                                                     :backImage nil
                                                                                     :tintColor "red"
                                                                                     :labelVisible true
                                                                                     :onPress (fn []
                                                                                                (rf/dispatch [:e.edit-screen/delete-pressed nav]))}]))})))}
     [:> (.-Screen Stack) {:name "edit"
                           :component (r/reactify-component (with-meta view/edit-screen
                                                              {:displayName "EditScreen"}))}]]]])
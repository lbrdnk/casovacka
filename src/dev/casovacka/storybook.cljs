(ns casovacka.storybook
  (:require ["@react-navigation/bottom-tabs" :refer [createBottomTabNavigator]]
            ["@react-navigation/native" :refer [NavigationContainer]]
            [reagent.core :as r]
            ;; tmp
            [casovacka.navigation :refer [navigation]]))

(def Storybook (-> (js/require "../.storybook/Storybook.js") .-default))

(defonce Tab (createBottomTabNavigator))

#_(defn storybook [props]
    [:> Storybook])



(defn with-storybook [props]
  [:> NavigationContainer
   [:> (.-Navigator Tab) {:screenOptions {:headerShown false}}
    [:> (.-Screen Tab)
     {:name "App"
      :component (r/reactify-component
                  (with-meta navigation {:displayName "Navigation"}))}]
    [:> (.-Screen Tab) {:name "Storybook"
                        :component Storybook}]]])
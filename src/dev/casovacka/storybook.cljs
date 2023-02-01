(ns casovacka.storybook
  (:require ["@react-navigation/bottom-tabs" :refer [createBottomTabNavigator]]
            ["@react-navigation/native" :refer [NavigationContainer]]
            [reagent.core :as r]
            ;; tmp
            [casovacka.navigation :refer [navigation]]

            ;; tmp
            ["@expo/vector-icons/MaterialCommunityIcons$default" :as MaterialCommunityIcons]))

(def Storybook (-> (js/require "../.storybook/Storybook.js") .-default))

(defonce Tab (createBottomTabNavigator))

#_(defn storybook [props]
    [:> Storybook])

(defn screen-options [^js x]
  (let [route-name (-> x .-route .-name)]
    (clj->js {:headerShown false
              :tabBarIcon (fn [^js y]
                                (let [icon-name (case route-name
                                                  "App" "application-outline"
                                                  "Storybook" "application-edit-outline"
                                                  "cicina")
                                      icon-color (.-color y)
                                      icon-size (.-size y)]
                                  (r/as-element [:> MaterialCommunityIcons
                                                 {:name icon-name :size icon-size :color icon-color}])))
              :tabBarActiveTintColor "tomato"
              :tabBarInactiveTintColor "gray"})))

(defn with-storybook [props]
  [:> NavigationContainer
   [:> (.-Navigator Tab) {:screenOptions screen-options}
    [:> (.-Screen Tab)
     {:name "App"
      :component (r/reactify-component
                  (with-meta navigation {:displayName "Navigation"}))}]
    [:> (.-Screen Tab) {:name "Storybook"
                        :component Storybook}]]])
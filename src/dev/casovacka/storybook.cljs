(ns casovacka.storybook
  (:require ["@react-navigation/bottom-tabs" :refer [createBottomTabNavigator]]
            ["@react-navigation/native" :refer [NavigationContainer]]
            [reagent.core :as r]
            ;; tmp
            [casovacka.navigation :refer [navigation]]
            
            ["@storybook/react-native" :as sb]

            #_["/storybook/Storybook$default" :as Storybook]
            ;; tmp
            ["@expo/vector-icons/MaterialCommunityIcons$default" :as MaterialCommunityIcons]))

(comment

  (def s (sb/getStorybook))
  s

  (-> (sb/raw) js->clj (nth 1))
  )

(defonce sb-nav-state (atom nil))

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
   {:independent true
    :initialState @sb-nav-state
    :onStateChange #(reset! sb-nav-state %)}
   [:> (.-Navigator Tab) {:screenOptions screen-options}
    [:> (.-Screen Tab)
     {:name "App"
      :component (r/reactify-component
                  (with-meta navigation {:displayName "Navigation"}))}]
    [:> (.-Screen Tab) {:name "Storybook"
                        :component Storybook}]]])
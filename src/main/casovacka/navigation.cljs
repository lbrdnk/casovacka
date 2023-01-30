(ns casovacka.navigation
  (:require ["@react-navigation/native" :refer [NavigationContainer]]
            ["@react-navigation/native-stack" :refer [createNativeStackNavigator]]
            
            ;; dev
            ["react-native" :as rn]
            [reagent.core :as r]
            ))

(defonce Stack (createNativeStackNavigator))

(defn Home [props]
  #_[:> rn/View {:align-items :center
               :flex 1 
               :justify-content :center}
   [:> rn/Text "home"]]
  [:> rn/View {:style {:flex 1 :justify-content "center" :align-items "center"}}
   [:> rn/Text "home"]])

(defn navigation []
  [:> NavigationContainer
   [:> (.-Navigator Stack)
    ;; works with var for some reason
    [:> (.-Screen Stack) {:name "tmp" :component (r/reactify-component #'Home)}]]])
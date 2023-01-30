(ns casovacka.navigation
  (:require ["@react-navigation/native" :refer [NavigationContainer]]
            ["@react-navigation/native-stack" :refer [createNativeStackNavigator]]
            
            ;; dev
            ["react-native" :as rn]
            [reagent.core :as r]
            
            ))

(def Home (-> (js/require "../src/gen/HomeScreen.js") .-default))

#_(defn Home [props]
  [:> HomeJS {}])


(defonce Stack (createNativeStackNavigator))

#_(defn Home [props]
  [:> rn/View {:style {:flex 1 :justify-content "center" :align-items "center"}}
   [:> rn/Text "home"]
   [:> rn/Button {:on-press #(.navigate (:navigation props) "interval") 
                  :title "go to interval"}]])

(defn Interval [props]
  [:> rn/View {:style {:flex 1 :justify-content "center" :align-items "center"}}
   [:> rn/Text "interval"]
   [:> rn/Button {:on-press #(.navigate (:navigation props) "settings")
                  :title "go to settings"}]])

(defn Settings [props]
  [:> rn/View {:style {:flex 1 :justify-content "center" :align-items "center"}}
   [:> rn/Text "settings"]])

(defn navigation []
  [:> NavigationContainer
   [:> (.-Navigator Stack)
    ;; works with var for some reason
    [:> (.-Screen Stack) {:name "home" :component Home #_(r/reactify-component #'Home)}]
    [:> (.-Screen Stack) {:name "interval" :component (r/reactify-component #'Interval)}]
    [:> (.-Screen Stack) {:name "settings" :component (r/reactify-component #'Settings)}]]])
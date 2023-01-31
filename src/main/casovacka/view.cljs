(ns casovacka.view
  (:require [re-frame.core :as rf]))

(def HomeScreen (-> (js/require "../src/gen/HomeScreen.js") .-default))
(def IntervalScreen (-> (js/require "../src/gen/IntervalScreen.js") .-default))

#_(defn EditScreen [props]
    [:> rn/View {:style {:flex 1 :justify-content "center" :align-items "center"}}
     [:> rn/Text "edit"]])

(defn home-screen
  [props]
  #_(def p props)
  (let [interval-list-items @(rf/subscribe [:home-screen/interval-list-items (:navigation props)])
        props (merge props
                     {:home-screen/interval-list-items interval-list-items})]
    #_(def p2 props)
    [:> HomeScreen props]))

(defn interval-screen
  [props]
  (let [title @(rf/subscribe [:selected-interval-title])
        props (merge props
                     {:title title})]
    #_(def p props)
    [:> IntervalScreen props]))
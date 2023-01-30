(ns casovacka.view)

(def HomeScreen (-> (js/require "../src/gen/HomeScreen.js") .-default))
(def IntervalScreen (-> (js/require "../src/gen/IntervalScreen.js") .-default))

#_(defn EditScreen [props]
    [:> rn/View {:style {:flex 1 :justify-content "center" :align-items "center"}}
     [:> rn/Text "edit"]])

(defn home-screen
    [props]
    [:> HomeScreen props])

(defn interval-screen
  [props]
  [:> IntervalScreen props])
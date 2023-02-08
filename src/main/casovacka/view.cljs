(ns casovacka.view
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            ["react-native" :as rn]
            ["/EditScreen$default" :as EditScreen]
            ["/IntervalScreen$default" :as IntervalScreen]
            ["/HomeScreen$default" :as HomeScreen]
            
            [goog.object :as gobj]

            ))

(defn edit-screen [props]
  (let [props (merge

               ;; orig props
               props

               ;; db data map
               ;; id, title, duration, repeat
               @(rf/subscribe [:edit-screen/data (:navigation props)])

               ;; handlers fns
               ;; TODO
               ;;   - missing navigation
               {:edit-screen/newPressedHandler #(rf/dispatch [:edit-screen/new-pressed (:navigation props)])
                :edit-screen/savePressedHandler #(rf/dispatch [:edit-screen/save-pressed (:navigation props)])

                ;; key, id, title, duration, repeat come from :edit-screen/data
                :updateTitle #(rf/dispatch [:edit-screen/title-changed %])
                :updateDuration #(rf/dispatch [:edit-screen/duration-changed %])
                :updateRepeat #(rf/dispatch [:edit-screen/repeat-changed %])

                ;; EditScreen component props
                :deletePressedHandler #(rf/dispatch [:edit-screen/delete-pressed (:navigation props)])})]
    (def pe props)
    [:> EditScreen props]))

;; TODO level 2 component may save it?
(defn home-screen
  [props]
  #_(def p props)
  (fn [props])
  (let [interval-list-items @(rf/subscribe [:home-screen/interval-list-items (:navigation props)])
        props (merge props
                     {:home-screen/interval-list-items interval-list-items
                      :newPressedHandler #(rf/dispatch [:home-screen/new-pressed (:navigation props)])
                      
                      :test-text @(rf/subscribe [:sub.home-screen.test/text])
                      :on-change-test-text #(rf/dispatch-sync [:e.home-screen.test/text-changed %])})]
    (def ph props)
    [:> HomeScreen props]))



(defn interval-screen
  [props]
  (let [props (merge props
                     {
                      
                      ;; subs?
                      ;; or data 
                      :title @(rf/subscribe [:selected-timer.sub/title])
                      :timeStr @(rf/subscribe [:selected-timer.sub/time-str])
                      :running @(rf/subscribe [:selected-timer.sub/running])
                      ;; 
                      :startPressedHandler #(rf/dispatch [:interval-screen/start-pressed])
                      :stopPressedHandler #(rf/dispatch [:interval-screen/stop-pressed])
                      :resetPressedHandler #(rf/dispatch [:interval-screen/reset-pressed])})]
    #_(def p props)
    [:> IntervalScreen props]))

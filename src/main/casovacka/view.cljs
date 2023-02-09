(ns casovacka.view
  (:require [re-frame.core :as rf]
            ["/EditScreen" :refer [EditScreen]]
            ["/IntervalScreen" :refer [IntervalScreen]]
            ["/HomeScreen" :refer [HomeScreen]]))

(defn edit-screen
  [props]
  (let [props (merge
               ;; orig props
               props
               {;; props
                :title                @(rf/subscribe [:sub.edit-screen.selected-interval/title])
                :duration             @(rf/subscribe [:sub.edit-screen.selected-interval/duration])
                :repeat               @(rf/subscribe [:sub.edit-screen.selected-interval/repeat])
                :intervals            @(rf/subscribe [:sub.edit-screen.selected-interval/intervals (:navigation props)])
                ;; handlers
                :newPressedHandler    #(rf/dispatch [:e.edit-screen/new-pressed (:navigation props)])
                :deletePressedHandler #(rf/dispatch [:e.edit-screen/delete-pressed (:navigation props)])
                :savePressedHandler   #(rf/dispatch [:e.dit-screen/save-pressed (:navigation props)])
                :updateTitle          #(rf/dispatch [:e.edit-screen/title-changed %])
                :updateDuration       #(rf/dispatch [:e.edit-screen/duration-changed %])
                :updateRepeat         #(rf/dispatch [:e.edit-screen/repeat-changed %])})]
    (def pe props)
    [:> EditScreen props]))

(defn home-screen
  [props]
  (let [navigation (:navigation props)
        props (merge props
                     {:intervals         @(rf/subscribe [:sub.home-screen/intervals navigation])
                      :newPressedHandler #(rf/dispatch [:e.home-screen/new-pressed navigation])})]
    (def ph props)
    [:> HomeScreen props]))

(defn interval-screen
  [props]
  (let [props (merge props
                     {;; subs?
                      ;; or data 
                      :title               @(rf/subscribe [:selected-timer.sub/title])
                      :timeStr             @(rf/subscribe [:selected-timer.sub/time-str])
                      :running             @(rf/subscribe [:selected-timer.sub/running])
                      ;; 
                      :startPressedHandler #(rf/dispatch [:interval-screen/start-pressed])
                      :stopPressedHandler  #(rf/dispatch [:interval-screen/stop-pressed])
                      :resetPressedHandler #(rf/dispatch [:interval-screen/reset-pressed])})]
    (def pi props)
    [:> IntervalScreen props]))

(ns casovacka.db
  (:require [re-frame.core :as rf]))

(comment
  (-> @re-frame.db/app-db :selected-interval)
  )

;;; grammar
;;;   - toplevel no title means untitled
;;;   

(def rope-interval
  {:id "rope"
   :duration 600
   :title "Rope jumping"
   :intervals [{:title "Prepare"
                :duration 20}
               {:repeat 3
                :intervals [{:title "Jump"
                             :duration 150}
                            {:title "Rest"
                             :duration 30}]}
               {:title "Cooldown"
                ;; compute whats left to 600s, in this case 40
                }]})

(def basic-upper-body-interval
  {:id "basic"
   :title "Basic upper body"
   :intervals [{:duration 1200 ; 20min
                :title "Warmup"
                :intervals [{:repeat 2
                             :title "Rope Jump"
                             :intervals
                             [{:title "Jump"
                               :duration 300}
                              {:title "Rest"
                               :duration 30}]}
                            {:title "Dynamic Stretch"}]}
               {:title "Rest"
                :duration 60}
               {:repeat 4
                :intervals [{:title "Pull ups"
                             :intervals [{:duration 30}
                                         {:title "Rest"
                                          :duration 60}]}
                            {:title "Handstand"
                             :intervals [{:duration 30}
                                         {:title "Rest"
                                          :duration 60}]}
                            {:title "Dips"
                             :intervals [{:duration 30}
                                         {:title "Rest"
                                          :duration 60}]}
                            {:title "Push ups"
                             :intervals [{:duration 30}
                                         {:title "Rest"
                                          :duration 60}]}]}
               {:title "Rest"
                :duration 60}
               {:title "Get ready"
                :duration 10}
               {:title "Core"
                :repeat 3
                :intervals [{:title "Leg raises"
                             :intervals [{:duration 30}
                                         {:title "Rest"
                                          :duration 30}]}
                            {:title "Sit ups"
                             :intervals [{:duration 60}
                                         {:title "Rest"
                                          :duration 30}]}
                            {:title "Plank"
                             :intervals [{:duration 60}
                                         {:title "Rest"
                                          :duration 30}]}]}]})

(rf/reg-fx
 :nav
 (fn [[nav dest]]
   (.navigate nav dest)))

(def empty-db {:intervals {"basic" basic-upper-body-interval
                           "rope" rope-interval}})

(rf/reg-event-db
 ::initialize
 (fn [_ _] empty-db))


;;; go to screen
;;; home
;;;   - existing timers
;;; interval
;;;   - time where it has stopped

(comment
  (rf/clear-event :interval-selected)
  (rf/clear-event :home-screen/interval-selected)
  )

(rf/reg-event-fx
 :home-screen/interval-selected
 (fn [{:keys [db]} [_ interval-id navigation]]
   {:db (assoc db :selected-interval-id interval-id)
    ;; navigation `react navigation` object and interval is path to be reached
    :nav [navigation "interval"]}))

;;; tmp
(rf/reg-sub
 :selected-interval-title
 (fn [db _]
   (get-in db [:intervals (:selected-interval-id db) :title])))

(comment
  (-> @re-frame.db/app-db :selected-interval-id)
  (some #(when (= (:id %) (:selected-interval-id @re-frame.db/app-db)) %) (:intervals @re-frame.db/app-db))
)

(defn assoc-handler [interval navigation]
  (assoc interval :onPressHandler #(rf/dispatch [:home-screen/interval-selected (:id interval) navigation])))

(rf/reg-sub
 :home-screen/interval-list-items
 (fn [db [_ navigation]]
   (let [intervals (map #(select-keys % [:id :title]) (-> db :intervals vals))
         with-handlers (mapv #(assoc-handler % navigation) intervals)]
     with-handlers)))

(comment
  (rf/dispatch-sync [::initialize])
  )

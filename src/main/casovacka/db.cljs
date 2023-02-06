(ns casovacka.db
  (:require [goog.string :as gstr]
            [re-frame.core :as rf]))

(comment
  (-> @re-frame.db/app-db :selected-interval))

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
  (rf/clear-event :home-screen/interval-selected))

(rf/reg-event-fx
 :home-screen/interval-selected
 (fn [{:keys [db]} [_ interval-id navigation]]
   {:db (assoc db :selected-interval-id interval-id)
    ;; navigation `react navigation` object and interval is path to be reached
    :nav [navigation "interval"]}))

;;; tmp
(rf/reg-sub
 :selected-timer.sub/title
 (fn [db _]
   (get-in db [:intervals (:selected-interval-id db) :title])))

(comment
  (-> @re-frame.db/app-db :selected-interval-id)
  (some #(when (= (:id %) (:selected-interval-id @re-frame.db/app-db)) %) (:intervals @re-frame.db/app-db)))

(defn assoc-handler [interval navigation]
  (assoc interval :onPressHandler #(rf/dispatch [:home-screen/interval-selected (:id interval) navigation])))

(rf/reg-sub
 :home-screen/interval-list-items
 (fn [db [_ navigation]]
   (let [intervals (map #(select-keys % [:id :title]) (-> db :intervals vals))
         with-handlers (mapv #(assoc-handler % navigation) intervals)]
     with-handlers)))

(comment
  (rf/dispatch-sync [::initialize]))

;;; timer screen

(rf/reg-fx
 :start-timer
 (fn [start]
   (letfn [(tick! []
             (let [now (.now js/Date)
                   delta (- now start)
                   raf-id (js/requestAnimationFrame tick!)]
               (rf/dispatch [:tick delta raf-id])))]
     (tick!))))

(rf/reg-fx
 :stop-timer
 (fn [[raf-id events]]
   (js/cancelAnimationFrame raf-id)
   (doseq [e events] (rf/dispatch e))))


(rf/reg-event-db
 :stop-timer/stopped
 (fn [db _]
   (-> db
       (assoc :selected-timer/running false
              :selected-timer/current-ms 0
              :selected-timer/raf-id nil)
       (update :selected-timer/total-ms + (:selected-timer/current-ms db)))))

(rf/reg-event-db
 :stop-timer/reset
 (fn [db _]
   (assoc db :selected-timer/total-ms 0)))

(rf/reg-cofx
 :now
 (fn [cofx _]
   (assoc cofx :now (.now js/Date))))

(rf/reg-event-fx
 :interval-screen/start-pressed
 [(rf/inject-cofx :now)]
 (fn [{:keys [db now]} _]
   ;; after timer starts db should be update, not as here, prior to start or?
   {:db (assoc db
               :selected-timer/running true
               :selected-timer/current-ms 0)
    :start-timer now}))

(rf/reg-event-db
 :tick
 (fn [db [_ passed-ms raf-id]]
   (-> db
       (assoc :selected-timer/raf-id raf-id
              :selected-timer/current-ms passed-ms))))

;; update to pressed -> fx stop -> e stopped
(rf/reg-event-fx
 :interval-screen/stop-pressed
 (fn [{:keys [db]} _]
   {:stop-timer [(:selected-timer/raf-id db) [[:stop-timer/stopped]]]}))

(rf/reg-event-fx
 :interval-screen/reset-pressed
 (fn [{:keys [db]} _]
   {:stop-timer [(:selected-timer/raf-id db) [[:stop-timer/stopped] [:stop-timer/reset]]]}))


;; TODO variable length, hiding hn, hiding h, ...
(defn ms->timer-str [ms]
  (let [[h hr] [(quot ms 3600000) (rem ms 3600000)]
        [m mr] [(quot hr 60000) (rem hr 60000)]
        [s sr] [(quot mr 1000) (rem mr 1000)]
        ;; hn hundredth
        hn (quot sr 10)]
    ;; when updating on every frame, ui gets "stuck"
    (str (gstr/format #_"%d:%02d:%02d" "%d:%02d:%02d.%02d" h m s hn))))

(rf/reg-sub
 :selected-timer.sub/time-str
 (fn [db _]
   ;; TODO or probably redundant
   (ms->timer-str (+ (:selected-timer/total-ms db) (:selected-timer/current-ms db)))))

(rf/reg-sub
 :selected-timer.sub/running
 (fn [db _]
   (:selected-timer/running db)))

;;; new timer / interval

(rf/reg-event-fx
 :home-screen/new-pressed
 [(rf/inject-cofx :uuid)]
 (fn [{:keys [db uuid]} [_ navigation]]
   ;; empty path
   {:db (-> (assoc db
                   :edit-screen.selected-interval/path []
                   :edit-screen/selected-interval {:id uuid})
            (update :uuid/used #(conj (set %) uuid)))
    :nav [navigation "edit"]}))

;;; EDIT SCREEN

(defn avail-uuid [used]
  (loop [i 0
         uuid (str (random-uuid))]
    (cond (>= i 5)
          (js/Error. "Unable to generate unique uuid")

          ;; TODO -- valid condition for intent?
          (get used uuid)
          (recur (inc i) (str (random-uuid)))

          :else
          uuid)))

(rf/reg-cofx
 :uuid
 (fn [cofx _]
   (let [uuid (avail-uuid (-> cofx :db :uuid/used))]
     (assoc cofx :uuid uuid))))

(rf/reg-sub
 :edit-screen/data
 (fn [db _]
   ;; TODO
   ;; selected-interval-path
   (let [selected-interval-path (:edit-screen.selected-interval/path db)
         selected-interval (get-in (:edit-screen/selected-interval db) selected-interval-path)]
     ;; id, title, duration, repeat, intervals
     (-> (update selected-interval :intervals
                 (fn [intervals]
                   (map #(select-keys % [:id :title :duration :repeat])
                        intervals)))
         (assoc :key (:id selected-interval))))))


(defn flush-interval-edit [db]
  (let [{:keys [id] :as selected-interval} (:edit-screen/selected-interval db)]
    (assoc-in db [:intervals id] selected-interval)))

;;; TOPLEVEL save should not be diff from others...?
;;; view should provide different handling for toplevel? -- which does actual flush
;;; WHEN should we actually flush?

;; TODO
;; may do handling also here
(rf/reg-event-fx
 :edit-screen/save-pressed
 (fn [{:keys [db]} [_ from-js]]
   (let [{:keys [duration title repeat] :as upd} (js->clj from-js :keywordize-keys true)]
     ;; write new structure into db and do cleanup
     {:db (-> db
              ;; update current timer values
              ;; TODO use merge
              (update :edit-screen/selected-interval assoc
                      :title title
                      :duration duration
                      :repeat repeat)
              ;; if toplevel, flush
              (cond->
               (empty? (:edit-screen.selected-interval/path db))
                flush-interval-edit))})))


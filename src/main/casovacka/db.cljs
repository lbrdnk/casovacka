(ns casovacka.db
  (:require [goog.string :as gstr]
            [re-frame.core :as rf]
            [casovacka.util :as u]

            [goog.object :as gobj]))

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
 (fn [[^js nav op-kw & args]]
   (case op-kw
     :back (.goBack nav)
     :navigate (.navigate nav (first args))
     :push (.push nav (first args))
     :top (.popToTop nav))))
(comment)

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
    :nav [navigation :push "interval"]}))

;;; tmp
(rf/reg-sub
 :selected-timer.sub/title
 (fn [db _]
   (get-in db [:intervals (:selected-interval-id db) :title])))

(comment
  (-> @re-frame.db/app-db :selected-interval-id)
  (some #(when (= (:id %) (:selected-interval-id @re-frame.db/app-db)) %) (:intervals @re-frame.db/app-db)))

(defn assoc-handler [interval navigation]
  (assoc interval 
         :onPressHandler #(rf/dispatch [:home-screen/interval-selected (:id interval) navigation])
         :onEditHandler #(rf/dispatch [:home-screen/edit-interval-pressed (:id interval) navigation])))

;; TODO add press handlers for edit mode
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
   ;; initialized only if some interval not currently in prog of editing ie. not unsaved
   ;; save will clear
   {:db (cond-> db
          ((comp not contains?) db :edit-screen/selected-interval)
          (-> (assoc :edit-screen.selected-interval/path []
                     :edit-screen/selected-interval {:id uuid})
              (update :uuid/used #(conj (set %) uuid))))
    :nav [navigation :push "edit"]}))

;;;
;;; EDIT SCREEN
;;;

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

(declare path-to-interval)

(defn data-for-edit-screen [db _]
  ;; following line works only in case 
  (let [selected-interval-path (path-to-interval (:edit-screen.selected-interval/path db))
        selected-interval (get-in db selected-interval-path)]
     ;; key, id, title, duration, repeat, intervals
    (-> (update selected-interval :intervals
                (fn [intervals]
                  (->> (vals intervals)
                       ;; pick  
                       (mapv #(select-keys % [:id :title :duration :repeat]))
                       
                       )))
        (assoc :key (:id selected-interval)))))

(rf/reg-sub
 :edit-screen/data
 data-for-edit-screen)

(defn persist-edit-screen-data [db]
  (let [selected-interval (-> db :edit-screen/selected-interval)
        root-id (:id selected-interval)]
    (assoc-in db [:intervals root-id] selected-interval)))
(comment
  (-> @re-frame.db/app-db))

(defn clear-edit-screen-data [db]
  (dissoc db
          :edit-screen/selected-interval
          :edit-screen.selected-interval/path))

(rf/reg-event-fx
 :edit-screen/save-pressed
 (fn [{:keys [db]} [_ navigation]]
   {:db (-> db
            persist-edit-screen-data
            clear-edit-screen-data)
    :nav [navigation :top]}))

;; THIS SHALL be handling toplevel and lower level deletes
;; TODO
;;   use dissoc-in to make it more readable
(rf/reg-event-fx
 :edit-screen/delete-pressed
 (fn [{:keys [db]} [_ navigation]]
   (merge (let [id-path (:edit-screen.selected-interval/path db)]
            (if (empty? id-path)
              ;; killing toplevel interval, write to interval "store"
              {:db (let [root-id (-> db :edit-screen/selected-interval :id)]
                     (-> db
                         ;; idempotent
                         (update :intervals dissoc root-id)
                         clear-edit-screen-data))}
              ;; kill only branch of :edit-screen/selected-interval
              {:db (let [[parent-path [to-del-id]]
                         (split-at (-> id-path count dec) id-path)]
                     (update-in db
                                (conj (path-to-interval parent-path) :intervals)
                                dissoc to-del-id))}))
          {:nav [navigation :back]})))

;; TODO this also in home-screen/new-pressed
#_(defn clear-selected [db]
    (-> db
        (dissoc :edit-screen/selected-interval)
        (assoc-in [:edit-screen/selected-interval] nil)))


(defn path-to-interval [path-components]
  (reduce (fn [acc id] (into acc [:intervals id]))
          [:edit-screen/selected-interval]
          path-components))

;; LOOKS FINE
(rf/reg-event-fx
 :edit-screen/new-pressed
 [(rf/inject-cofx :uuid)]
 (fn [{:keys [db uuid]} [_ navigation]]
   ;; here bug
   (let [new-path (u/conjv (:edit-screen.selected-interval/path db) uuid)
         new-interval {:id uuid}]
     {:db (-> db
              (assoc :edit-screen.selected-interval/path new-path)
              (update :uuid/used u/conjs uuid)
              ;; here the path is wrong
              (assoc-in (path-to-interval new-path) new-interval))
      :nav [navigation :push "edit"]})))

;;;
;;; edit-screen
;;;   update fns
;;;

#_(comment
    (path-to-interval [:a :b :c]))

(defn assoc-selected-interval-key [db k v]
  (assoc-in db
            (u/conjv (path-to-interval (:edit-screen.selected-interval/path db)) k)
            v))

(rf/reg-event-db
 :edit-screen/title-changed
 (fn [db [_ val]]
   #_(def t val)
   #_(def x (-> val (gobj/getValueByKeys "nativeEvent" "text")))
   (assoc-selected-interval-key db :title val)))

(rf/reg-event-db
 :edit-screen/duration-changed
 (fn [db [_ val]]
   (assoc-selected-interval-key db :duration val)))

(rf/reg-event-db
 :edit-screen/repeat-changed
 (fn [db [_ val]]
   (assoc-selected-interval-key db :repeat val)))

;;;
;;; edit-screen.header
;;;

;; this has no access to components local state
;; hence should update on press into "tmp"
(rf/reg-event-db
 :edit-screen.header/back-pressed
 (fn [db _]
   (update db :edit-screen.selected-interval/path u/popv)))
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
  {:id "dd6ffe70-7075-4075-8aa1-74f0327ec4b6"
   :duration 600
   :title "Rope jumping"
   :intervals {"258b6fca-3f87-4829-9f47-8c0e0e7496a1"
               {:id "258b6fca-3f87-4829-9f47-8c0e0e7496a1"
                :title "Prepare"
                :duration 20}
               "a553f843-c549-4dc9-a869-9e80295398c2"
               {:id "a553f843-c549-4dc9-a869-9e80295398c2"
                :repeat 3
                :intervals {"3e617a78-1cf6-48fa-891d-530ea7f3b73f"
                            {:id "3e617a78-1cf6-48fa-891d-530ea7f3b73f"
                             :title "Jump"
                             :duration 150}
                            "1f3c6912-31e1-4b33-a7cd-ec401ebe84f0"
                            {:id "1f3c6912-31e1-4b33-a7cd-ec401ebe84f0"
                             :title "Rest"
                             :duration 30}}}
               "22f34830-6554-4b37-a459-e4a267c3bee8"
               {:id "22f34830-6554-4b37-a459-e4a267c3bee8"
                :title "Cooldown"
                ;; compute whats left to 600s, in this case 40
                }}})

(def basic-upper-body-interval
  {:id "60ecc631-33c9-4a5b-8b52-bff2eaf74ab7"
   :title "Basic upper body"
   :intervals {"c9411572-9a0c-44d5-b556-175e977114dd"
               {:id "c9411572-9a0c-44d5-b556-175e977114dd"
                :duration 1200 ; 20min
                :title "Warmup"
                :intervals {"1eb1e6f3-4d2a-4b31-ad91-fb8756694432"
                            {:id "1eb1e6f3-4d2a-4b31-ad91-fb8756694432"
                             :repeat 2
                             :title "Rope Jump"
                             :intervals
                             {"5f2de1d7-1dcb-42b2-bde3-98cb43e12bf9"
                              {:id "5f2de1d7-1dcb-42b2-bde3-98cb43e12bf9"
                               :title "Jump"
                               :duration 300}
                              "c5a610d6-5ce3-4d52-a02e-8ddc84082d8d"
                              {:id "c5a610d6-5ce3-4d52-a02e-8ddc84082d8d"
                               :title "Rest"
                               :duration 30}}}
                            "6d5c674c-1457-4d29-b796-638dbcaedd43"
                            {:id "6d5c674c-1457-4d29-b796-638dbcaedd43"
                             :title "Dynamic Stretch"}}}
               "7bb970ef-68d0-484a-b780-c0a61bb9289d"
               {:id "7bb970ef-68d0-484a-b780-c0a61bb9289d"
                :title "Rest"
                :duration 60}
               "645614b5-1b8e-4332-9b84-9a948550642d"
               {:id "645614b5-1b8e-4332-9b84-9a948550642d"
                :repeat 4
                :intervals {"2f1a563b-a4b5-427c-b0f3-eeb088829b5d"
                            {:id "2f1a563b-a4b5-427c-b0f3-eeb088829b5d"
                             :title "Pull ups"
                             :intervals {"614daa63-a6b5-48cb-b0c0-8592f3db1931"
                                         {:id "614daa63-a6b5-48cb-b0c0-8592f3db1931"
                                          :duration 30}
                                         "7f984084-9bba-41d3-86c4-fb2f4ed29ad2"
                                         {:id "7f984084-9bba-41d3-86c4-fb2f4ed29ad2"
                                          :title "Rest"
                                          :duration 60}}}
                            "516e8761-6946-4803-9b85-8fcc76e281d4"
                            {:id "516e8761-6946-4803-9b85-8fcc76e281d4"
                             :title "Handstand"
                             :intervals {"9a3841ad-7e9c-4e1a-a6e1-52e0d10ee410"
                                         {:id "9a3841ad-7e9c-4e1a-a6e1-52e0d10ee410"
                                          :duration 30}
                                         "2d6f3cce-a4b8-4fe6-b455-abb27b100c5c"
                                         {:id "2d6f3cce-a4b8-4fe6-b455-abb27b100c5c"
                                          :title "Rest"
                                          :duration 60}}}
                            "f37eedab-ca55-4710-9eb4-59bd9c0382f6"
                            {:id "f37eedab-ca55-4710-9eb4-59bd9c0382f6"
                             :title "Dips"
                             :intervals {"70443225-fd0d-483f-9a18-73e7472652ef"
                                         {:id "70443225-fd0d-483f-9a18-73e7472652ef"
                                          :duration 30}
                                         "980c195d-c8c9-4425-8e68-e00d67916362"
                                         {:id "980c195d-c8c9-4425-8e68-e00d67916362"
                                          :title "Rest"
                                          :duration 60}}}
                            "2b9a99f1-722c-4c85-ae55-8897815d135e"
                            {:id "2b9a99f1-722c-4c85-ae55-8897815d135e"
                             :title "Push ups"
                             :intervals {"53db5ac2-db94-453e-8fdd-51ae3ac2a84c"
                                         {:id "53db5ac2-db94-453e-8fdd-51ae3ac2a84c"
                                          :duration 30}
                                         "bff12ff8-ad7f-4919-b7da-c721f7955a2b"
                                         {:id "bff12ff8-ad7f-4919-b7da-c721f7955a2b"
                                          :title "Rest"
                                          :duration 60}}}}}
               "0e38780c-c4f5-40da-9d8f-b43a0f1aefe8"
               {:id "0e38780c-c4f5-40da-9d8f-b43a0f1aefe8"
                :title "Rest"
                :duration 60}
               "473fd38a-e354-43ee-90ac-2717c3e7c1af"
               {:id "473fd38a-e354-43ee-90ac-2717c3e7c1af"
                :title "Get ready"
                :duration 10}
               "b4602ed9-073f-41e2-9b8b-cf96f54d71c8"
               {:id "b4602ed9-073f-41e2-9b8b-cf96f54d71c8"
                :title "Core"
                :repeat 3
                :intervals {"b4f1fa7f-89f4-4f29-a105-93f9a813fa81"
                            {:id "b4f1fa7f-89f4-4f29-a105-93f9a813fa81"
                             :title "Leg raises"
                             :intervals {"f8353484-8841-4ce9-b38f-5623ea72dcca"
                                         {:id "f8353484-8841-4ce9-b38f-5623ea72dcca"
                                          :duration 30}
                                         "283bf768-6eb4-4ec9-b78b-8d62d4559bc1"
                                         {:id "283bf768-6eb4-4ec9-b78b-8d62d4559bc1"
                                          :title "Rest"
                                          :duration 30}}}
                            "a1c36681-0dc7-43ba-99ec-4aba16a0a43c"
                            {:id "a1c36681-0dc7-43ba-99ec-4aba16a0a43c"
                             :title "Sit ups"
                             :intervals {"0a66d81b-6b51-44a7-aa34-2ed078608bb9"
                                         {:id "0a66d81b-6b51-44a7-aa34-2ed078608bb9"
                                          :duration 60}
                                         "831f3793-8be4-4c51-8c94-4d47b7d42c69"
                                         {:id "831f3793-8be4-4c51-8c94-4d47b7d42c69"
                                          :title "Rest"
                                          :duration 30}}}
                            "230e1d86-3b49-455f-b1dd-3a28d5a2e313"
                            {:id "230e1d86-3b49-455f-b1dd-3a28d5a2e313"
                             :title "Plank"
                             :intervals {"09443065-d458-4bd8-b5d4-dd5acc87fc4a"
                                         {:id "09443065-d458-4bd8-b5d4-dd5acc87fc4a"
                                          :duration 60}
                                         "8efffff3-9af5-40ff-9267-516551782dd8"
                                         {:id "8efffff3-9af5-40ff-9267-516551782dd8"
                                          :title "Rest"
                                          :duration 30}}}}}}})

(comment
  (-> @re-frame.db/app-db :intervals)
  (rf/dispatch [::initialize])
  
  (-> @re-frame.db/app-db :intervals)
)

(rf/reg-fx
 :nav
 (fn [[^js nav op-kw & args]]
   (case op-kw
     :back (.goBack nav)
     :navigate (.navigate nav (first args))
     :push (.push nav (first args))
     :top (.popToTop nav))))


(def empty-db {:intervals {(:id basic-upper-body-interval) basic-upper-body-interval
                           (:id rope-interval) rope-interval}})

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

(declare clear-edit-screen-data)

(rf/reg-event-fx
 :home-screen/edit-interval-pressed
 (fn [{:keys [db]} [_ root-interval-id nav]]
   ;; TODO copy existing into :edit-screen/selected-interval
   (let [selected-interval (get-in db [:intervals root-interval-id])]
     {:db (-> db
              ;; also following line redundat?
              clear-edit-screen-data
              (assoc :edit-screen/selected-interval selected-interval
                     ;; TODO is following line redundant?
                     :edit-screen.selected-interval/path []))
      :nav [nav :push "edit"]})))

(comment
  (-> @re-frame.db/app-db :edit-screen/selected-interval)
  (-> @re-frame.db/app-db (get-in [:intervals "rope"]))
)

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

(defn data-for-edit-screen [db [_ navigation]]
  ;; following line works only in case 
  (let [selected-interval-path (path-to-interval (:edit-screen.selected-interval/path db))
        selected-interval (get-in db selected-interval-path)]
     ;; key, id, title, duration, repeat, intervals
    (-> (update selected-interval :intervals
                (fn [intervals]
                  (->> (vals intervals)
                       ;; pick  
                       (mapv #(select-keys % [:id :title :duration :repeat]))
                       
                       (mapv (fn [interval]
                               ;; navigation prop
                               (assoc interval 
                                      :pressedHandler #(rf/dispatch [:edit-screen/interval-pressed (:id interval) navigation])))
                             )
                       
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

(rf/reg-event-fx
 :edit-screen/interval-pressed
 (fn [{:keys [db]} [_ id navigation]]
   {:db (update db :edit-screen.selected-interval/path u/conjv id)
    :nav [navigation :push "edit"]}))

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
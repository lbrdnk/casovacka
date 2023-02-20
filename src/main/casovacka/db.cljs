(ns casovacka.db
  (:require [goog.string :as gstr]
            [re-frame.core :as rf]
            [casovacka.util :as u]

            [goog.object :as gobj]
            
            
            [casovacka.db.api :as db.api]
            [casovacka.db.util :as db.util]))

;;;
;;; init
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

(def empty-db {:intervals {(:id basic-upper-body-interval) basic-upper-body-interval
                           (:id rope-interval) rope-interval}})

(rf/reg-event-db
 ::initialize
 (fn [_ _] empty-db))

;;;
;;; cofx
;;;

(rf/reg-cofx
 :uuid
 (fn [cofx _]
   (let [uuid (u/avail-uuid (-> cofx :db :uuid/used))]
     (assoc cofx :uuid uuid))))

(rf/reg-cofx
 :now
 (fn [cofx _]
   (assoc cofx :now (.now js/Date))))

;;;
;;; fx
;;;

(rf/reg-fx
 :nav
 (fn [[^js nav op-kw & args]]
   (case op-kw
     :back (.goBack nav)
     :navigate (.navigate nav (first args))
     :push (.push nav (first args))
     :top (.popToTop nav))))

;;;
;;; interval screen
;;;

(rf/reg-event-fx
 :e.interval-screen.timer/start-pressed
 [(rf/inject-cofx :now)]
 (fn [{:keys [db now]} _]
   {:db (assoc db
               :interval-screen.timer/running true
               :interval-screen.timer/start-epoch now)}))

(rf/reg-event-db
 :e.interval-screen.timer/stop-pressed
 (fn [db [_ current-ms]]
   ;; following code does not cleanup `startEpoch`
   (-> db 
       (assoc :interval-screen.timer/running false)
       (update :interval-screen.timer/total-ms + current-ms))))

#_(rf/reg-event-db
 :e.interval-screen.timer/stopped
 (fn [db [_ current-ms]]
   (update db :interval-screen.timer/total-ms + current-ms)))

;;;
;;; sub.interval-screen
;;;

(rf/reg-sub
 :sub.interval-screen.timer/running
 (fn [db _]
   (-> db :interval-screen.timer/running boolean)))

(rf/reg-sub
 :sub.interval-screen.timer/start-epoch
 (fn [db _]
   (-> db :interval-screen.timer/start-epoch long)))

(rf/reg-sub
 :sub.interval-screen.timer/total-ms
 (fn [db _]
   (-> db :interval-screen.timer/total-ms long)))

(rf/reg-sub
 :sub.interval-screen.timer/title
 (fn [db _]
   (-> db :interval-screen.timer/root :title str)))


#_(rf/reg-fx
 :start-timer
 (fn [start]
   (letfn [(tick! []
             (let [now (.now js/Date)
                   delta (- now start)
                   raf-id (js/requestAnimationFrame tick!)]
               (rf/dispatch [:tick delta raf-id])))]
     (tick!))))

#_(rf/reg-fx
 :stop-timer
 (fn [[raf-id events]]
   (js/cancelAnimationFrame raf-id)
   (doseq [e events] (rf/dispatch e))))


#_(rf/reg-event-db
 :stop-timer/stopped
 (fn [db _]
   (-> db
       (assoc :selected-timer/running false
              :selected-timer/current-ms 0
              :selected-timer/raf-id nil)
       (update :selected-timer/total-ms + (:selected-timer/current-ms db)))))

#_(rf/reg-event-db
 :stop-timer/reset
 (fn [db _]
   (assoc db :selected-timer/total-ms 0)))

#_(rf/reg-event-fx
 :interval-screen/start-pressed
 [(rf/inject-cofx :now)]
 (fn [{:keys [db now]} _]
   ;; after timer starts db should be update, not as here, prior to start or?
   {:db (assoc db
               :selected-timer/running true
               :selected-timer/current-ms 0)
    :start-timer now}))

#_(rf/reg-event-db
 :tick
 (fn [db [_ passed-ms raf-id]]
   (-> db
       (assoc :selected-timer/raf-id raf-id
              :selected-timer/current-ms passed-ms))))

;; update to pressed -> fx stop -> e stopped
#_(rf/reg-event-fx
 :interval-screen/stop-pressed
 (fn [{:keys [db]} _]
   {:stop-timer [(:selected-timer/raf-id db) [[:stop-timer/stopped]]]}))

#_(rf/reg-event-fx
 :interval-screen/reset-pressed
 (fn [{:keys [db]} _]
   {:stop-timer [(:selected-timer/raf-id db) [[:stop-timer/stopped] [:stop-timer/reset]]]}))

#_(rf/reg-sub
 :selected-timer.sub/time-str
 (fn [db _]
   (db.util/ms->timer-str (+ (:selected-timer/total-ms db) (:selected-timer/current-ms db)))))

;;;
;;; e.home-screen
;;;

(rf/reg-event-fx
 :e.home-screen.intervals/edit-pressed
 (fn [{:keys [db]} [_ root-id navigation]]
   {:db (db.api/select-root-interval db root-id)
    :nav [navigation :push "edit"]}))

;;; TODO update to new structure
(rf/reg-event-fx
 :e.home-screen.intervals/select-pressed
 (fn [{:keys [db]} [_ interval-id navigation]]
   {:db (db.api/interval-select-root-interval db interval-id)
    :nav [navigation :push "interval"]}))

(rf/reg-event-fx
 :e.home-screen/new-pressed
 [(rf/inject-cofx :uuid)]
 (fn [{:keys [db uuid]} [_ navigation]]
   {:db (cond-> db
          ((comp not contains?) db :edit-screen/selected-interval)
          (-> (assoc :edit-screen.selected-interval/path-ids []
                     :edit-screen/selected-interval {:id uuid})
              (update :uuid/used #(conj (set %) uuid))))
    :nav [navigation :push "edit"]}))

;;;
;;; sub.home-screen
;;;

(rf/reg-sub
 :sub.home-screen/intervals
 (fn [db [_ navigation]]
   (let [intervals (-> db :intervals vals)]
     (mapv (fn [interval]
             (let [{:keys [id] :as interval} (select-keys interval [:id :title :duration :repeat])]
               (assoc interval
                      :key id
                      :selectPressedHandler #(rf/dispatch [:e.home-screen.intervals/select-pressed id navigation])
                      :editPressedHandler #(rf/dispatch [:e.home-screen.intervals/edit-pressed id navigation]))))
           intervals))))

;;;
;;; sub.edit-screen
;;;

(rf/reg-sub
 :sub.edit-screen.selected-interval/title
 (fn [db _]
   (db.api/from-selected-interval db :title)))

(rf/reg-sub
 :sub.edit-screen.selected-interval/duration
 (fn [db _]
   (db.api/from-selected-interval db :duration)))

(rf/reg-sub
 :sub.edit-screen.selected-interval/repeat
 (fn [db _]
   (db.api/from-selected-interval db :repeat)))

(rf/reg-sub
 :sub.edit-screen.selected-interval/intervals
 (fn [db [_ navigation]]
   (let [intervals (-> (db.api/from-selected-interval db :intervals) vals)]
     (mapv (fn [interval]
             (assoc interval
                    :pressedHandler #(rf/dispatch [:e.edit-screen.intervals/pressed (:id interval) navigation])
                    :key (:id interval)))
           intervals))))

;;;
;;; e.edit-screen
;;;

(rf/reg-event-fx
 :e.edit-screen/back-pressed
 (fn [{:keys [db]} [_ navigation]]
   {:db (-> db
            db.api/write-root-selected-interval
            (cond-> (= (-> db :edit-screen.selected-interval/path-ids count) 0)
              db.api/clear-edit-screen)
            (update :edit-screen.selected-interval/path-ids u/popv))
    :nav [navigation :back]}))

(rf/reg-event-fx
 :e.edit-screen/delete-pressed
 (fn [{:keys [db]} [_ navigation]]
   {:db (db.api/delete-selected-interval db)
    :nav [navigation :back]}))

(rf/reg-event-fx
 :e.edit-screen/new-pressed
 [(rf/inject-cofx :uuid)]
 (fn [{:keys [db uuid]} [_ navigation]]
   (let [new-path-ids (u/conjv (:edit-screen.selected-interval/path-ids db) uuid)
         new-path (db.api/path-to-interval :edit-screen/selected-interval new-path-ids)
         new-interval {:id uuid}]
     {:db (-> db
              (assoc :edit-screen.selected-interval/path-ids new-path-ids)
              (update :uuid/used u/conjs uuid)
              (assoc-in new-path new-interval))
      :nav [navigation :push "edit"]})))

(rf/reg-event-fx
 :e.edit-screen.intervals/pressed
 (fn [{:keys [db]} [_ id navigation]]
   {:db (update db :edit-screen.selected-interval/path-ids u/conjv id)
    :nav [navigation :push "edit"]}))

(rf/reg-event-db
 :e.edit-screen/title-changed
 (fn [db [_ val]]
   (db.api/assoc-selected-interval-key db :title val)))

(rf/reg-event-db
 :e.edit-screen/duration-changed
 (fn [db [_ val]]
   (db.api/assoc-selected-interval-key db :duration val)))

(rf/reg-event-db
 :e.edit-screen/repeat-changed
 (fn [db [_ val]]
   (db.api/assoc-selected-interval-key db :repeat val)))

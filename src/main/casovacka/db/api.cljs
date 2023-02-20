(ns casovacka.db.api
  (:require [casovacka.util :as u]))

(defn clear-edit-screen [db]
  (dissoc db
          :edit-screen/selected-interval
          :edit-screen.selected-interval/path-ids))

(defn path-to-interval [root path-ids]
  (reduce (fn [acc id] (into acc [:intervals id]))
          [root]
          path-ids))

(defn selected-interval-path [db]
  (let [path-components (:edit-screen.selected-interval/path-ids db)]
    (path-to-interval :edit-screen/selected-interval path-components)))

(defn selected-interval [db]
  (get-in db (selected-interval-path db)))

(defn selected-interval-parent-path [db]
  (let [path-ids (u/popv (:edit-screen.selected-interval/path-ids db))]
    (path-to-interval :edit-screen/selected-interval path-ids)))

(defn from-selected-interval [db key]
  (get (selected-interval db) key))

(defn write-root-selected-interval
  ":intervals
   :edit-screen/selected-interval"
  [db]
  (let [root (:edit-screen/selected-interval db)]
    (assoc-in db [:intervals (:id root)] root)))

(defn delete-selected-interval [db]
  (let [path-ids (:edit-screen.selected-interval/path-ids db)]
    (if (empty? path-ids)
      (let [root-id (get-in db [:edit-screen/selected-interval :id])]
        (-> db
            (update :intervals dissoc root-id)
            clear-edit-screen))
      (let [path-to-parent (selected-interval-parent-path db)
            selected-interval-id (from-selected-interval db :id)]
        (-> db
            (update-in (u/conjv path-to-parent :intervals) dissoc selected-interval-id)
            (update :edit-screen.selected-interval/path-ids u/popv)
            write-root-selected-interval))
      )))

(defn assoc-selected-interval-key [db k v]
  (assoc-in db
            (u/conjv (path-to-interval :edit-screen/selected-interval (:edit-screen.selected-interval/path-ids db)) k)
            v))

(defn select-root-interval [db root-id]
  (assoc db 
         :edit-screen/selected-interval (get-in db [:intervals root-id])
         :edit-screen.selected-interval/path-ids []))

;;;
;;; interval-screen
;;;

;; TODO divide modules into db.api.edit/, db.api.interval/

(defn interval-select-root-interval [db root-id]
  (assoc db :interval-screen.timer/root (get-in db [:intervals root-id])))
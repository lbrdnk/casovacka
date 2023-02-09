(ns casovacka.core
  (:require
   [casovacka.db :as db]

   ["react-native" :as rn]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [shadow.expo :as expo]
   
   ;; dev
   [casovacka.navigation :refer [navigation]]
   [casovacka.storybook :as sb]
   ))

(goog-define STORYBOOK false)

(defonce functional-compiler (reagent.core/create-compiler {:function-components true}))
(reagent.core/set-default-compiler! functional-compiler)

(defn root []
  (if STORYBOOK
    [sb/with-storybook]
    [navigation]))

(defn start
  {:dev/after-load true}
  []
  (expo/render-root (r/as-element [#'root])))

(defn init []
  (rf/dispatch-sync [::db/initialize])
  (start))

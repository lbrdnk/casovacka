(ns casovacka.dev
  #_(:require [shadow.cljs.devtools.api :as shadow]))

(defonce navigation-state (atom nil))

(comment
  ;; following must be called from cljs repl
  shadow/with-runtime
  shadow/watch-set-autobuild!
  )
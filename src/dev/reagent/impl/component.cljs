(ns reagent.impl.component
  (:require [reagent.impl.util :as util]))

#_#_
(declare react-class? as-class)
(defn reactify-component [comp compiler]
  (prn "cicina")
  (if (react-class? comp)
    comp
    (as-class comp compiler)))
#_#_
(declare assert-callable  react-class? warn-unless reagent-class? comp-name cache-react-class create-class)
(defn fn-to-class [compiler f]
  (def called 1
    )
  (assert-callable f)
  (warn-unless (not (and (react-class? f)
                         (not (reagent-class? f))))
               "Using native React classes directly in Hiccup forms "
               "is not supported. Use create-element or "
               "adapt-react-class instead: " (or (util/fun-name f)
                                                 f)
               (comp-name))
  (if (reagent-class? f)
    (cache-react-class compiler f f)
    (let [spec (meta f)
          withrender (assoc spec :reagent-render f)
          res (create-class withrender compiler)]
      (cache-react-class compiler f res))))
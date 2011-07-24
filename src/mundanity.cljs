(ns mundanity
  (:require [goog.global :as global]
            [goog.graphics :as gfx]
            [goog.events :as events]))

(def game-window (gfx/createGraphics 200 150))

(def square-fill (gfx/SolidFill. "yellow"))
(def square-stroke (gfx/Stroke. 2 "green"))
(def dot-fill (gfx/SolidFill. "blue"))
(def dot-stroke (gfx/Stroke. 1 "black"))

(def dot (atom
          {:x 1 :y 1}))

(def size 40)
(def margin 5)
(def width (- size margin))
(def num-rows 3)
(def num-cols 4)

(dotimes [x num-cols]
  (fn [] (dotimes [y num-rows]
           (fn [] (.drawRect game-window
                            (-> size (* x) (+ margin))
                            (-> size (* y) (+ margin))
                            width
                            width
                            square-stroke
                            square-fill)))))

(swap! dot (assoc :graphic
             (.drawEllipse game-window
                           (-> (:x @dot) (* size) (+ margin (/ width 2)))
                           (-> (:y @dot) (* size) (+ margin (/ width 2)))
                           (/ width 4)
                           (/ width 4)
                           (dot-stroke)
                           (dot-fill))))

(defn redraw-dot []
  (swap! dot (update-in
              :graphic
              (.setCenter 
               (-> (:x @dot)
                   (* size)
                   (+ margin (/ width 2)))
               (-> (:y @dot)
                   (* size)
                   (+ margin (/ width 2)))))))

(def key-codes events/KeyCodes)

(def key-handler (events/KeyHandler. global/document))
(defn key-event [e]
  (let [key (.keyCode e)]
    (cond
     (and (= key key-codes/UP)
          (> (:y @dot) 0))
     (swap! dot (update-in :y dec))
     (and (= key key-codes/RIGHT)
          (<= (:x @dot) (- num-cols 2)))
     (swap! dot (update-in :x inc))
     (and (= key key-codes/DOWN)
          (<= (:y @dot) (- num-rows 2)))
     (swap! dot (update-in :y inc))
     (and (= key key-codes/LEFT)
          (> (:x @dot) 0))
     (swap! dot (update-in :x dec)))
    (redraw-dot)))

(events/listen key-handler "key" key-event)
(.render game-window (.getElementById global/document "window"))
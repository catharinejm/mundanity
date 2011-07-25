(ns mundanity
  (:require [goog.global :as global]
            [goog.graphics :as gfx]
            [goog.events :as events]
            [goog.events.KeyHandler :as KeyHandler]
            [goog.events.KeyCodes :as KeyCodes]))

(def game-window (gfx/createGraphics 200 150))

(def square-fill (gfx/SolidFill. "yellow"))
(def square-stroke (gfx/Stroke. 2 "green"))
(def dot-fill (gfx/SolidFill. "blue"))
(def dot-stroke (gfx/Stroke. 1 "black"))

(def dot (atom {:x 1 :y 1}))

(def size 40)
(def margin 5)
(def width (- size margin))
(def num-rows 3)
(def num-cols 4)

(dotimes [x num-cols]
  (dotimes [y num-rows]
    (.drawRect game-window
               (-> size (* x) (+ margin))
               (-> size (* y) (+ margin))
               width
               width
               square-stroke
               square-fill)))

(defn init-graphic [dot-state]
  (assoc dot-state :graphic
         (.drawEllipse game-window
                       (-> (:x dot-state) (* size) (+ margin (/ width 2)))
                       (-> (:y dot-state) (* size) (+ margin (/ width 2)))
                       (/ width 4)
                       (/ width 4)
                       dot-stroke
                       dot-fill)))

(swap! dot init-graphic)

(defn redraw-dot []
  (let [x (:x @dot)
        y (:y @dot)
        half-width (/ width 2)
        new-x (+ margin (* x size) half-width)
        new-y (+ margin (* y size) half-width)]
    (.setCenter (:graphic @dot) new-x new-y)))

(def key-handler (events/KeyHandler. global/document))
(defn key-event [e]
  (let [key (.keyCode e)]
    (cond
     (and (= key KeyCodes/UP)
          (> (:y @dot) 0))
     (swap! dot update-in [:y] dec)
     (and (= key KeyCodes/RIGHT)
          (<= (:x @dot) (- num-cols 2)))
     (swap! dot update-in [:x] inc)
     (and (= key KeyCodes/DOWN)
          (<= (:y @dot) (- num-rows 2)))
     (swap! dot update-in [:y] inc)
     (and (= key KeyCodes/LEFT)
          (> (:x @dot) 0))
     (swap! dot update-in [:x] dec))
    (redraw-dot)))

(events/listen key-handler "key" key-event)
(.render game-window (.getElementById global/document "window"))
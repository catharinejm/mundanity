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

(def dot (.drawEllipse game-window
                       (+ margin (/ width 2))
                       (+ margin (/ width 2))
                       (/ width 4)
                       (/ width 4)
                       dot-stroke
                       dot-fill)) 

(defn dot-attr [attr]
  (.. dot (getElement) (getAttribute attr)))

(defn dot-x []
  (global/parseFloat (dot-attr "cx")))

(defn dot-y []
  (global/parseFloat (dot-attr "cy")))

(defn update-x [step]
  (let [cur-x (dot-x)
        new-x (+ cur-x step)]
    (if (and (< new-x (* num-cols size))
             (> new-x 0))
      new-x
      cur-x)))

(defn update-y [step]
  (let [cur-y (dot-y)
        new-y (+ cur-y step)]
    (if (and (< new-y (* num-rows size))
             (> new-y 0))
      new-y
      cur-y)))

(defn redraw-dot [x-step y-step]
  (.setCenter dot (update-x x-step) (update-y y-step)))

(def movement-x
  {KeyCodes/RIGHT size
   KeyCodes/LEFT (* size -1)})

(def movement-y
  {KeyCodes/UP (* size -1)
   KeyCodes/DOWN size})

(def key-handler (events/KeyHandler. global/document))

(defn key-event [e]
  (let [key (.keyCode e)]
    (redraw-dot (movement-x key) (movement-y key))))

(events/listen key-handler "key" key-event)
(.render game-window (.getElementById global/document "window"))
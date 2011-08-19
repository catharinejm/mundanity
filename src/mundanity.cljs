(ns mundanity
  (:require [goog.global :as global]
            [goog.graphics :as gfx]
            [goog.events :as events]
            [goog.events.KeyHandler :as KeyHandler]
            [goog.events.KeyCodes :as KeyCodes]))

(def game-window (gfx/createGraphics 800 600))

(def scr-height 800)
(def scr-width 600)
(def scr-ratio (/ scr-width scr-height))
(def fov-x (/ Math/PI 4))
(def fov-y (/ fov-x scr-ratio))
(def horizon 0.1)
(def z-min (/ scr-width 2.0 (Math/tan (/ fov-x 2.0))))
(def z-max (/ (* z-min (/ scr-height 2.0)) horizon))

(defn screen-x [wx wz]
  (+ (/ (* wx z-min) wz)
      (/ scr-width 2.0)))

(defn screen-y [wy wz]
  (- (/ scr-height 2.0)
     (* (- wy half-sht)
        (/ z-min wz))))

(defn world-x
  ([sx sy] (world-x sx sy 0))
  ([sx sy wy]
     (let [half-sht (/ scr-height 2.0)
           half-swd (/ scr-width 2.0)]
       (/ (* (- sx half-swd)
             (- wy half-sht))
          (- half-sht sy)))))

(defn world-z
  ([sy] (world-z sy 0))
  ([sy wy]
     (let [half-sht (/ scr-height 2.0)]
       (/ (* z-min (- wy half-sht))
          (- half-sht sy)))))

(.drawRect game-window
           0 300
           800 300
           (gfx/Stroke. 1 "blue")
           (gfx/SolidFill. "blue"))

(.render game-window (.getElementById global/document "window"))
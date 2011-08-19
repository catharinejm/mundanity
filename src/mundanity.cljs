(ns mundanity
  (:require [goog.global :as global]
            [goog.graphics :as gfx]
            [goog.events :as events]
            [goog.events.KeyHandler :as KeyHandler]
            [goog.events.KeyCodes :as KeyCodes]))

(def game-window (gfx/createGraphics 800 600))

(.drawRect game-window
           0 300
           800 300
           (gfx/Stroke. 1 "black")
           (gfx/SolidFill. "blue"))

(.render game-window (.getElementById global/document "window"))
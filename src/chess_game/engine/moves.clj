(ns chess-game.engine.moves
  (:require [chess-game.engine.util :as util]))

(def north 8)
(def northeast 9)
(def east 1)
(def southeast -7)
(def south -8)
(def southwest -9)
(def west -1)
(def northwest 7)
(def notAFile 0xfefefefefefefefe)
(def notHFile 0x7f7f7f7f7f7f7f7f)

(defn generalized-shift [bboard shift]
  "Shifts left for positive amounts, but right for negative amounts."
  (if (> shift 0)
    (bit-shift-left bboard shift)
    (bit-shift-right bboard shift)))

(defn single-pawn-push-targets [pawn-push] )

(defn possible-pawn-moves :white
  [history bit-boards start-pos color]
  (let [pawn-bboard (:p bit-boards)
        empty-bboard (:non-occupied bit-boards)
        push-per-side (util/intersection pawn-bboard (generalized-shift empty-bboard north))]))

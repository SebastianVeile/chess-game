(ns chess-game.engine.util)

;; Bitwise operations

(defn most-significant-bit [bboard]

  )

(defn intersection [bboard1 bboard2]
  "Intersection of two bitboards"
  (bit-and bboard1 bboard2))

(defn union [bboard1 bboard2]
  "Union of two bitboards"
  (bit-or bboard1 bboard2))

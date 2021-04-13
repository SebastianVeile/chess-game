(ns chess-game.engine.board
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))

(def boards (edn/read-string (slurp (io/resource "boards.edn"))))

;; the vector starts from top-left, meaning the index corresponds with position
;; For bit-board it is similar, but in the opposite direction. This means that:
;; :Q in bit-vector at index 60
;; :Q in vector at index 3

(defn board->bit [board]
  (let [upd-bit (fn [bit-board pos piece]
                  (update bit-board piece #(if % (bit-set % pos) (bit-set 0 pos))))
        upd-bit&pos (fn [[bit-board pos] piece]
                      (case piece
                        :- [bit-board (dec pos)]
                        [(upd-bit bit-board pos piece) (dec pos)]))
        bit&pos [{} 63]]
    (first
      (reduce upd-bit&pos bit&pos board))))

; Have made an assumption that there can max be one piece in each position.
; If there is more than one piece in the same position it is random which one
; gets to stay there. It depends on the order it is gotten from the hashmap.

(defn bit->board [bbit]
  (let [piece-at-pos? #(-> (bbit %) (unsigned-bit-shift-right %2) (bit-and 1) (= 1))]
    (for [pos (take 64 (iterate dec 63))
          :let [piece-at-pos (filterv #(piece-at-pos? % pos) (keys bbit))]
          :let [piece (if (empty? piece-at-pos) :- (piece-at-pos 0))]]
      piece)))

(defn starting-board
  []
  (board->bit (:initial-setup boards)))

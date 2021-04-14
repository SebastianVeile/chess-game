(ns chess-game.engine.board
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))

(def ^:private boards (edn/read-string (slurp (io/resource "boards.edn"))))

(defn board->bit
  "Converts a vector-board into its bit-board representation.

  A note on an implementation detail. The vector-board is indexed
  from top-left (0) to bottom-right (63) of the board. For the
  bit-board it is in the opposite direction, hence it is indexed
  from bottom-right (0) to top-left (63). For example
  :Q in bit-board at index 60
  :Q in vector-board at index 3"
  [board]
  (let [upd-bit (fn [bit-board pos piece]
                  (update bit-board piece #(if % (bit-set % pos) (bit-set 0 pos))))
        upd-bit&pos (fn [[bit-board pos] piece]
                      (case piece
                        :- [bit-board (dec pos)]
                        [(upd-bit bit-board pos piece) (dec pos)]))
        bit&pos [{} 63]]
    (first
      (reduce upd-bit&pos bit&pos board))))

(defn bit->board
  "Converts a bit-board into its vector-board representation.

  The implementation assumes there is only one piece in each position.
  If there is more than one piece in the same position it is random
  which one will be printed. It depends on the order it is taken from
  the bit-board map."
  [bbit]
  (let [piece-at-pos? #(-> (bbit %) (unsigned-bit-shift-right %2) (bit-and 1) (= 1))]
    (vec (for [pos (take 64 (iterate dec 63))
               :let [piece-at-pos (filterv #(piece-at-pos? % pos) (keys bbit))]
               :let [piece (if (empty? piece-at-pos) :- (piece-at-pos 0))]]
           piece))))

(defn pprint
  [board]
  "Prints the board to stdout with its rank and file shown.

  This is mainly meant for debugging purposes."
  (doseq [:let [board (bit->board board)]
           start (range 0 57 8)
           :let [row (subvec board start (+ start 8))
                 rank (- 8 (/ start 8))]]
     (print rank " ")
     (println row))
  (print "     ")
  (doseq [file (take 8 (iterate #(-> % int inc char) \a))]
    (print file " "))
  (newline))

(defn starting-board []
  (board->bit (:initial-setup boards)))
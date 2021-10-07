(ns chess-game.engine.board
  "Contains the bit-board representation and functionality to convert other
  representations into bit-board.

  Functionality like printing the board to is also contained here."
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))

(def boards (edn/read-string (slurp (io/resource "boards.edn"))))
63 62 61 60 59 58 57 56
55 54 53 52 51 50 49 48
47 46 45 44 43 42 41 40
39 38 37 36 35 34 33 32
31 30 29 28 27 26 25 24
23 22 21 20 19 18 17 16
15 14 13 12 11 10 09 08
07 06 05 04 03 02 01 00
(defn- upd-board
  [board pos p]
  (let [p-str (if (keyword? p) (name p) (str p))
        white? (every? #(Character/isUpperCase ^char %) p-str)
        color (if white? :white :black)
        piece (keyword p-str)
        upd-pos #(if % (bit-set % pos) (bit-set 0 pos))]
    (-> board
        (update-in [:occupancy color] upd-pos)
        (update-in [:occupancy :all] upd-pos)
        (update-in [color piece] upd-pos))))

(defn vector->board
  "Converts a vector-board into its bit-board representation.

  A note on an implementation detail. The vector-board is indexed
  from top-left (0) to bottom-right (63) of the board. For the
  bit-board it is in the opposite direction, hence it is indexed
  from bottom-right (0) to top-left (63). For example
  :Q in bit-board at index 60
  :Q in vector-board at index 3"
  [board]
  (let [upd-bit&pos (fn [[bit-board pos] piece]
                      (case piece
                        :- [bit-board (dec pos)]
                        [(upd-board bit-board pos piece) (dec pos)]))
        bit&pos [{} 63]]
    (first
      (reduce upd-bit&pos bit&pos board))))

(defn board->vector
  "Converts a bit-board into its vector-board representation.

  The implementation assumes there is only one piece in each position.
  If there is more than one piece in the same position it is random
  which one will be printed. It depends on the order it is taken from
  the bit-board map."
  [board]
  (let [ps-board (merge (:white board) (:black board))
        piece-at-pos? #(-> (ps-board %) (unsigned-bit-shift-right %2) (bit-and 1) (= 1))]
    (vec (for [pos (take 64 (iterate dec 63))
               :let [piece-at-pos (filterv #(piece-at-pos? % pos) (keys ps-board))]
               :let [piece (if (empty? piece-at-pos) :- (piece-at-pos 0))]]
           piece))))

(defn pprint
  "Prints the board to stdout with its rank and file shown.

  This is mainly meant for debugging purposes."
  [board]
  (doseq [:let [board (board->vector board)]
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
  (vector->board (:initial-setup boards)))

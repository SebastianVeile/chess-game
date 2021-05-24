(ns chess-game.engine.board
  "Contains the bit-board representation and functionality to convert other
  representations into bit-board.

  Functionality like printing the board to is also contained here."
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))

(def boards (edn/read-string (slurp (io/resource "boards.edn"))))

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

(def empty_board "8/8/8/8/8/8/8/8") ;  w - -
(def start_position "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR") ; w KQkq - 0 1
(def tricky_position "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R") ;  w KQkq - 0 1
(def killer_position "rnbqkb1r/pp1p1pPp/8/2p1pP2/1P1P4/3P3P/P1P1P3/RNBQKBNR") ;  w KQkq e6 0 1
(def cmk_position "r2q1rk1/ppp2ppp/2n1bn2/2b1p3/3pP3/3P1NPP/PPP1NPB1/R1BQ1RK1") ;  b - - 0 9

(defn FEN->bit
  [FEN]
  (let [upd-bit (fn [bit-board pos piece]
                  (update bit-board piece #(if % (bit-set % pos) (bit-set 0 pos))))
        f (fn [[bit-board pos] piece]
            (cond (Character/isDigit piece)
                  [bit-board (- pos (Character/digit piece 10))]
                  (= piece \/)
                  [bit-board pos]
                  :else
                  [(upd-bit bit-board pos (keyword (str piece))) (dec pos)]))]
    (first
      (reduce f [{} 63] FEN))))

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

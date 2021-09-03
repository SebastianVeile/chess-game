(ns chess-game.engine.board
  "Contains the bit-board representation and functionality to convert other
  representations into bit-board.

  Functionality like printing the board to is also contained here."
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))

(def boards (edn/read-string (slurp (io/resource "boards.edn"))))

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
  [vector]
  (let [upd-bit&pos (fn [[bit-board pos] piece]
                      (case piece
                        :- [bit-board (dec pos)]
                        [(upd-board bit-board pos piece) (dec pos)]))
        bit&pos [{} 63]]
    (first
      (reduce upd-bit&pos bit&pos vector))))

(def empty_board "8/8/8/8/8/8/8/8") ;  w - -
(def start_position "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR") ; w KQkq - 0 1
(def tricky_position "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R") ;  w KQkq - 0 1
(def killer_position "rnbqkb1r/pp1p1pPp/8/2p1pP2/1P1P4/3P3P/P1P1P3/RNBQKBNR") ;  w KQkq e6 0 1
(def cmk_position "r2q1rk1/ppp2ppp/2n1bn2/2b1p3/3pP3/3P1NPP/PPP1NPB1/R1BQ1RK1") ;  b - - 0 9

;; https://www.daniweb.com/programming/software-development/code/423640/unicode-chessboard-in-a-terminal

(defn FEN->board
  [FEN]
  (let [upd-bit&pos (fn [[bit-board pos] piece]
                      (cond (Character/isDigit ^char piece)
                            [bit-board (- pos (Character/digit ^char piece 10))]
                            (= piece \/)
                            [bit-board pos]
                            :else
                            [(upd-board bit-board pos piece) (dec pos)]))]
    (first
      (reduce upd-bit&pos [{} 63] FEN))))

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

;(defn board->FEN
;  [board]
;  (let [ps-board (board->vector board)
;        upd-fen&pos (fn [[FEN pos] piece]
;                      (cond (= (mod pos 8) 0)
;                            ))]))

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

(ns chess-game.engine.moves
  (:require [chess-game.engine.util :as util]))

(def not-A-file 0xfefefefefefefefe)
(def not-H-file 0x7f7f7f7f7f7f7f7f)

;;Directions
;; northwest    north   northeast
;;         +9    +8    +7
;;             \  |  /
;; west     1 <-  0 -> -1    east
;;             /  |  \
;;         -7    -8    -9
;; southwest    south   southeast

;;Bit-shift-left is for positive directions. Right is for negative directions
(defn north-one [bitboard]
  (bit-shift-left bitboard 8))

(defn south-one [bitboard]
  (bit-shift-right bitboard 8))

(defn east-one [bitboard]
  (bit-shift-right bitboard 1))

(defn west-one [bitboard]
  (bit-shift-left bitboard 1))

(defn north-east-one [bitboard]
  (bit-shift-right bitboard 9))

(defn south-east-one [bitboard]
  (bit-shift-left bitboard 7))

(defn north-west-one [bitboard]
  (bit-shift-left bitboard 9))

(defn south-west-one [bitboard]
  (bit-shift-right bitboard 7))

(defn white-possible-pawn-moves
  [history bitboards]
  (let [white-pawns-bboard (get-in bitboards [:white :P])
        black-pieces-bboard (get-in bitboards [:occupancy :black])

        ;;White attack diagonal right
        attacks-ne (bit-and (north-east-one white-pawns-bboard) black-pieces-bboard not-A-file)

        ;;White attack diagonal left
        attacks-nw (bit-and (north-west-one white-pawns-bboard) black-pieces-bboard not-H-file)

        ;;White push single

        ;;White push double

        ]))

(defn white-king-moves [bitboards]
  ;;White king can move in 8 different directions
  ; Numbers indicate the number of shifts for a given position
  ;
  ;
  ;  	  	 7 	 8 	 9
  ;  	  	-1 	 K 	 1
  ;  	  	-9 	-8 	-7
  ;
  ;
  ;
  (let [white-king-bboard (get-in bitboards [:white :K])
        white-pieces-bboard (get-in bitboards [:occupancy :white])
        black-pieces-bboard (get-in bitboards [:occupancy :black])

        ;;Clears board if king is on either the A or H.
        ;;Directions on the opposite site are still needed though
        ;;Therefore we make two separate variables.
        king-clip-file-A-bboard (bit-and white-king-bboard not-A-file)
        king-clip-file-H-bboard (bit-and white-king-bboard not-H-file)


        ;;Moves affected if the king occupies the A-file
        move-ne (bit-and (north-east-one white-king-bboard) king-clip-file-A-bboard)
        move-nw (bit-and (north-west-one white-king-bboard) king-clip-file-A-bboard)
        move-w (bit-and (west-one white-king-bboard) king-clip-file-A-bboard)

        ;;Moves affected if the king occupies the H-file
        move-sw (bit-and (south-west-one white-king-bboard) king-clip-file-H-bboard)
        move-se (bit-and (south-east-one white-king-bboard) king-clip-file-H-bboard)
        move-e (bit-and (east-one white-king-bboard) king-clip-file-H-bboard)

        ;;Moves not affected by either A or H-file
        move-n (north-one white-king-bboard)
        move-s (south-one white-king-bboard)

        ;;Union all possible moves and remove moves where white already has pieces
        king-moves (bit-and (bit-or move-ne move-nw move-w
                           move-sw move-se move-e
                           move-n move-s) white-pieces-bboard)


        ]))

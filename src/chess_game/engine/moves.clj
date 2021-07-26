(ns chess-game.engine.moves)

"Bitboard where all bits are 1 except for the given file.
 Used to clip moves, so they wont jump to the other side of the board"
(def not-A-file (unchecked-long 0x7f7f7f7f7f7f7f7f))
(def not-H-file (unchecked-long 0xfefefefefefefefe))
(def not-G-file (unchecked-long 0xfdfdfdfdfdfdfdfd))        ;; Used for knights
(def not-B-file (unchecked-long 0xbfbfbfbfbfbfbfbf))        ;; Used for knights

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
  (bit-shift-left (unchecked-long bitboard) (unchecked-long 8)))

(defn south-one [bitboard]
  (unsigned-bit-shift-right (unchecked-long bitboard) (unchecked-long 8)))

(defn east-one [bitboard]
  (unsigned-bit-shift-right (unchecked-long bitboard) (unchecked-long 1)))

(defn west-one [bitboard]
  (bit-shift-left (unchecked-long bitboard) (unchecked-long 1)))

(defn north-east-one [bitboard]
  (bit-shift-left (unchecked-long bitboard) (unchecked-long 7)))

(defn south-east-one [bitboard]
  (unsigned-bit-shift-right (unchecked-long bitboard) (unchecked-long 9)))

(defn north-west-one [bitboard]
  (bit-shift-left (unchecked-long bitboard) (unchecked-long 9)))

(defn south-west-one [bitboard]
  (unsigned-bit-shift-right (unchecked-long bitboard) (unchecked-long 7)))

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
  "White king can move in 8 different directions


           1 	2  3
           8 	K  4
           7 	6  5


  "
  (let [white-king-bboard (get-in bitboards [:white :K])
        white-pieces-bboard (get-in bitboards [:occupancy :white])
        black-pieces-bboard (get-in bitboards [:occupancy :black])

        ;;Masks the board if the king is on either the A or H.
        ;;If the king is on A, we don't want to calculate positions to the left and vice versa.
        king-mask-file-A-bboard (bit-and white-king-bboard not-A-file)
        king-mask-file-H-bboard (bit-and white-king-bboard not-H-file)

        ;Sets a bit in all 8 direction if possible
        move-ne (north-east-one king-mask-file-H-bboard)
        move-n (north-one white-king-bboard)
        move-nw (north-west-one king-mask-file-A-bboard)
        move-w (west-one king-mask-file-A-bboard)
        move-sw (south-west-one king-mask-file-A-bboard)
        move-s (south-one white-king-bboard)
        move-se (south-east-one king-mask-file-H-bboard)
        move-e (east-one king-mask-file-H-bboard)

        ;;Union all possible moves and remove moves where white already has pieces
        king-moves (bit-and (bit-not white-pieces-bboard) (bit-or move-ne move-nw move-w
                                                                  move-sw move-se move-e
                                                                  move-n move-s))

        ;;Remove moves that will result in check.
        ]
    king-moves))





;;Knight directions
;         noNoWe    noNoEa
;            +17  +15
;             |     |
;noWeWe  +10__|     |__+6  noEaEa
;              \   /
;               >0<
;           __ /   \ __
;soWeWe -6    |     |   -10  soEaEa
;             |     |
;            -15  -17
;        soSoWe    soSoEa

(defn white-knight-moves [bitboards]
  "

          2 	  	3
      1 	  	  	  	4
              N
      8 	  	  	  	5
          7 	  	6


  "
  (let [white-knight-bboard (get-in bitboards [:white :K])
        white-pieces-bboard (get-in bitboards [:occupancy :white])
        black-pieces-bboard (get-in bitboards [:occupancy :black])

        ;;Clips A,B,G and H files to make sure the knight does not jump to the other side of the board
        ;;when we shift the bits
        knight-clip-A-files not-A-file
        knight-clip-H-files not-H-file
        knight-clip-A-B-files (bit-and not-A-file not-B-file)
        knight-clip-H-B-files (bit-and not-H-file not-B-file)
        knight-clip-A-G-files (bit-and not-A-file not-G-file)
        knight-clip-H-G-files (bit-and not-H-file not-G-file)

        ])
  )

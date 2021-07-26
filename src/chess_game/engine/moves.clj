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
  (bit-shift-right (unchecked-long bitboard) (unchecked-long 8)))

(defn east-one [bitboard]
  (bit-shift-right (unchecked-long bitboard) (unchecked-long 1)))

(defn west-one [bitboard]
  (bit-shift-left (unchecked-long bitboard) (unchecked-long 1)))

(defn north-east-one [bitboard]
  (bit-shift-left (unchecked-long bitboard) (unchecked-long 7)))

(defn south-east-one [bitboard]
  (bit-shift-right (unchecked-long bitboard) (unchecked-long 9)))

(defn north-west-one [bitboard]
  (bit-shift-left (unchecked-long bitboard) (unchecked-long 9)))

(defn south-west-one [bitboard]
  (bit-shift-right (unchecked-long bitboard) (unchecked-long 7)))

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
        _ (println white-king-bboard)
        _ (println not-A-file)
        _ (println not-H-file)
        ;;Clips board if king is on either the A or H.
        ;;Directions on the opposite site are still needed though
        ;;Therefore we make two separate variables.
        king-clip-file-A-bboard (bit-and white-king-bboard not-A-file)
        _ (println king-clip-file-A-bboard)
        king-clip-file-H-bboard (bit-and white-king-bboard not-H-file)
        _ (println king-clip-file-H-bboard)


        move-ne (north-east-one king-clip-file-H-bboard)
        _ (println move-ne)
        move-nw (north-west-one king-clip-file-A-bboard)
        _ (println move-nw)
        move-w (west-one king-clip-file-A-bboard)
        _ (println move-w)

        move-sw (south-west-one king-clip-file-A-bboard)
        _ (println move-sw)
        move-se (south-east-one king-clip-file-H-bboard)
        _ (println move-se)
        move-e (east-one king-clip-file-H-bboard)
        - (println move-e)

        ;;Moves not affected by either A or H-file
        move-n (north-one white-king-bboard)
        _ (println move-n)
        move-s (south-one white-king-bboard)
        _ (println move-s)

        _ (println (bit-or move-ne move-nw move-w
                           move-sw move-se move-e
                           move-n move-s))
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

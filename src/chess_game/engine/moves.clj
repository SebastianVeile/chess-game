(ns chess-game.engine.moves
  (:require [chess-game.engine.direction :as direction]))

"Bitboard where all bits are 1 except for the given file.
 Used to clip moves, so they wont jump to the other side of the board"
(def not-A-file (unchecked-long 0x7f7f7f7f7f7f7f7f))
(def not-H-file (unchecked-long 0xfefefefefefefefe))
(def not-AB-file (unchecked-long 0x3f3f3f3f3f3f3f3f))       ;; Used for knights
(def not-HG-file (unchecked-long 0xfcfcfcfcfcfcfcfc))       ;; Used for knights

;;Bit-shift-left is for positive directions. Right is for negative directions
#_(defn white-possible-pawn-moves
  [history bitboards]
  (let [white-pawns-bboard (get-in bitboards [:white :P])
        black-pieces-bboard (get-in bitboards [:occupancy :black])

        ;;White attack diagonal right
        attacks-ne (bit-and (direction/north-east-one white-pawns-bboard) black-pieces-bboard not-A-file)

        ;;White attack diagonal left
        attacks-nw (bit-and (direction/north-west-one white-pawns-bboard) black-pieces-bboard not-H-file)

        ;;White push single

        ;;White push double

        ]))

(defn white-king-moves [bitboards]
  "White king can move in 8 different directions


           1  2  3
           8  K  4
           7  6  5


  "
  (let [white-king-bboard (get-in bitboards [:white :K])
        white-pieces-bboard (get-in bitboards [:occupancy :white])
        black-pieces-bboard (get-in bitboards [:occupancy :black])

        ;;Masks the board if the king is on either the A or H.
        ;;If the king is on A, we don't want to calculate positions to the left and vice versa.
        king-mask-file-A-bboard (bit-and white-king-bboard not-A-file)
        king-mask-file-H-bboard (bit-and white-king-bboard not-H-file)

        ;Sets a bit in all 8 directions if possible
        move-ne (direction/north-east-one king-mask-file-H-bboard)
        move-n (direction/north-one white-king-bboard)
        move-nw (direction/north-west-one king-mask-file-A-bboard)
        move-w (direction/west-one king-mask-file-A-bboard)
        move-sw (direction/south-west-one king-mask-file-A-bboard)
        move-s (direction/south-one white-king-bboard)
        move-se (direction/south-east-one king-mask-file-H-bboard)
        move-e (direction/east-one king-mask-file-H-bboard)

        ;;Union all possible moves and remove moves where white already has pieces
        king-moves (bit-and (bit-not white-pieces-bboard) (bit-or move-ne move-nw move-w
                                                                  move-sw move-se move-e
                                                                  move-n move-s))

        ;;Remove moves that will result in check.
        ]
    king-moves))

(defn get-knight-moves [bitboards]
  "Knights can move in 8 different directions

          2       3
      1               4
              N
      8               5
          7       6


  "
  (let [;;There is no distinction between black and white knight moves,
        ;; therefore we only need to decide from what color we extract the bit boards
        white-knight-bboard (unchecked-long (get-in bitboards [:white :N]))
        white-pieces-bboard (unchecked-long (get-in bitboards [:occupancy :white]))

        ;;Calculates the position in all 8 directions
        knight-no-e-e (direction/north-east-east (bit-and white-knight-bboard not-HG-file))
        knight-no-no-e (direction/north-north-east (bit-and white-knight-bboard not-H-file))
        knight-no-no-w (direction/north-north-west (bit-and white-knight-bboard not-A-file))
        knight-no-w-w (direction/north-west-west (bit-and white-knight-bboard not-AB-file))
        knight-so-w-w (direction/south-west-west (bit-and white-knight-bboard not-AB-file))
        knight-so-so-w (direction/south-south-west (bit-and white-knight-bboard not-A-file))
        knight-so-so-e (direction/south-south-east (bit-and white-knight-bboard not-H-file))
        knight-so-e-e (direction/south-east-east (bit-and white-knight-bboard not-HG-file))

        ;;Union all possible moves and remove moves where white already has pieces
        knight-moves (bit-and (bit-not white-pieces-bboard)
                              (bit-or knight-no-e-e knight-no-no-e knight-no-no-w knight-no-w-w
                                      knight-so-e-e knight-so-so-e knight-so-so-w knight-so-w-w))
        ]
    knight-moves))

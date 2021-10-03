(ns chess-game.engine.moves
  (:require [chess-game.engine.direction :as direction]))

;;TODO
;;Rooks
;;Bishops
;;Queens
;;Promotion of pawn
;;Concept of check - and not being able to move intgvf o check as king
;;En Pessant
;;Castling

"Bitboard where all bits are 1 except for the given file.
 Used to clip moves, so they wont jump to the other side of the board"
(def not-A-file (unchecked-long 0x7f7f7f7f7f7f7f7f))
(def not-H-file (unchecked-long 0xfefefefefefefefe))
(def rank-3 (unchecked-long 0xff0000))
(def rank-6 (unchecked-long 0xff0000000000))
(def not-AB-file (unchecked-long 0x3f3f3f3f3f3f3f3f))       ;; Used for knights
(def not-HG-file (unchecked-long 0xfcfcfcfcfcfcfcfc))       ;; Used for knights
(def file-masks {0 (unchecked-long 0X101010101010101)       ; H
                 1 (unchecked-long 0X202020202020202)       ; G
                 2 (unchecked-long 0X404040404040404)       ; F
                 3 (unchecked-long 0X808080808080808)       ; E
                 4 (unchecked-long 0X1010101010101010)      ; D
                 5 (unchecked-long 0X2020202020202020)      ; C
                 6 (unchecked-long 0X4040404040404040)      ; B
                 7 (unchecked-long 0X8080808080808080)})
(def rank-masks {0 (unchecked-long 0Xff)                    ; 1
                 1 (unchecked-long 0Xff00)                  ; 2
                 2 (unchecked-long 0Xff0000)                ; 3
                 3 (unchecked-long 0Xff000000)              ; 4
                 4 (unchecked-long 0Xff00000000)            ; 5
                 5 (unchecked-long 0Xff0000000000)          ; 6
                 6 (unchecked-long 0Xff000000000000)        ; 7
                 7 (unchecked-long 0Xff00000000000000)})
(def diagonal-masks {0  (unchecked-long 0x1)
                     1  (unchecked-long 0x102)
                     2  (unchecked-long 0x10204)
                     3  (unchecked-long 0x1020408)
                     4  (unchecked-long 0x102040810)
                     5  (unchecked-long 0x10204081020)
                     6  (unchecked-long 0x1020408102040)
                     7  (unchecked-long 0x102040810204080)
                     8  (unchecked-long 0x204081020408000)
                     9  (unchecked-long 0x408102040800000)
                     10 (unchecked-long 0x810204080000000)
                     11 (unchecked-long 0x1020408000000000)
                     12 (unchecked-long 0x2040800000000000)
                     13 (unchecked-long 0x4080000000000000)
                     14 (unchecked-long 0x8000000000000000)})
(def anti-diagonal-masks {0  (unchecked-long 0X80)
                          1  (unchecked-long 0X8040)
                          2  (unchecked-long 0X804020)
                          3  (unchecked-long 0X80402010)
                          4  (unchecked-long 0X8040201008)
                          5  (unchecked-long 0x804020100804)
                          6  (unchecked-long 0x80402010080402)
                          7  (unchecked-long 0x8040201008040201)
                          8  (unchecked-long 0x4020100804020100)
                          9  (unchecked-long 0x2010080402010000)
                          10 (unchecked-long 0x1008040201000000)
                          11 (unchecked-long 0x804020100000000)
                          12 (unchecked-long 0x402010000000000)
                          13 (unchecked-long 0x201000000000000)
                          14 (unchecked-long 0x100000000000000)})

(def full-board-mask (unchecked-long 0xffffffffffffffff))


(defn find-white-pawn-moves [white-pawn-bboard all-pieces black-pieces]
  "White pawns can only move forward if there are no white and black pieces on those positions.
   White pawns can only attacks north-west or north-east if there is a black piece on that position

           2
        3  1  4
           P


   "
  (let [empty-slots (bit-not all-pieces)

        ;;Checks if the step in front of the pawn is not occupied by other pieces
        white-pawn-single-push (bit-and (direction/north-one white-pawn-bboard) empty-slots)

        ;;Checks if the pawn can move an additional step using white-pawn-single-push
        ;;If the pawn is on rank 3 and the space in front is free, it can move again.
        white-pawn-double-push (bit-and (direction/north-one (bit-and white-pawn-single-push rank-3)) empty-slots)

        ;;Union both to get all possible forward pawn moves
        white-pawn-push (bit-or white-pawn-single-push white-pawn-double-push)

        ;;White attack diagonal right
        attacks-ne (bit-and (direction/north-east-one white-pawn-bboard) not-A-file)

        ;;White attack diagonal left
        attacks-nw (bit-and (direction/north-west-one white-pawn-bboard) not-H-file)

        ;;Union both to get all possible attack moves
        white-attack-moves (bit-and black-pieces (bit-or attacks-ne attacks-nw))

        ;;TODO En passant moves
        ]

    ;;Union white pawn attacks and pushes
    (bit-or white-pawn-push white-attack-moves)))

(defn find-black-pawn-moves [black-pawn-bboard all-pieces white-pieces]
  "Black pawns can only move forward if there are no white and black pieces on those positions.
   Black pawns can only attacks north-west or north-east if there is a white piece on that position


           P
         4 1 3
           2


   "
  (let [empty-slots (bit-not all-pieces)

        ;;Checks if the step in front of the pawn is not occupied by other pieces
        black-pawn-single-push (bit-and (direction/south-one black-pawn-bboard) empty-slots)

        ;;Checks if the pawn can move an additional step based on the results of black-pawn-single-push
        ;;If the pawn is on rank 3 and the space in front is free, it can move again.
        black-pawn-double-push (bit-and (direction/south-one (bit-and black-pawn-single-push rank-6)) empty-slots)

        ;;Union both to get all possible forward pawn moves
        white-pawn-push (bit-or black-pawn-single-push black-pawn-double-push)

        ;;Black attack diagonal right
        attacks-ne (bit-and (direction/south-east-one black-pawn-bboard) not-A-file)

        ;;Black attack diagonal left
        attacks-nw (bit-and (direction/south-west-one black-pawn-bboard) not-H-file)

        ;;Union both to get all possible attack moves
        white-attack-moves (bit-and white-pieces (bit-or attacks-ne attacks-nw))

        ;;TODO En passant moves
        ]

    ;;Union white pawn attacks and pushes
    (bit-or white-pawn-push white-attack-moves)))

(defn find-king-moves [king-bboard own-pieces-bboard]
  "White king can move in 8 different directions


           1  2  3
           8  K  4
           7  6  5


  "
  (let [;;Uses a clip if the king is on either the A or H file.
        ;;If the king is on A, we don't want to calculate positions to the left and vice versa.
        king-clip-file-A-bboard (bit-and king-bboard not-A-file)
        king-clip-file-H-bboard (bit-and king-bboard not-H-file)

        ;Sets a bit in all 8 directions if possible
        move-ne (direction/north-east-one king-clip-file-H-bboard)
        move-n (direction/north-one king-bboard)
        move-nw (direction/north-west-one king-clip-file-A-bboard)
        move-w (direction/west-one king-clip-file-A-bboard)
        move-sw (direction/south-west-one king-clip-file-A-bboard)
        move-s (direction/south-one king-bboard)
        move-se (direction/south-east-one king-clip-file-H-bboard)
        move-e (direction/east-one king-clip-file-H-bboard)

        ;;Union all possible moves and remove moves where white already has pieces
        king-moves (bit-and (bit-not own-pieces-bboard) (bit-or move-ne move-nw move-w
                                                                move-sw move-se move-e
                                                                move-n move-s))

        ;;TODO Remove moves that will result in check.

        ]
    king-moves))

(defn find-knight-moves [knight-bboard own-pieces-bboard]
  "Knights can move in 8 different directions

          2       3
      1               4
              N
      8               5
          7       6


  "
  (let [;;Calculates the position in all 8 directions
        knight-no-e-e (direction/north-east-east (bit-and knight-bboard not-HG-file))
        knight-no-no-e (direction/north-north-east (bit-and knight-bboard not-H-file))
        knight-no-no-w (direction/north-north-west (bit-and knight-bboard not-A-file))
        knight-no-w-w (direction/north-west-west (bit-and knight-bboard not-AB-file))
        knight-so-w-w (direction/south-west-west (bit-and knight-bboard not-AB-file))
        knight-so-so-w (direction/south-south-west (bit-and knight-bboard not-A-file))
        knight-so-so-e (direction/south-south-east (bit-and knight-bboard not-H-file))
        knight-so-e-e (direction/south-east-east (bit-and knight-bboard not-HG-file))]

    ;;Union all possible moves and remove moves where white already has pieces
    (bit-and (bit-not own-pieces-bboard)
             (bit-or knight-no-e-e knight-no-no-e knight-no-no-w knight-no-w-w
                     knight-so-e-e knight-so-so-e knight-so-so-w knight-so-w-w))))

"Hyperbola Quintessence
for further understanding: https://www.youtube.com/watch?v=bCH4YK6oq8M

"

(defn- reverse-bits [l]
  (Long/reverse l))

(defn- calculate-ray-attacks [slider-piece occupancy]
  "Applying (o-2s) of o^(o-2s)"
  (->> (unchecked-long slider-piece)
       (*' (unchecked-long 2))
       (- occupancy)
       unchecked-long))                                     ;Safety measure to avoid overflow when reversing bits later

(defn- calculate-ray-moves [slider-piece occupancy rank-file]
  "Applying o^(o-2s) ^ o^(o'-2s')' - ' symbol represents reverse bits to calculate the other direction
  Can be reduced to (o-2s) ^ (o'-2s')' "
  (bit-and
    (bit-xor (calculate-ray-attacks slider-piece (bit-and occupancy rank-file))
             (reverse-bits
               (calculate-ray-attacks (reverse-bits slider-piece) (reverse-bits (bit-and occupancy rank-file)))))
    rank-file))

(defn find-rook-moves [^Integer rook-piece-pos own-pieces occupancy]
  "Hyperbola Quintessence using the o^(o-2r) trick
  o^(o-2r) : https://www.chessprogramming.org/Subtracting_a_Rook_from_a_Blocking_Piece
  Hyperbola Quintessence: https://www.chessprogramming.org/Hyperbola_Quintessence

  BE AWARE: This function can only take one rook-piece at the time. So to avoid giving multiple rooks, the function does not accept
  bitboards, but instead a position of a single rook eg. number of trailing zeros.
  Returns a bitboard with all possible moves for specified pos"
  (let [rook-piece-bitboard (unchecked-long (bit-shift-left 1 rook-piece-pos))
        horisontal-attacks (calculate-ray-moves rook-piece-bitboard occupancy (get rank-masks (quot rook-piece-pos 8)))
        vertical-attacks (calculate-ray-moves rook-piece-bitboard occupancy (get file-masks (mod rook-piece-pos 8)))]

    (bit-and (bit-or horisontal-attacks vertical-attacks)
             (bit-not own-pieces))))

(defn find-bishop-moves [^Integer bishop-piece-pos own-pieces occupancy]
  "Using same technique as rooks"
  (let [bishop-piece-bitboard (unchecked-long (bit-shift-left 1 bishop-piece-pos))
        diagonal-attacks (calculate-ray-moves bishop-piece-bitboard occupancy (get diagonal-masks (+ (quot bishop-piece-pos 8) (mod bishop-piece-pos 8))))
        anti-diagonal-attacks (calculate-ray-moves bishop-piece-bitboard occupancy (get anti-diagonal-masks (- (+ 7 (quot bishop-piece-pos 8)) (mod bishop-piece-pos 8))))]
    (bit-and (bit-or diagonal-attacks anti-diagonal-attacks)
             (bit-not own-pieces))))

(defn white-turn? [bitboards]
  (get-in bitboards [:history :turn]))


(defn get-moves [bitboards]
  "Just a temp function to illustrate that the king and knight functions are generic and works for both colors
  This is not true for the pawn functions."
  (if (white-turn? bitboards)
    (let [occupancy (:occupancy bitboards)
          white-pieces (:white occupancy)

          king-moves (find-king-moves (get-in bitboards [:white :K]) white-pieces)
          knight-moves (find-knight-moves (get-in bitboards [:white :N]) white-pieces)
          pawn-moves (find-white-pawn-moves (get-in bitboards [:white :P]) (:all occupancy) (:black occupancy))]
      (bit-or king-moves knight-moves pawn-moves))
    (let [occupancy (:occupancy bitboards)
          black-pieces (:black occupancy)

          king-moves (find-king-moves (get-in bitboards [:black :k]) black-pieces)
          knight-moves (find-knight-moves (get-in bitboards [:black :n]) black-pieces)
          pawn-moves (find-white-pawn-moves (get-in bitboards [:black :p]) (:all occupancy) (:white occupancy))]
      (bit-or king-moves knight-moves pawn-moves))))

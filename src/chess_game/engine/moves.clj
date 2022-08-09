(ns chess-game.engine.moves
  "Contains the functionality of generating all possible moves for all 6 piece types."
  (:require [chess-game.engine.direction :as direction]))

(def not-on-A-file (unchecked-long 0x7f7f7f7f7f7f7f7f))
(def not-on-H-file (unchecked-long 0xfefefefefefefefe))
(def on-rank-3 (unchecked-long 0xff0000))
(def on-rank-6 (unchecked-long 0xff0000000000))
(def not-on-AB-file (unchecked-long 0x3f3f3f3f3f3f3f3f))    ; Used for knights
(def not-on-HG-file (unchecked-long 0xfcfcfcfcfcfcfcfc))    ; Used for knights

(defn lookup-white-pawn-moves [white-pawn all-pieces black-pieces]
  "Given a bitboard representing one or multiple white pawns,
  a bitboard representing all-pieces, and a bitboard representing black-pieces,
  return a bitboard where all set bits represent a possible white-pawn move"

  (let [empty-slots (bit-not all-pieces)

        single-push (bit-and (direction/move-north-one white-pawn) empty-slots)

        ; If a pawn is on rank 3 after a single-push, it can move an additional step
        double-push (bit-and (direction/move-north-one (bit-and single-push on-rank-3)) empty-slots)

        attacks-diagonal-right (bit-and (direction/move-north-east-one white-pawn) not-on-A-file)
        attacks-diagonal-left (bit-and (direction/move-north-west-one white-pawn) not-on-H-file)
        combined-attack-moves (bit-and black-pieces (bit-or attacks-diagonal-right attacks-diagonal-left))]
    (bit-or single-push double-push combined-attack-moves)))

(defn lookup-black-pawn-moves [black-pawn all-pieces white-pieces]
  "Given a bitboard representing one or multiple black pawns,
  a bitboard representing all-pieces, and a bitboard representing white-pieces,
  return a bitboard where all set bits represent a possible black-pawn move"
  (let [empty-slots (bit-not all-pieces)
        single-push (bit-and (direction/move-south-one black-pawn) empty-slots)

        ; If a pawn is on rank 6 after a single-push, it can move an additional step
        double-push (bit-and (direction/move-south-one (bit-and single-push on-rank-6)) empty-slots)

        attacks-diagonal-right (bit-and (direction/move-south-east-one black-pawn) not-on-A-file)
        attacks-diagonal-left (bit-and (direction/move-south-west-one black-pawn) not-on-H-file)

        combined-attack-moves (bit-and white-pieces (bit-or attacks-diagonal-right attacks-diagonal-left))]
    (bit-or single-push double-push combined-attack-moves)))

(defn lookup-king-moves [king own-pieces]
  "Given a bitboard representing one or multiple kings and a bitboard representing all own-pieces
  return a bitboard where all set bits represent a possible king move"
  (let [king-not-on-A-file (bit-and king not-on-A-file)
        king-not-on-H-file (bit-and king not-on-H-file)

        ; Sets a bit in all 8 directions if possible
        move-north-east-one (direction/move-north-east-one king-not-on-H-file)
        move-north-one (direction/move-north-one king)
        move-north-west-one (direction/move-north-west-one king-not-on-A-file)
        move-west-one (direction/move-west-one king-not-on-A-file)
        move-south-west-one (direction/move-south-west-one king-not-on-A-file)
        move-south-one (direction/move-south-one king)
        move-south-east-one (direction/move-south-east-one king-not-on-H-file)
        move-east-one (direction/move-east-one king-not-on-H-file)

        combined-king-moves (bit-and (bit-or move-north-east-one move-north-west-one move-west-one
                                             move-south-west-one move-south-east-one move-east-one
                                             move-north-one move-south-one)
                                     (bit-not own-pieces))]
    combined-king-moves))

(defn lookup-knight-moves [knight-bboard own-pieces-bboard]
  "Given a bitboard representing one or multiple knights and a bitboard representing all own-pieces
  return a bitboard where all set bits represent a possible knight move"
  (let [knight-not-on-AB-file (bit-and knight-bboard not-on-AB-file)
        knight-not-on-HG-file (bit-and knight-bboard not-on-HG-file)
        knight-not-on-A-file (bit-and knight-bboard not-on-A-file)
        knight-not-on-H-file (bit-and knight-bboard not-on-H-file)

        move-north-east-east (direction/north-east-east knight-not-on-HG-file)
        move-north-north-east (direction/north-north-east knight-not-on-H-file)
        move-north-north-west (direction/north-north-west knight-not-on-A-file)
        move-north-west-west (direction/north-west-west knight-not-on-AB-file)
        move-south-west-west (direction/south-west-west knight-not-on-AB-file)
        move-south-south-west (direction/south-south-west knight-not-on-A-file)
        move-south-south-east (direction/south-south-east knight-not-on-H-file)
        move-south-east-east (direction/south-east-east knight-not-on-HG-file)]

    ; Union all possible moves and remove moves where own pieces are located
    (bit-and (bit-not own-pieces-bboard)
             (bit-or move-north-east-east move-north-north-east move-north-north-west move-north-west-west
                     move-south-east-east move-south-south-east move-south-south-west move-south-west-west))))

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

(defn find-queen-moves [^Integer queen-piece-pos own-pieces occupancy]
  "Queens just combine the bishop and rook calculations to find all possible moves"
  (bit-or (find-bishop-moves queen-piece-pos own-pieces occupancy)
          (find-rook-moves queen-piece-pos own-pieces occupancy)))

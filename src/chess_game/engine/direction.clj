(ns chess-game.engine.direction)

;;Directions
;; northwest    north   northeast
;;         +9    +8    +7
;;             \  |  /
;; west     1 <-  0 -> -1    east
;;             /  |  \
;;         -7    -8    -9
;; southwest    south   southeast
(defn north-one [bitboard]
  (bit-shift-left (unchecked-long bitboard) 8))

(defn south-one [bitboard]
  (unsigned-bit-shift-right (unchecked-long bitboard) 8))

(defn east-one [bitboard]
  (unsigned-bit-shift-right (unchecked-long bitboard) 1))

(defn west-one [bitboard]
  (bit-shift-left (unchecked-long bitboard) 1))

(defn north-east-one [bitboard]
  (bit-shift-left (unchecked-long bitboard) 7))

(defn south-east-one [bitboard]
  (unsigned-bit-shift-right (unchecked-long bitboard) 9))

(defn north-west-one [bitboard]
  (bit-shift-left (unchecked-long bitboard) 9))

(defn south-west-one [bitboard]
  (unsigned-bit-shift-right (unchecked-long bitboard) 7))


; ONLY USED BY KNIGHTS
; The naming represent a move in each direction -> North north east, would be two spaces up and one to the right
; Knight directions
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
(defn north-north-east [bitboard]
  (bit-shift-left (unchecked-long bitboard) 15))

(defn north-north-west [bitboard]
  (bit-shift-left (unchecked-long bitboard) 17))

(defn north-east-east [bitboard]
  (bit-shift-left (unchecked-long bitboard) 6))

(defn north-west-west [bitboard]
  (bit-shift-left (unchecked-long bitboard) 10))

(defn south-west-west [bitboard]
  (unsigned-bit-shift-right  (unchecked-long bitboard) 6))

(defn south-east-east [bitboard]
  (unsigned-bit-shift-right (unchecked-long bitboard) 10))

(defn south-south-west [bitboard]
  (unsigned-bit-shift-right (unchecked-long bitboard) 15))

(defn south-south-east [bitboard]
  (unsigned-bit-shift-right (unchecked-long bitboard) 17))

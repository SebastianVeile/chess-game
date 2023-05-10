(ns chess-game.engine.move-generation.direction)

;;Directions
;; northwest    north   northeast
;;         +9    +8    +7
;;             \  |  /
;; west     1 <-  0 -> -1    east
;;             /  |  \
;;         -7    -8    -9
;; southwest    south   southeast
(defn move-north-one [bitboard]
  (bit-shift-left (unchecked-long bitboard) 8))

(defn move-south-one [bitboard]
  (unsigned-bit-shift-right (unchecked-long bitboard) 8))

(defn move-east-one [bitboard]
  (unsigned-bit-shift-right (unchecked-long bitboard) 1))

(defn move-west-one [bitboard]
  (bit-shift-left (unchecked-long bitboard) 1))

(defn move-north-east-one [bitboard]
  (bit-shift-left (unchecked-long bitboard) 7))

(defn move-south-east-one [bitboard]
  (unsigned-bit-shift-right (unchecked-long bitboard) 9))

(defn move-north-west-one [bitboard]
  (bit-shift-left (unchecked-long bitboard) 9))

(defn move-south-west-one [bitboard]
  (unsigned-bit-shift-right (unchecked-long bitboard) 7))

(defn move-diagonal-right
  [bitboard color]
  (if (= color :white)
    (bit-shift-left bitboard 7)
    (bit-shift-right bitboard 9)))

(defn move-forward
  [bitboard color]
  (if (= color :white)
    (bit-shift-left bitboard 8)
    (bit-shift-right bitboard 8)))

(defn move-diagonal-left
  [bitboard color]
  (if (= color :white)
    (bit-shift-left bitboard 9)
    (bit-shift-right bitboard 7)))

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
  (unsigned-bit-shift-right (unchecked-long bitboard) 6))

(defn south-east-east [bitboard]
  (unsigned-bit-shift-right (unchecked-long bitboard) 10))

(defn south-south-west [bitboard]
  (unsigned-bit-shift-right (unchecked-long bitboard) 15))

(defn south-south-east [bitboard]
  (unsigned-bit-shift-right (unchecked-long bitboard) 17))

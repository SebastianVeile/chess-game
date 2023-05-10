(ns chess-game.engine.move-generation.constants)

(def not-on-A-file (unchecked-long 0x7f7f7f7f7f7f7f7f))
(def not-on-H-file (unchecked-long 0xfefefefefefefefe))
(def on-rank-3 (unchecked-long 0xff0000))
(def on-rank-6 (unchecked-long 0xff0000000000))
(def not-on-AB-file (unchecked-long 0x3f3f3f3f3f3f3f3f))    ; Used for knights
(def not-on-HG-file (unchecked-long 0xfcfcfcfcfcfcfcfc))    ; Used for knights
(def file-masks {0 (unchecked-long 0X101010101010101)       ; H
                 1 (unchecked-long 0X202020202020202)       ; G
                 2 (unchecked-long 0X404040404040404)       ; F
                 3 (unchecked-long 0X808080808080808)       ; E
                 4 (unchecked-long 0X1010101010101010)      ; D
                 5 (unchecked-long 0X2020202020202020)      ; C
                 6 (unchecked-long 0X4040404040404040)      ; B
                 7 (unchecked-long 0X8080808080808080)})    ; A
(def rank-masks {0 (unchecked-long 0Xff)                    ; 1
                 1 (unchecked-long 0Xff00)                  ; 2
                 2 (unchecked-long 0Xff0000)                ; 3
                 3 (unchecked-long 0Xff000000)              ; 4
                 4 (unchecked-long 0Xff00000000)            ; 5
                 5 (unchecked-long 0Xff0000000000)          ; 6
                 6 (unchecked-long 0Xff000000000000)        ; 7
                 7 (unchecked-long 0Xff00000000000000)})    ; 8
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

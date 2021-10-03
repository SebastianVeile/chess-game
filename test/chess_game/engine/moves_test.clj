(ns chess-game.engine.moves-test
  (:require [clojure.test :refer :all]
            [chess-game.engine.moves :as move]))

(deftest king-moves-test
  "Validating king moves from different positions"
  (testing "Testing king pos H4"
    (is (= (move/find-king-moves 16777216 16777216)
           12918652928)))

  (testing "King position H1 (First bit)"
    (is (= (move/find-king-moves (unchecked-long 1) 1)
           (unchecked-long 770))))

  (testing "King position A1"
    (is (= (move/find-king-moves (unchecked-long 128) 128)
           (unchecked-long 49216))))

  (testing "King position H8"
    (is (= (move/find-king-moves (unchecked-long 72057594037927936) (unchecked-long 72057594037927936))
           (unchecked-long 144959613005987840))))

  (testing "King position A8 (Last bit)"
    (is (= (move/find-king-moves (unchecked-long 9223372036854775808) (unchecked-long 9223372036854775808))
           (unchecked-long 4665729213955833856)))))

(deftest knight-moves-test
  "Validating knight moves from different positions"
  (testing "Knight position H1 (First bit)"
    (is (= (move/find-knight-moves (unchecked-long 1) 1)
           (unchecked-long 132096))))

  (testing "Knight position A1"
    (is (= (move/find-knight-moves (unchecked-long 128) 128)
           (unchecked-long 4202496))))

  (testing "Knight position H8"
    (is (= (move/find-knight-moves (unchecked-long 72057594037927936) (unchecked-long 72057594037927936))
           (unchecked-long 1128098930098176))))

  (testing "Knight position A8 (Last bit)"
    (is (= (move/find-knight-moves (unchecked-long 9223372036854775808) (unchecked-long 9223372036854775808))
           (unchecked-long 9077567998918656))))

  (testing "Knight position D4"
    (is (= (move/find-knight-moves (unchecked-long 268435456) (unchecked-long 268435456))
           (unchecked-long 44272527353856))))

  (testing "Knight position D4, but all slots are filled by white pieces"
    (is (= (move/find-knight-moves (unchecked-long 268435456) (unchecked-long 44272795789312))
           (unchecked-long 0))))

  (testing "Knight at all position D4, A1, H1, H8, A8"
    (is (= (move/find-knight-moves (unchecked-long 9295429631161139329) (unchecked-long 9295429631161139329))
           (unchecked-long 10249939456502784)))))

(deftest white-pawn-moves-test
  (testing "initial pawn position - single and double push"
    (is (= (move/find-white-pawn-moves (unchecked-long 65280) (unchecked-long 65280) 0)
           (unchecked-long 4294901760))))

  (testing "pawn push on rank other rank than 2"
    (is (= (move/find-white-pawn-moves (unchecked-long 16711680) (unchecked-long 16711680) 0)
           (unchecked-long 4278190080))))

  (testing "Pawn position h2 is unable to attack black piece on a2"
    (is (= (move/find-white-pawn-moves (unchecked-long 256) (unchecked-long 33024) 32768)
           (unchecked-long 16842752))))

  (testing "Pawn position h2 is able to attack black piece on g3"
    (is (= (move/find-white-pawn-moves (unchecked-long 256) (unchecked-long 131328) 131072)
           (unchecked-long 16973824))))

  (testing "Pawn position a2 is unable to attack black piece on h4"
    (is (= (move/find-white-pawn-moves (unchecked-long 32768) (unchecked-long 16809984) 16777216)
           (unchecked-long 2155872256))))

  (testing "Pawn position a2 is able to attack black piece on b3"
    (is (= (move/find-white-pawn-moves (unchecked-long 32768) (unchecked-long 4227072) 4194304)
           (unchecked-long 2160066560))))

  (testing "Initial pawn position - pawn can only move 1 if piece is in the way on rank 4"
    (is (= (move/find-white-pawn-moves (unchecked-long 16384) (unchecked-long 1073758208) 0)
           (unchecked-long 4194304))))

  (testing "Initial pawn position - pawn cannot move since piece is in the way on rank 3"
    (is (= (move/find-white-pawn-moves (unchecked-long 16384) (unchecked-long 4210688) 0)
           (unchecked-long 0))))

  (testing "Pawn position b2 can attack black pieces on both diagonals"
    (is (= (move/find-white-pawn-moves (unchecked-long 16384) (unchecked-long 10502144) 10485760)
           (unchecked-long 1088421888)))))

(deftest black-pawn-moves-test
  (testing "initial pawn position - single and double push"
    (is (= (move/find-black-pawn-moves (unchecked-long 71776119061217280) (unchecked-long 71776119061217280) 0)
           (unchecked-long 281470681743360))))

  (testing "pawn push on rank other rank than 7"
    (is (= (move/find-black-pawn-moves (unchecked-long 280375465082880) (unchecked-long 280375465082880) 0)
           (unchecked-long 1095216660480))))

  (testing "Pawn position h7 is unable to attack black piece on a7"
    (is (= (move/find-black-pawn-moves (unchecked-long 281474976710656) (unchecked-long 36310271995674624) (unchecked-long 36028797018963968))
           (unchecked-long 1103806595072))))

  (testing "Pawn position h2 is able to attack black piece on g6"
    (is (= (move/find-black-pawn-moves (unchecked-long 281474976710656) (unchecked-long 283673999966208) (unchecked-long 2199023255552))
           (unchecked-long 3302829850624))))

  (testing "Pawn position a7 is unable to attack black piece on h5"
    (is (= (move/find-black-pawn-moves (unchecked-long 36028797018963968) (unchecked-long 36028801313931264) 4294967296)
           (unchecked-long 141287244169216))))

  (testing "Pawn position a7 is able to attack black piece on b6"
    (is (= (move/find-black-pawn-moves (unchecked-long 36028797018963968) (unchecked-long 36099165763141632) (unchecked-long 70368744177664))
           (unchecked-long 211655988346880))))

  (testing "Initial pawn position - pawn can only move 1 if piece is in the way on rank 4"
    (is (= (move/find-black-pawn-moves (unchecked-long 18014398509481984) (unchecked-long 18014673387388928) 0)
           (unchecked-long 70368744177664))))

  (testing "Initial pawn position - pawn cannot move since piece is in the way on rank 3"
    (is (= (move/find-black-pawn-moves (unchecked-long 18014398509481984) (unchecked-long 18084767253659648) 0)
           (unchecked-long 0))))

  (testing "Pawn position b7 can attack black pieces on both diagonals"
    (is (= (move/find-black-pawn-moves (unchecked-long 18014398509481984) (unchecked-long 18190320369926144) 175921860444160)
           (unchecked-long 246565482528768)))))

(deftest rook-moves-test
  (testing "rook E5 with opponent piece on G5, B5 and E7 and own piece at E2"
    (is (= (move/find-rook-moves 35 2056 (unchecked-long 578712869944690696))
           (unchecked-long 2261102847590400))))

  (testing "Initial rook position H1 - blocked in corner by own pieces"
    (is (= (move/find-rook-moves 0 259 259)
           (unchecked-long 0))))

  (testing "Initial rook position H1 - No blockers"
    (is (= (move/find-rook-moves 0 0 0)
           (unchecked-long 72340172838076926)))))

(deftest bishop-moves-test
  (testing "Bishop E4 with opponent piece on D3, D5, F3 and F5"
    (is (= (move/find-bishop-moves 27 0x8000000 (unchecked-long 0x1408140000))
           (unchecked-long 0x1400140000))))

  (testing "Bishop E4 - No blockers"
    (is (= (move/find-bishop-moves 27 0x8000000 0x8000000)
           (unchecked-long 0x8041221400142241))))

  (testing "Bishop E4 blocked by own pieces on D3, D5, F3 and F5"
    (is (= (move/find-bishop-moves 27 (unchecked-long 0x1408140000) (unchecked-long 0x1408140000))
           (unchecked-long 0)))))

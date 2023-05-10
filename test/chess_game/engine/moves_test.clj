(ns chess-game.engine.moves-test
  (:require [clojure.test :refer :all]
            [chess-game.engine.move-generation.moves :as move]
            [chess-game.engine.board :as board]))

(deftest king-moves-test
  (testing "Testing king pos H4"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :q :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :K
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-king-moves (get-in bitboards [:white :K]) (get-in bitboards [:occupancy :white]))
             12918652928))))

  (testing "King position H1 (First bit)"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :q :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :K])]
      (is (= (move/lookup-king-moves (get-in bitboards [:white :K]) (get-in bitboards [:occupancy :white]))
             (unchecked-long 770)))))

  (testing "King position A1"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :q :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :K :- :- :- :- :- :- :-])]
      (is (= (move/lookup-king-moves (get-in bitboards [:white :K]) (get-in bitboards [:occupancy :white]))
             (unchecked-long 49216)))))

  (testing "King position H8"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :K
                                          :q :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-king-moves (get-in bitboards [:white :K]) (get-in bitboards [:occupancy :white]))
             (unchecked-long 144959613005987840)))))

  (testing "King position A8 (Last bit)"
    (let [bitboards (board/vector->board [:K :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :q :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-king-moves (get-in bitboards [:white :K]) (get-in bitboards [:occupancy :white]))
             (unchecked-long 4665729213955833856))))))

(deftest knight-moves-test
  "Validating knight moves from different positions"
  (testing "Knight position H1 (First bit)"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :q :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :N])]
      (is (= (move/lookup-knight-moves (get-in bitboards [:white :N]) (get-in bitboards [:occupancy :white]))
             (unchecked-long 132096)))))

  (testing "Knight position A1"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :q :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :N :- :- :- :- :- :- :-])]
      (is (= (move/lookup-knight-moves (get-in bitboards [:white :N]) (get-in bitboards [:occupancy :white]))
             (unchecked-long 4202496)))))

  (testing "Knight position H8"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :N
                                          :q :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-knight-moves (get-in bitboards [:white :N]) (get-in bitboards [:occupancy :white]))
             (unchecked-long 1128098930098176)))))

  (testing "Knight position A8 (Last bit)"
    (let [bitboards (board/vector->board [:N :- :- :- :- :- :- :-
                                          :q :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-knight-moves (get-in bitboards [:white :N]) (get-in bitboards [:occupancy :white]))
             (unchecked-long 9077567998918656)))))

  (testing "Knight position D4"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :q :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :N :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-knight-moves (get-in bitboards [:white :N]) (get-in bitboards [:occupancy :white]))
             (unchecked-long 44272527353856)))))

  (testing "Knight position D4, but all slots are filled by white pieces"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :q :- :- :- :- :- :- :-
                                          :- :- :Q :- :Q :- :- :-
                                          :- :Q :- :- :- :Q :- :-
                                          :- :- :- :N :- :- :- :-
                                          :- :Q :- :- :- :Q :- :-
                                          :- :- :Q :- :Q :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-knight-moves (get-in bitboards [:white :N]) (get-in bitboards [:occupancy :white]))
             (unchecked-long 0)))))

  (testing "Knight at all position D4, A1, H1, H8, A8"
    (let [bitboards (board/vector->board [:N :- :- :- :- :- :- :N
                                          :q :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :N :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :N :- :- :- :- :- :- :N])]
      (is (= (move/lookup-knight-moves (get-in bitboards [:white :N]) (get-in bitboards [:occupancy :white]))
             (unchecked-long 10249939456502784))))))

(deftest white-pawn-moves-test
  (testing "initial pawn position - single and double push"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :q :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :P :P :P :P :P :P :P :P
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-pawn-moves (get-in bitboards [:white :P]) (get-in bitboards [:occupancy :all]) (get-in bitboards [:occupancy :black]) :white)
             (unchecked-long 4294901760)))))

  (testing "pawn push on rank other rank than 2"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :q :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :P :P :P :P :P :P :P :P
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-pawn-moves (get-in bitboards [:white :P]) (get-in bitboards [:occupancy :all]) (get-in bitboards [:occupancy :black]) :white)
             (unchecked-long 4278190080)))))

  (testing "Pawn position h2 is unable to attack black piece on a2"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :r :- :- :- :- :- :- :P
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-pawn-moves (get-in bitboards [:white :P]) (get-in bitboards [:occupancy :all]) (get-in bitboards [:occupancy :black]) :white)
             (unchecked-long 16842752)))))

  (testing "Pawn position h2 is able to attack black piece on g3"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :r :-
                                          :- :- :- :- :- :- :- :P
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-pawn-moves (get-in bitboards [:white :P]) (get-in bitboards [:occupancy :all]) (get-in bitboards [:occupancy :black]) :white)
             (unchecked-long 16973824)))))

  (testing "Pawn position a2 is unable to attack black piece on h4"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :r
                                          :- :- :- :- :- :- :- :-
                                          :P :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-pawn-moves (get-in bitboards [:white :P]) (get-in bitboards [:occupancy :all]) (get-in bitboards [:occupancy :black]) :white)
             (unchecked-long 2155872256)))))

  (testing "Pawn position a2 is able to attack black piece on b3"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :r :- :- :- :- :- :-
                                          :P :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-pawn-moves (get-in bitboards [:white :P]) (get-in bitboards [:occupancy :all]) (get-in bitboards [:occupancy :black]) :white)
             (unchecked-long 2160066560)))))

  (testing "Pawn position b2 - pawn can only move 1 if piece is in the way on rank 4"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :r :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :P :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-pawn-moves (get-in bitboards [:white :P]) (get-in bitboards [:occupancy :all]) (get-in bitboards [:occupancy :black]) :white)
             (unchecked-long 4194304)))))

  (testing "Pawn position b2 - pawn cannot move since piece is in the way on rank 3"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :r :- :- :- :- :- :-
                                          :- :P :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-pawn-moves (get-in bitboards [:white :P]) (get-in bitboards [:occupancy :all]) (get-in bitboards [:occupancy :black]) :white)
             (unchecked-long 0)))))

  (testing "Pawn position b2 can attack black pieces on both diagonals"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :r :- :r :- :- :- :- :-
                                          :- :P :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-pawn-moves (get-in bitboards [:white :P]) (get-in bitboards [:occupancy :all]) (get-in bitboards [:occupancy :black]) :white)
             (unchecked-long 1088421888))))))

(deftest black-pawn-moves-test
  (testing "initial pawn position - single and double push"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :p :p :p :p :p :p :p :p
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :P :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-pawn-moves (get-in bitboards [:black :p]) (get-in bitboards [:occupancy :all]) (get-in bitboards [:occupancy :white]) :black)
             (unchecked-long 281470681743360)))))

  (testing "pawn push on all rank 6"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :p :p :p :p :p :p :p :p
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :P :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-pawn-moves (get-in bitboards [:black :p]) (get-in bitboards [:occupancy :all]) (get-in bitboards [:occupancy :white]) :black)
             (unchecked-long 1095216660480)))))

  (testing "Pawn position h7 is unable to attack black piece on a7"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :Q :- :- :- :- :- :- :p
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-pawn-moves (get-in bitboards [:black :p]) (get-in bitboards [:occupancy :all]) (get-in bitboards [:occupancy :white]) :black)
             (unchecked-long 1103806595072)))))

  (testing "Pawn position h7 is able to attack black piece on g6"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :p
                                          :- :- :- :- :- :- :Q :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-pawn-moves (get-in bitboards [:black :p]) (get-in bitboards [:occupancy :all]) (get-in bitboards [:occupancy :white]) :black)
             (unchecked-long 3302829850624)))))

  (testing "Pawn position a7 is unable to attack black piece on h5"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :p :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :Q
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-pawn-moves (get-in bitboards [:black :p]) (get-in bitboards [:occupancy :all]) (get-in bitboards [:occupancy :white]) :black)
             (unchecked-long 141287244169216)))))

  (testing "Pawn position a7 is able to attack black piece on b6"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :p :- :- :- :- :- :- :-
                                          :- :Q :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-pawn-moves (get-in bitboards [:black :p]) (get-in bitboards [:occupancy :all]) (get-in bitboards [:occupancy :white]) :black)
             (unchecked-long 211655988346880)))))

  (testing "Pawn position B7 - blocked by own piece on rank 4, pawn can only move 1 forward"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :p :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :r :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :P
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-pawn-moves (get-in bitboards [:black :p]) (get-in bitboards [:occupancy :all]) (get-in bitboards [:occupancy :white]) :black)
             (unchecked-long 70368744177664)))))

  (testing "Pawn position B7 - blocked by own piece on rank 3"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :p :- :- :- :- :- :-
                                          :- :r :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :P
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-pawn-moves (get-in bitboards [:black :p]) (get-in bitboards [:occupancy :all]) (get-in bitboards [:occupancy :white]) :black)
             (unchecked-long 0)))))

  (testing "Pawn position b7 can attack black pieces on both diagonals"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :p :- :- :- :- :- :-
                                          :Q :- :Q :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/lookup-pawn-moves (get-in bitboards [:black :p]) (get-in bitboards [:occupancy :all]) (get-in bitboards [:occupancy :white]) :black)
             (unchecked-long 246565482528768))))))


(deftest rook-moves-test
  (testing "rook E5 with opponent piece on G5, B5 and E7 and own piece at E2"
    (let [bitboards (board/vector->board [:- :- :- :- :p :- :- :-
                                          :- :- :- :- :p :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :p :- :- :R :- :p :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :P :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/find-rook-moves 35 (get-in bitboards [:occupancy :white]) (get-in bitboards [:occupancy :all]))
             (unchecked-long 2261102847590400)))))

  (testing "Initial rook position H1 - blocked in corner by own pieces"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :P
                                          :- :- :- :- :- :- :P :R])]
      (is (= (move/find-rook-moves 0 (get-in bitboards [:occupancy :white]) (get-in bitboards [:occupancy :all]))
             (unchecked-long 0)))))

  (testing "Initial rook position H1 - No blockers"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :R])]
      (is (= (move/find-rook-moves 0 (get-in bitboards [:occupancy :white]) (get-in bitboards [:occupancy :all]))
             (unchecked-long 72340172838076926))))))

(deftest bishop-moves-test
  (testing "Bishop E4 with opponent piece on D3, D5, F3 and F5"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :p :- :p :- :-
                                          :- :- :- :- :B :- :- :-
                                          :- :- :- :p :- :p :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/find-bishop-moves 27 (get-in bitboards [:occupancy :white]) (get-in bitboards [:occupancy :all]))
             (unchecked-long 0x1400140000)))))

  (testing "Bishop E4 - No blockers"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :B :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/find-bishop-moves 27 (get-in bitboards [:occupancy :white]) (get-in bitboards [:occupancy :all]))
             (unchecked-long 0x8041221400142241)))))

  (testing "Bishop E4 blocked by own pieces on D3, D5, F3 and F5"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :P :- :P :- :-
                                          :- :- :- :- :B :- :- :-
                                          :- :- :- :P :- :P :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/find-bishop-moves 27 (get-in bitboards [:occupancy :white]) (get-in bitboards [:occupancy :all]))
             (unchecked-long 0))))))

(deftest queen-moves-test
  (testing "Queen E4 with opponent piece on D3, D4, D5, E3, E4, F3, F4 and F5"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :p :p :p :- :-
                                          :- :- :- :p :Q :p :- :-
                                          :- :- :- :p :p :p :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/find-queen-moves 27 (get-in bitboards [:occupancy :white]) (get-in bitboards [:occupancy :all]))
             (unchecked-long 0x1c141c0000)))))

  (testing "Queen E4 - No blockers"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :Q :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/find-queen-moves 27 (get-in bitboards [:occupancy :white]) (get-in bitboards [:occupancy :all]))
             (unchecked-long 0x88492a1cf71c2a49)))))

  (testing "Queen E4 blocked by own pieces on D3, D4, D5, E3, E4, F3, F4 and F5"
    (let [bitboards (board/vector->board [:- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :P :P :P :- :-
                                          :- :- :- :P :Q :P :- :-
                                          :- :- :- :P :P :P :- :-
                                          :- :- :- :- :- :- :- :-
                                          :- :- :- :- :- :- :- :-])]
      (is (= (move/find-queen-moves 27 (get-in bitboards [:occupancy :white]) (get-in bitboards [:occupancy :all]))
             (unchecked-long 0))))))

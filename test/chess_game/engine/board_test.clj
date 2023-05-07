(ns chess-game.engine.board-test
  (:require [chess-game.engine.board :as board]
            [clojure.test :refer [deftest is testing]]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]))

; Would be more correct to use gen/return :k instead of gen/elements [:k]
(def pieces [[8 (gen/elements [:p])] [2 (gen/elements [:t])]
             [2 (gen/elements [:n])] [2 (gen/elements [:b])]
             [1 (gen/elements [:q])] [1 (gen/elements [:k])]
             [8 (gen/elements [:P])] [2 (gen/elements [:T])]
             [2 (gen/elements [:N])] [2 (gen/elements [:B])]
             [1 (gen/elements [:Q])] [1 (gen/elements [:K])]
             [32 (gen/elements [:-])]])

(defspec shuffled-vector->vector->board 100
         (prop/for-all [vector (gen/shuffle (:initial-setup board/boards))]
                       (= vector (-> vector board/vector->board board/board->vector))))

(defspec random-vector->vector->board 100
         (prop/for-all [vector (gen/vector (gen/frequency pieces) 64)]
                       (= vector (-> vector board/vector->board board/board->vector))))

(def empty-board (:empty board/boards))

(deftest empty-vector->board
  (testing "Testing that an empty vector-board creates an empty bit-board (aka empty map)"
    (is (= (board/vector->board empty-board) {}))))

(deftest empty-map->vector-board
  (testing "Testing that a empty map creates an empty vector-board"
    (is (= (board/board->vector {}) empty-board))))

(deftest empty-board->vector-board
  (testing "Testing that an empty board creates an empty vector-board"
    (is (= (board/board->vector
             {:white {:P 0 :R 0 :K 0 :B 0 :Q 0 :N 0} :black {:p 0 :r 0 :k 0 :b 0 :q 0 :n 0}})
           empty-board))))

(deftest board->bit->full-board-pawns
  (testing "Validates that converting a full board of pawns from vector -> bitboard -> vector results in the same board"
    (let [vector (:full-pawns board/boards)]
      (= vector (-> vector board/vector->board board/board->vector)))))

(board/vector->board (:initial-setup board/boards))

(ns chess-game.engine.board-test
  (:require [chess-game.engine.board :as board]
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

(defspec board->bit->board-shuffle 100
         (prop/for-all [board (gen/shuffle (:initial-setup board/boards))]
                       (= board (-> board board/board->bit board/bit->board))))

(defspec board->bit->board-random 100
         (prop/for-all [board (gen/vector (gen/frequency pieces) 64)]
                       (= board (-> board board/board->bit board/bit->board))))

; Other test cases to think about
; What if the board is empty?
; What if the board is full? (of only one type)
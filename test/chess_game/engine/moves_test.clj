(ns chess-game.engine.moves-test
  (:require [clojure.test :refer :all]
            [chess-game.engine.moves :as move]))


(deftest king-moves
  "Validating king moves from different positions"
  (testing "Testing king pos H4"
    (is (= (move/white-king-moves {:white     {:K 16777216}
                                   :occupancy {:white 1
                                               :black 2048}})
           12918652928)))

  (testing "King position H1 (First bit)"
    (is (= (move/white-king-moves {:white     {:K (unchecked-long 1)}
                                   :occupancy {:white 0
                                               :black 2048}})
           (unchecked-long 770))))

  (testing "King position A1"
    (is (= (move/white-king-moves {:white     {:K (unchecked-long 128)}
                                   :occupancy {:white 0
                                               :black 2048}})
           (unchecked-long 49216))))

  (testing "King position H8"
    (is (= (move/white-king-moves {:white     {:K (unchecked-long 72057594037927936)}
                                   :occupancy {:white 0
                                               :black 0}})
           (unchecked-long 144959613005987840))))

  (testing "King position A8 (Last bit)"
    (is (= (move/white-king-moves {:white     {:K (unchecked-long 9223372036854775808)}
                                   :occupancy {:white 0
                                               :black 2048}})
           (unchecked-long 4665729213955833856)))))

(deftest knight-moves
  "Validating king moves from different positions"
  (testing "King position H1 (First bit)"
    (is (= (move/get-knight-moves {:white     {:N (unchecked-long 1)}
                                   :occupancy {:white 0
                                               :black 2048}})
           (unchecked-long 132096))))

  (testing "King position A1"
    (is (= (move/get-knight-moves {:white     {:N (unchecked-long 128)}
                                   :occupancy {:white 0
                                               :black 2048}})
           (unchecked-long 4202496))))

  (testing "King position H8"
    (is (= (move/get-knight-moves {:white     {:N (unchecked-long 72057594037927936)}
                                   :occupancy {:white 0
                                               :black 0}})
           (unchecked-long 1128098930098176))))

  (testing "King position A8 (Last bit)"
    (is (= (move/get-knight-moves {:white     {:N (unchecked-long 9223372036854775808)}
                                   :occupancy {:white 0
                                               :black 2048}})
           (unchecked-long 9077567998918656))))
  (testing "Knight position D4"
    (is (= (move/get-knight-moves {:white     {:N (unchecked-long 268435456)}
                                   :occupancy {:white (unchecked-long 0)
                                               :black 2048}})
           (unchecked-long 44272527353856))))
  (testing "Knight position D4, but all slots are filled by white pieces"
    (is (= (move/get-knight-moves {:white     {:N (unchecked-long 268435456)}
                                   :occupancy {:white (unchecked-long 44272527353856)
                                               :black 2048}})
           (unchecked-long 0)))))

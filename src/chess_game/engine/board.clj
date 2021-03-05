(ns chess-game.engine.board)

;; In bit board we divide it into 12 boards

;; Make a board that represents the location of the black king
;; pawn, bishop, knight, tower, queen, king

(def- start-positions [:T :N :B :Q :K :B :N :T
                       :P :P :P :P :P :P :P :P
                       :- :- :- :- :- :- :- :-
                       :- :- :- :- :- :- :- :-
                       :- :- :- :- :- :- :- :-
                       :- :- :- :- :- :- :- :-
                       :p :p :p :p :p :p :p :p
                       :t :n :b :q :k :b :n :t])

(defn initial-board
    "docstring")


(defn move []
    (println "I have moved a piece"))
(ns chess-game.engine.board)

;; In bit board we divide it into 12 boards

;; Make a board that represents the location of the black king
;; pawn, bishop, knight, tower, queen, king
;; Black is uppercase, white is lowercase.

(def start-positions [:T :N :B :Q :K :B :N :T
                       :P :P :P :P :P :P :P :P
                       :- :- :- :- :- :- :- :-
                       :- :- :- :- :- :- :- :-
                       :- :- :- :- :- :- :- :-
                       :- :- :- :- :- :- :- :-
                       :p :p :p :p :p :p :p :p
                       :t :n :b :q :k :b :n :t])

;; the vector starts from top-left, meaning the index corresponds with position
;; For bit-board it is similar, but in the opposite direction. This means that:
;; :Q in bit-vector at index 60
;; :Q in vector at index 3

(defn board->bit [board]
    (let [upd-bit (fn [bit-board pos piece]
                            (update bit-board piece #(if % (bit-set % pos) (bit-set 0 pos))))
          upd-bit&pos (fn [[bit-board pos] piece]
                            (case piece
                                :- [bit-board (dec pos)]
                                [(upd-bit bit-board pos piece) (dec pos)]))
          bit&pos [{} 63]]
        (first
            (reduce upd-bit&pos bit&pos board))))

(defn squares []
    (count start-positions))
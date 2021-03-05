(ns chess-game.core
  (:gen-class)
  (:require [chess-game.engine.board :as board]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (board/move)
  (println "Hello, World!"))

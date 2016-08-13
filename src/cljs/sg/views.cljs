(ns sg.views
    (:require [re-frame.core :as re-frame]))

(defn score
  "Renders player's score"
  []
  (let [points (re-frame/subscribe [:points])]
    (fn []
      [:div.score (str "Score: " @points)])))

(defn render-board
  "Renders the game board area with snake and the food item"
  []
  (let [board (re-frame/subscribe [:board])
        snake (re-frame/subscribe [:snake])
        point (re-frame/subscribe [:point])]
    (fn []
      (let [[width height] @board
            snake-positions (into #{} @snake)
            current-point @point
            cells (for [y (range height)]
                    (into [:tr]
                          (for [x (range width)
                                :let [current-pos [x y]]]
                            (cond
                              (snake-positions current-pos) [:td.snake-on-cell]
                              (= current-pos current-point) [:td.point]
                              :else [:td.cell]))))]
        (into [:table.stage {:style {:height 377
                                     :width 527}}]
              cells)))))
(defn game-over
  "Renders the game over overlay if the game is finished"
  []
  (let [game-state (re-frame/subscribe [:game-running?])]
    (fn []
      (if @game-state
        [:div]
        [:div.overlay
         [:div.play {:on-click #(re-frame/dispatch [:initialize-db])}
          [:h1 "↺" ]]]))))

(defn main-panel []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div ;"Hello from " @name
       [render-board]
       [score]
       [game-over]])))


(ns sg.handlers
    (:require [re-frame.core :as re-frame]
              [sg.db :as db]
              [sg.utils :as utils]))

;; (re-frame/register-handler
;;  :initialize-db
;;  (fn  [_ _]
;;    db/default-db))
(def snake {:direction [1 0]
            :body [[3 2] [2 2] [1 2] [0 2]]})

(def board [35 25])

(def initial-state {:board board
                    :snake snake
                    :point (utils/rand-free-position snake board)
                    :points 0
                    :game-running? true})

(re-frame/register-handler
 :initialize-db
 (fn  [db _]
   (merge db initial-state)))

(re-frame/register-handler
 :next-state
 (fn
   [{:keys [snake board] :as db} _]
   (if (:game-running? db)
     (if (utils/collisions snake board)
       (assoc-in db [:game-running?] false)
       (-> db
           (update-in [:snake] utils/move-snake)
           (as-> after-move
               (utils/process-move after-move))))
     db)))

(re-frame/register-handler
 :change-direction
 (fn [db [_ new-direction]]
   (update-in db [:snake :direction]
              (partial utils/change-snake-direction new-direction))))

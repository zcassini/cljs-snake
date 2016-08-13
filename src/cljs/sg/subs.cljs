(ns sg.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
 :name
 (fn [db _]
   (reaction (:name @db))))

(re-frame/register-sub
 :board
 (fn
   [db _]                         ;; db is the app-db atom 
   (reaction (:board @db))))      ;; wrap the computation in a reaction 

(re-frame/register-sub
 :snake
 (fn
   [db _]
   (reaction (:body (:snake @db)))))

(re-frame/register-sub
 :point
 (fn
   [db _]
   (reaction (:point @db))))

(re-frame/register-sub
 :points
 (fn
   [db _]
   (reaction (:points @db))))

(re-frame/register-sub
 :game-running?
 (fn
   [db _]
   (reaction (:game-running? @db))))

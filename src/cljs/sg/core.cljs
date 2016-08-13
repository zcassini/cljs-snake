(ns sg.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [devtools.core :as devtools]
              [sg.handlers]
              [sg.subs]
              [sg.views :as views]
              [sg.config :as config]))

(defonce snake-moving
  (js/setInterval #(re-frame/dispatch [:next-state]) 150))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")
    (devtools/install!)))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))

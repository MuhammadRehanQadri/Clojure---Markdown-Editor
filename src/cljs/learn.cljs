(ns ^:figwheel-hooks cljs.learn
  (:require
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]
   [reagent.dom :as rdom]
   [cljsjs.showdown :as showdown]))


;; define your app data so that it doesn't get over-written on reload
;; (defonce app-state (atom {:text "Hello world!"}))


(defonce markdown (reagent/atom ""))

(defn get-app-element []
  (gdom/getElement "app"))

(defn hello-world []
  [:div
   [:p (prn-str @markdown)]
   [:h3 "Markdown Editr"]
   [:textarea
    {:on-change #(reset! markdown (-> % .-target .-value))
     :value @markdown}]])








(defn mount [el]
  (rdom/render [hello-world] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )



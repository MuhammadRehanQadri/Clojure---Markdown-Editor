(ns ^:figwheel-hooks cljs.learn
  (:require
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]
   [reagent.dom :as rdom]
   [cljsjs.showdown]))


;; define your app data so that it doesn't get over-written on reload
;; (defonce app-state (atom {:text "Hello world!"}))

(defonce markdown (reagent/atom ""))
(defonce converter (js/showdown.Converter.))

(defn markdown->html [markdown]
  (.makeHtml converter markdown))

(defn hello-world []
  [:div
   [:h1
    {:style {:text-align "center"
             :color "green"}}
    "Markdownify"]
   [:div
    {:style {:display "flex"}}
    [:div
     {:style {:flex "1"}}
     [:h2
      {:style {:text-align "center"}}
      "Markdown Editor"]
     [:textarea
      {:on-change #(reset! markdown (-> % .-target .-value))
       :style {:resize "none"
               :width "100%"
               :height "500px"}
       :value @markdown}]]

    [:div
     {:style {:flex "1"
              :padding-left "2em"}}
     [:h2 
      {:style {:text-align "center"}}
      "HTML Preview"]
     [:div {:dangerouslySetInnerHTML {:__html (markdown->html @markdown)}}]]]])





(defn get-app-element []
  (gdom/getElement "app"))


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



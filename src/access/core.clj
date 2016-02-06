(ns access.core
  (:require [quil.core :as q]
            [quil.middleware]))

(defn register-position []
  (q/fill 255)
  (let [x (q/mouse-x)
        y (q/mouse-y)]
    (q/rect (- x 10) (- y 10 ) 20 20)))


(defn setup []
  {:inputting? false})

(defn step [state] state)


(defn key-pressed [state event]
  (assoc state :inputting? true))

(defn key-released [_state]
  (setup))


(defn draw [state]
  (q/no-stroke)
  (q/fill 0 10)
  (q/rect 0 0 (q/width) (q/height))
  (q/fill 255)
  (if (:inputting? state)
    (register-position)
    nil))


(defn run []
  (q/defsketch example
    :title "Art!"
    :middleware [quil.middleware/fun-mode]
    :settings #(q/smooth 2)
    :setup setup
    :update step
    :draw draw
    :key-pressed key-pressed
    :key-released key-released
    :size [500 500]
    ))
(run)

; void sendOsc() {
;   OscMessage msg = new OscMessage("/wek/inputs");
;   msg.add((float)mouseX);
;   msg.add((float)mouseY);
;   oscP5.send(msg, dest);
; }

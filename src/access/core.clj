(ns access.core
  (use overtone.osc)
  (:require [quil.core :as q]
            [quil.middleware]))


(def weki-port 6448)
(def weki-client (osc-client "localhost" weki-port))

(defn weki-send-xy [x y]
  (osc-send weki-client "/wek/inputs" x y))

(defn weki-start-recording [n]
  (osc-send weki-client "/wekinator/control/startDtwRecording" (int n)))

(defn weki-stop-recording []
  (osc-send weki-client "/wekinator/control/stopDtwRecording"))


(defn register-position []
  (q/fill 255)
  (let [x (q/mouse-x)
        y (q/mouse-y)]
    (q/rect (- x 10) (- y 10) 20 20)
    (weki-send-xy x y)))

(defn setup [] {})
(defn step [state] state)

 ; 1 V
 ; 2 A

(defn key-pressed [state event]
  (weki-start-recording (int 3))
  state)

(defn key-released [state]
  (weki-stop-recording)
  state)

(defn draw [state]
  (q/no-stroke)
  (q/fill 0 10)
  (q/rect 0 0 (q/width) (q/height))
  (q/fill 255)
  (register-position))

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

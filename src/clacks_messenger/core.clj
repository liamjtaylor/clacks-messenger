(ns clacks-messenger.core
  (require [clacks-messenger.clacks-mapping :as clack-map]
           [clojure.pprint :as pp])
  (:gen-class))

(defn build-state [word]
    {:original-message    [word]
     :to-be-sent          (clack-map/string->clacks word)
     :first-tower         [0 0 0 0 0 0 0 0]
     :second-tower        [0 0 0 0 0 0 0 0]
     :third-tower         [0 0 0 0 0 0 0 0]
     :received-message    []})

(defn sending-message [{:keys [received-message first-tower second-tower third-tower to-be-sent original-message] :as state}]
  (let [clack-received        (if-not (empty? third-tower) (conj () third-tower) [])
        received-message      (if-not (empty? third-tower) (conj received-message (clack-map/clacks->string clack-received)) received-message)
        third-tower           (if-not (empty? second-tower) second-tower [0 0 0 0 0 0 0 0])
        second-tower          (if-not (empty? first-tower) first-tower [0 0 0 0 0 0 0 0])
        first-tower           (if-not (empty? to-be-sent) (first to-be-sent) [0 0 0 0 0 0 0 0])
        to-be-sent            (if-not (empty? to-be-sent) (rest to-be-sent) [])
        new-state             {:original-message    original-message
                               :to-be-sent          to-be-sent
                               :first-tower         first-tower
                               :second-tower        second-tower
                               :third-tower         third-tower
                               :received-message    received-message}]
        (if (and (= third-tower [0 0 0 0 0 0 0 0]) (empty? to-be-sent))
          (pp/pprint (reduce str (:received-message new-state)))
          (do
            (pp/pprint (select-keys new-state [:first-tower :second-tower :third-tower]))
            (println "--------")
            (Thread/sleep 10)
            (sending-message new-state)))))

(defn -main [*command-line-args*]
  (prn *command-line-args*)
  (println "--------")
  (sending-message (build-state *command-line-args*)))

;look at devcards to dynamically update a grid

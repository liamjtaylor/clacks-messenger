(ns clacks-messenger.core
  (require [clacks-messenger.clacks-mapping :as clack-map]
           [clojure.pprint :as pp])
  (:gen-class))

(defn build-state [word]
    {:original-message    [word]
     :to-be-sent          (clack-map/string->clacks word)
     :first-tower         []
     :second-tower        []
     :third-tower         []
     :received-message    []})

(defn sending-message [{:keys [received-message first-tower second-tower third-tower to-be-sent original-message] :as state}]
  (let [clack-received        (if-not (empty? third-tower) (conj () third-tower) [])
        received-message      (if-not (empty? third-tower) (conj received-message (clack-map/clacks->string clack-received)) received-message)
        third-tower           (if-not (empty? second-tower) second-tower [])
        second-tower          (if-not (empty? first-tower) first-tower [])
        first-tower           (if-not (empty? to-be-sent) (first to-be-sent) [])
        to-be-sent            (if-not (empty? to-be-sent) (rest to-be-sent) [])
        new-state             {:original-message    original-message
                               :to-be-sent          to-be-sent
                               :first-tower         first-tower
                               :second-tower        second-tower
                               :third-tower         third-tower
                               :received-message    received-message}]
        (if (and (empty? third-tower) (empty? to-be-sent))
          (pp/pprint (reduce str (:received-message new-state)))
          (do
            (pp/pprint (select-keys new-state [:first-tower :second-tower :third-tower]))
            (Thread/sleep 10)
            (sending-message new-state)))))

(defn -main [*command-line-args*]
  (prn *command-line-args*)
  (sending-message (build-state *command-line-args*)))


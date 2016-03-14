(ns clacks-messenger.core
  (require [clacks-messenger.clacks-mapping :as clack-map]))

(defn build-state [word]
    {:original-message    [word]
     :to-be-sent          (clack-map/string->clacks word)
     :first-tower         []
     :second-tower        []
     :third-tower         []
     :received-message    []})

(defn sending-message [{:keys [received-message first-tower second-tower third-tower to-be-sent original-message] :as state} n]
  (let [received-message      (if-not (empty? third-tower) (conj received-message third-tower) received-message)
        third-tower           second-tower
        second-tower          first-tower
        first-tower           (first to-be-sent)
        to-be-sent            (rest to-be-sent)
        new-state             {:original-message    original-message
                               :to-be-sent          to-be-sent
                               :first-tower         first-tower
                               :second-tower        second-tower
                               :third-tower         third-tower
                               :received-message    received-message}]
        (if (= n 0)
          new-state
          (sending-message new-state (dec n)))))

(sending-message (build-state "test") 4)

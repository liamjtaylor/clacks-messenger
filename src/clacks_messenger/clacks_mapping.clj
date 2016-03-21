(ns clacks-messenger.clacks-mapping
  (require [clojure.string :as clj-str]
           [clacks-messenger.alphabet-mapping :as alph-map]))

(defn reverse-map [m]
  (into {} (map (fn [[a b]] [b a]) m)))

(defn kebabify [string]
  (clj-str/replace string " " "-"))

(defn de-kebabify [string]
  (clj-str/replace string "-" " "))

(defn clacksify [letter]
  (let [character (str letter)]
  (alph-map/alphabet character)))

(defn back-to-letter [clack]
  (name ((reverse-map alph-map/alphabet) clack)))

(defn string->clacks [word]
  (->> word
       kebabify
       (map clacksify)))

(defn clacks->string [clacks]
  (->> clacks
       (map back-to-letter)
       (reduce str)
       de-kebabify))

(string->clacks "Hello this is the message!!!")

(def returned (string->clacks "Hello @£%&^<> this is the message!!??!??!?"))

(clacks->string returned)


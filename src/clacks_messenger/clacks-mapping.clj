(ns clacks-messenger.clacks-mapping
  (require [clojure.string :as clj-str]))

(def alphabet {:a [0,1,0,0,0,1]
               :b [0,0,1,0,1,0]
               :t [1,0,0,1,1,1]
               :. [1,0,1,1,0,1]
               :- [0,0,1,0,0,0]})

(defn reverse-map [m]
  (into {} (map (fn [[a b]] [b a]) m)))

(defn kebabify [string]
  (clj-str/replace string " " "-"))

(defn de-kebabify [string]
  (clj-str/replace string "-" " "))

(defn clacksify [letter]
  (let [character (str letter)]
  (alphabet (keyword character))))

(defn back-to-letter [clack]
  (name ((reverse-map alphabet) clack)))

(defn string->clacks [word]
  (->> word
       kebabify
       (map clacksify)))

(defn clacks->string [clacks]
  (->> clacks
       (map back-to-letter)
       (reduce str)
       de-kebabify))

(string->clacks "bt bt.")

(def returned (string->clacks "bt bt."))

(clacks->string returned)


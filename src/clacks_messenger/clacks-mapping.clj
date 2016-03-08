(ns clacks-messenger.clacks-mapping
  (require [clojure.string :as clj-str]))

(def alphabet {:a [0,1,0,0,0,1]
               :b [0,0,1,0,1,0]
               :c [0,1,0,0,1,0]
               :d [1,0,1,0,0,0]
               :e [1,0,1,1,0,0]
               :f [1,1,0,1,0,0]
               :g [1,0,0,1,1,0]
               :h [1,0,1,0,0,1]
               :i [1,1,1,0,0,0]
               :j [0,0,1,1,1,1]
               :k [0,1,0,1,0,1]
               :l [1,1,1,0,0,1]
               :m [1,1,1,0,1,1]
               :n [0,1,1,1,0,1]
               :o [1,1,0,1,1,0]
               :p [1,1,1,1,1,0]
               :q [1,0,1,1,1,0]
               :r [1,1,1,1,0,0]
               :s [0,1,1,1,1,0]
               :t [1,0,0,1,1,1]
               :u [0,0,1,0,1,1]
               :v [0,1,1,0,0,1]
               :w [1,1,0,1,0,1]
               :x [1,0,1,0,1,0]
               :y [1,0,0,0,1,1]
               :z [1,1,0,0,1,1]
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

(string->clacks "hello this is the message.")

(def returned (string->clacks "hello this is the message."))

(clacks->string returned)


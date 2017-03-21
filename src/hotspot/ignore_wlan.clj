; (ns hotspot.ignore_wlan
;   (:require [clojure.string :as s]
;             [hotspot.filemaps :as filemaps :refer [filemaps]])
;   (:gen-class))
;
; (defn place_tag
;  [fileContent regex tag]
;  (if (nil? (re-find regex fileContent))
;   (str fileContent "\n" (:begin tag) "\n" (:end tag))
;   fileContent))
;
; (defn ignore_wlan0
;  [ignore]
;  (let [filemap (:ignore_wlan0 filemaps)
;        content (place_tag (slurp (:filename filemap)) (:regex filemap) (:tag filemap))]
;   (if ignore
;    (spit (:filename filemap)
;          (s/replace content (:regex filemap) (:replace_text filemap)))
;    (spit (:filename filemap)
;          (s/replace content (:regex filemap) (:restore_text filemap))))))

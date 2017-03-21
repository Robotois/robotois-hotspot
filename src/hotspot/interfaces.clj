(ns hotspot.interfaces
  (:require [clojure.string :as s]
            [hotspot.filemaps :as filemaps :refer [filemaps]])
  (:gen-class))

(defn place_tag
  "Appends the ignore tag at the end of the file content"
 [fileContent regex tag]
 (if (nil? (re-find regex fileContent))
  (str fileContent "\n" (:begin tag) "\n" (:end tag))
  fileContent))

(defn ignore_wlan0
  "Set the config files for ignoring (or not) wlan0"
 [ignore]
 (let [filemap (:ignore_wlan0 filemaps)
       content (place_tag (slurp (:filename filemap)) (:regex filemap) (:tag filemap))]
  (if ignore
   (spit (:filename filemap)
         (s/replace content (:regex filemap) (:replace_text filemap)))
   (spit (:filename filemap)
         (s/replace content (:regex filemap) (:restore_text filemap))))))

(defn interfaces
 [setup_hotspot]
 (let [filemap (:interfaces filemaps)
       content (slurp (:filename filemap))]
  (if setup_hotspot
   (spit (:filename filemap)
         (s/replace content (:regex filemap) (:replace_text filemap)))
   (spit (:filename filemap)
         (s/replace content (:regex filemap) (:restore_text filemap))))))

(defn wpa_settings
 [settings]
 (str "\n    ssid="
  (first (re-find #"(?<=\"ssid\":)([^.]+)(?=,\n)" settings))
  "\n"
  "    psk="
  (first (re-find #"(?<=\"psk\":)([^.]+)(?=,\n)" settings))
  "\n"))

(defn wpa_supplicant
 [setup]
 (if setup
  (let [filemap (:wpa_supplicant filemaps)
        content (slurp (:filename filemap))
        settings (slurp (:filename (:settings filemap)))]
   (spit (:filename filemap)
         (s/replace content (:regex filemap) (wpa_settings settings))))))

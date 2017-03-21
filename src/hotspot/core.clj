(ns hotspot.core
  (:require [hotspot.interfaces :as interfaces :refer [ignore_wlan0 interfaces]]
            [hotspot.hostapd :as hostapd :refer [hostapd_conf hostapd_filename]]
            [hotspot.dnsmasq :as dnsmasq :refer [dnsmasq_file]])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (do
   (println "args: " (nth args 0) (nth args 1))
   (if (= "interfaces" (nth args 0))
    (let [setup (= "true" (nth args 1))]
     (interfaces/ignore_wlan0 setup)
     (interfaces/interfaces setup)
     (interfaces/wpa_supplicant setup)))
   (if (= "hostapd" (nth args 0))
    (let [setup (= "true" (nth args 1))]
     (hostapd/hostapd_conf setup)
     (hostapd/hostapd_filename setup)))
   (if (= "dnsmasq" (nth args 0))
    (let [setup (= "true" (nth args 1))]
     (dnsmasq/dnsmasq_file setup)))))





; (clojure.string/replace (slurp "test/dhcpcd.conf") #"(?<=ignore_wlan0\s)([^.]+)\s#--" "lol yeah..")
; (re-find #"(?<=#--ignore_wlan0)([^.]+)(?=#--)" (slurp "test/dhcpcd.conf"))

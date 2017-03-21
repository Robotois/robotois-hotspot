(ns hotspot.hostapd
  (:require [clojure.string :as s]
            [hotspot.filemaps :as filemaps :refer [filemaps]])
  (:gen-class))

(defn hostapd_conf
 [setup]
 (let [filename "/etc/hostapd/hostapd.conf"]
  (if setup
   (spit filename "# This is the name of the WiFi interface we configured above
interface=wlan0

# Use the nl80211 driver with the brcmfmac driver
driver=nl80211

# This is the name of the network
ssid=robotois0001

# Use the 2.4GHz band
hw_mode=g

# Use channel 6
channel=6

# Enable 802.11n
ieee80211n=1

# Enable WMM
wmm_enabled=1

# Enable 40MHz channels with 20ns guard interval
ht_capab=[HT40][SHORT-GI-20][DSSS_CCK-40]

# Accept all MAC addresses
macaddr_acl=0

# Use WPA authentication
auth_algs=1

# Require clients to know the network name
ignore_broadcast_ssid=0

# Use WPA2
wpa=2

# Use a pre-shared key
wpa_key_mgmt=WPA-PSK

# The network passphrase
wpa_passphrase=robotois

# Use AES, instead of TKIP
rsn_pairwise=CCMP")
   (spit filename ""))))

(defn hostapd_filename
 [setup]
 (let [filemap (:hostapd filemaps)
       content (slurp (:filename filemap))]
  (if setup
      (spit (:filename filemap)
            (s/replace content (:regex filemap) (:replace_text filemap)))
      (spit (:filename filemap)
            (s/replace content (:regex filemap) (:restore_text filemap))))))

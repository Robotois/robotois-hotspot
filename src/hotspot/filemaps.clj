(ns hotspot.filemaps
 (:require [clojure.string :as s])
 (:gen-class))

(def filemaps {:ignore_wlan0 {:filename "/etc/dhcpcd.conf"
                              :tag {:begin "#--ignore_wlan0"
                                    :end "#--"}
                              :regex #"(?<=#--ignore_wlan0)([^.]+)(?=#--)"
                              :replace_text "\ndenyinterfaces wlan0\n"
                              :restore_text "\n"}

               :interfaces {:filename "/etc/network/interfaces"
                            :regex #"(?<=allow-hotplug wlan0)(\n.+)+\.conf\n"
                            :replace_text "\niface wlan0 inet static
    address 172.24.1.1
    netmask 255.255.255.0
    network 172.24.1.0
    broadcast 172.24.1.255
#iface wlan0 inet manual
#    wpa-conf /etc/wpa_supplicant/wpa_supplicant.conf\n"
                            :restore_text "\niface wlan0 inet manual
    wpa-conf /etc/wpa_supplicant/wpa_supplicant.conf\n"}
               :hostapd {:filename "/etc/default/hostapd"
                         :regex #"#*DAEMON_CONF=.+\n"
                         :replace_text "DAEMON_CONF=\"/etc/hostapd/hostapd.conf\"\n"
                         :restore_text "#DAEMON_CONF=\"\"\n"}
               :dnsmasq {:filename "/etc/dnsmasq.conf"}
               :wpa_supplicant {:filename "/etc/wpa_supplicant/wpa_supplicant.conf"
                                :regex #"(?<=network=\{)([^.]+)(?=\})"
                                :settings {:filename "test/wlan_settings.json"
                                           :ssid #"(?<=\"ssid\":)([^.]+)(?=,)"
                                           :psk #"(?<=\"psk\":)([^.]+)(?=\n)"}}})

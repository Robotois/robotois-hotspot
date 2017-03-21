(ns hotspot.dnsmasq
  (:require [clojure.string :as s]
            [hotspot.filemaps :as filemaps :refer [filemaps]])
  (:gen-class))

(def filemap (:dnsmasq filemaps))

(defn backup_file
 [filename]
 (let [dnsfilename (str filename ".orig")]
  (if-not (.exists (clojure.java.io/as-file dnsfilename))
   (spit dnsfilename (slurp filename)))))

(defn dnsmasq_file
 [setup]
 (if setup
  (do
   (backup_file (:filename filemap))
   (spit (:filename filemap) "interface=wlan0      # Use interface wlan0
listen-address=172.24.1.1 # Explicitly specify the address to listen on
bind-interfaces      # Bind to the interface to make sure we aren't sending things elsewhere
server=8.8.8.8       # Forward DNS requests to Google DNS
domain-needed        # Don't forward short names
bogus-priv           # Never forward addresses in the non-routed address spaces.
dhcp-range=172.24.1.50,172.24.1.150,12h # Assign IP addresses between 172.24.1.50 and 172.24.1.150 with a 12 hour lease time"))))

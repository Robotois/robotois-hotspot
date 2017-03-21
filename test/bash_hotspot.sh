echo "================================="
echo "Configuring Conectivity"
echo "================================="

connect_wlan(){
 sudo java -jar target/uberjar/hotspot-0.1.0-SNAPSHOT-standalone.jar interfaces true

# sudo service hostapd stop
# sudo service dnsmasq stop

# sudo service dhcpcd restart
# sudo ifdown wlan0
# sudo ifup wlan0
}

connect_hotspot(){
 sudo java -jar target/uberjar/hotspot-0.1.0-SNAPSHOT-standalone.jar interfaces false
# CONFIGURE HOSTAPD
 sudo java -jar target/uberjar/hotspot-0.1.0-SNAPSHOT-standalone.jar hostapd false
# CONFIGURE DNSMASQ
 sudo java -jar target/uberjar/hotspot-0.1.0-SNAPSHOT-standalone.jar dnsmasq false

# SET UP IPV4 FORWARDING
# sudo sh -c "echo 1 > /proc/sys/net/ipv4/ip_forward"
# sudo iptables -t nat -A POSTROUTING -o eth0 -j MASQUERADE
# sudo iptables -A FORWARD -i eth0 -o wlan0 -m state --state RELATED,ESTABLISHED -j ACCEPT
# sudo iptables -A FORWARD -i wlan0 -o eth0 -j ACCEPT

# Start services
# sudo service hostapd start
# sudo service dnsmasq start
}

# hotspot=$(grep -Po '(?<="hotspot":).*?[^\\]"(?=,)' test/wlan_settings.json);
# $(grep -Po '(?="hotspot":).*?[^\\]"(?=,)' wlan_settings.json)
# cat "wlan_settings.json" | \
    # python -c "import sys, json; print json.load(sys.stdin)['hotspot']"

hotspot="false";

case "$(grep -Po '(?<="hotspot":).*?[^\\]"(?=,)' test/wlan_settings.json)" in
 *true*) connect_wlan;;
 *)      connect_hotspot;;
esac

# echo "Hotspot enable?: " $hotspot

# sudo java -jar target/uberjar/hotspot-0.1.0-SNAPSHOT-standalone.jar interfaces true

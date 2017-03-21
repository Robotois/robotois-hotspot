#! /bin/bash
export PYTHONIOENCODING=utf8
cat "../resources/wlan_settings.txt" | \
    python -c "import sys, json; print json.load(sys.stdin)['ssid']"

cat "../resources/wlan_settings.txt" | while read LINE
do
echo $LINE
done

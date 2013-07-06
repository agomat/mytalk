#!/bin/sh
killall -9 python
killall -9 python2.7
cd ../ && python2.7 -m SimpleHTTPServer 8042 &
echo "HTTP server running at http://127.0.0.1:8042/"
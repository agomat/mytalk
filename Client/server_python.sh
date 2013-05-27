#!/bin/sh
echo "Server started at port 4242. Click here -> http://localhost:4242"
killall -9 python
killall -9 python2.7
python2.7 -m SimpleHTTPServer 4242 &
cd -

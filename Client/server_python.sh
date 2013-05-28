#!/bin/sh
echo "Client ready at port 8042. Click here -> http://localhost:8042"
killall -9 python
killall -9 python2.7
python2.7 -m SimpleHTTPServer 8042 &
cd -

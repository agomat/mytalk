#!/bin/sh
echo "Server started at port 4242. Click here -> http://localhost:4242"
cd web-app && python2.7 -m SimpleHTTPServer 4242


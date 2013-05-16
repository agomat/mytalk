#!/bin/sh
echo "Server started at port 4242. Click here -> http://localhost:4242"
cd web-app && python -m SimpleHTTPServer 4242


var connect = require('connect');
connect.createServer(
  connect.static(__dirname.replace(/\/tools$/g,''))
).listen(8042);
console.log('HTTP server running at http://127.0.0.1:8042/');
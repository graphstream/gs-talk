var connect = require('connect');
connect.createServer(
  connect.static(__dirname+'/app/')
).listen(8080);
console.log("http://localhost:8080/");


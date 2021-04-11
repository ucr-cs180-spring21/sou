var net = require('net');

var HOST = "127.0.0.1";
var PORT = 3000;

net.createServer( function(sock) {
   
    console.log( 'CONNECTED: ' + sock.remoteAdress + ':' + sock.remotePort);

    sock.on( 'data', function(data) {
	console.log( 'Message received: ' + data );

	
	sock.write('Message received: ' + data);
	
    });

    sock.on('close', function(data) {
	console.log( 'CLOSED: ' + sock.remoteAdress + ' ' + sock.remotePort);
    });

}).listen(PORT, HOST);

console.log( 'Server listening on ' + HOST + ':' + PORT);

//var net = require('net');
const fs = require('fs');
const express = require('express');
const app = express();
const host = "127.0.0.1";
const port = 3000;
const path = '\\public';


app.get('/', (req, res) => {
    res.send('Hello World!');
});
app.use('/public', express.static(__dirname + '/public'));
app.listen(port, ()=> console.log('listening on port: ' + port));


/**net.createServer( function(sock) {
   
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
**/

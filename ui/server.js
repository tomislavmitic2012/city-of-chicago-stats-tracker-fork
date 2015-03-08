/* chicagostatstracker.com - v1.0.0 - 01/31/2015
 * http://www.chicagostatstracker.com/
 * Copyright (c) 2014 chicagostatstracker.com */

if (process.argv[2] == null || process.argv[2].match(/minified|expanded/gi) == null) {
    console.log('you must provide a command line argument specifying whether to expand or minify css and js files -->\n\tminified for minified js and css resources\n\texpanded for expanded js and css resources');
    process.exit(1);
}

var connect = require('connect')
    , https = require('https')
    , http = require('http')
    , url = require('url')
    , fs = require('fs')
    , vhost = require('vhost')
    , serverGlobals
    , app;

serverGlobals = require('./globals_prod')['serverGlobals'];

var portNumber = 80;
var sslPortNumber = 443;
var app_en = require('./app_en')['appen'];

app = connect()
    .use(vhost(serverGlobals.vhost_en, app_en))
    .use(vhost(serverGlobals.vhost_en2, app_en));

var options = {
    key : fs.readFileSync('./ssl/key.pem')
    , cert : fs.readFileSync('./ssl/cert.pem')
    , requestCert : true
    , rejectUnathorized : false
};

http.createServer(function(req, res) {
    res.writeHead(301, { "Location": "https://" + req.headers['host'] + req.url });
    res.end();
}).listen(portNumber, function () {
    console.log('tfx.cPortal.Web running on port ' + portNumber + '.')
})
https.createServer(options, app).listen(sslPortNumber, function () {
    console.log('tfx.cPortal.Web running on port ' + sslPortNumber + '.')
});
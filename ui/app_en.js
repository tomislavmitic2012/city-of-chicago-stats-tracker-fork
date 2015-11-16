/* chicagostatstracker.com - v1.0.0 - 01/31/2015
 * http://www.chicagostatstracker.com/
 * Copyright (c) 2015 chicagostatstracker */

var express = require('express')
    , https = require('https')
    , http = require('http')
    , url = require('url')
    , fs = require('fs')
    , ms = require('ms')
    , staticServe = require('serve-static')
    , routes = require('./routes')['routes']
    , serverGlobals
    , app
    , staticOptions = {
        maxAge: ms('2 days')
    };

serverGlobals = require('./globals_prod')['serverGlobals'];

app = express()
    .set('views', [__dirname, '/html/en'].join(''))
    .engine('html', require('ejs').renderFile)
    .use('/css', staticServe(process.argv[2] === 'minified' ? 'css/min' : 'css', staticOptions))
    .use('/fonts', staticServe('fonts', staticOptions))
    .use('/images', staticServe('images', staticOptions))
    .use('/js/app', staticServe(process.argv[2] === 'minified' ? 'js/min/app/en' : 'js/app/en', staticOptions))
    .use('/js/bootstrap', staticServe(process.argv[2] === 'minified' ? 'js/min/bootstrap/en' : 'js/bootstrap/en', staticOptions))
    .use('/js/content', staticServe('js/content/en', staticOptions))
    .use('/js/libs', staticServe('js/libs', staticOptions))
    .use('/js/models', staticServe(process.argv[2] === 'minified' ? 'js/min/models/en' : 'js/models/en', staticOptions))
    .use('/js/utils', staticServe(process.argv[2] === 'minified' ? 'js/min/utils' : 'js/utils', staticOptions))
    .use('/js/templates', staticServe('js/templates/en', staticOptions))
    .use('/js/viewmodels', staticServe(process.argv[2] === 'minified' ? 'js/min/viewmodels' : 'js/viewmodels', staticOptions));

app.use(function(req, res, next) {
    if (routes[url.parse(req.url).pathname] != null) {
        next();
    } else {
        if (url.parse(req.url).pathname === '/') {
            res.render(routes['/signin']);
        } else {
            res.render(routes['/404']);
        }
    }
});

app.get(routes['keys'][0], function(req, res) {
    res.render(routes[url.parse(req.url).pathname]);
});

app.get(routes['keys'][1], function(req, res) {
    res.render(routes[url.parse(req.url).pathname]);
});

app.get(routes['keys'][3], function(req, res) {
    res.render(routes[url.parse(req.url).pathname]);
});

app.get(routes['keys'][4], function(req, res) {
    res.render(routes[url.parse(req.url).pathname]);
});
app.get(routes['keys'][5], function(req, res) {
    res.render(routes[url.parse(req.url).pathname]);
});
module.exports.appen = app;
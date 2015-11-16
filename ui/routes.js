/* chicagostatstracker.com - v1.0.0 - 01/31/2015
 * http://www.chicagostatstracker.com/
 * Copyright (c) 2015 chicagostatstracker.com */

routes = {
    '/dashboard' : 'index.html'
    , '/signin' : 'signIn.html'
    , '/404' : '404.html'
    , '/createaccount' : 'createAccount.html'
    , '/getpassword' : 'getPassword.html'
    , '/resetPassword' : 'resetPassword.html'
    , 'keys' : ['/dashboard', '/signin', '/404', '/createaccount', '/getpassword','/resetpassword']
};

module.exports.routes = routes;
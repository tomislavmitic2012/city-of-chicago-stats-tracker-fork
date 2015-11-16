/**
 * Created by adampodraza on 4/19/15.
 */
require.config({
    baseUrl: 'js',
    shim: {
        parsley: {
            deps: ['jquery']
        },
        bootstrap: {
            deps: ['jquery']
        },
        cookie: {
            deps: ['jquery']
        },
        knockout: {
            deps: ['jquery']
        },
        knockout_mapping: {
            deps: ['knockout','jquery']
        },
        utils : {
            deps: ['jquery']
        }
    },
    paths: {
        jquery: 'libs/jquery',
        knockout: 'libs/knockout',
        knockout_mapping: 'libs/knockout.mapping',
        domReady: 'libs/domReady',
        cookie: 'libs/jquery.cookie',
        xdomain: 'libs/jquery.xdomainrequest',
        parsley: 'libs/parsley',
        bootstrap: 'libs/bootstrap',
        moment: 'libs/moment',
        bowser: 'libs/bowser',
        html5shiv: 'libs/html5shiv',
        respond: 'libs/respond.min',
        globals : 'utils/globals',
        utils: 'utils/utils',
        createaccountViewModel: "viewmodels/createaccountViewModel"
    }
});

define(function (require) {

    var $ = require('jquery');
    var cookie = require('cookie');
    var domReady = require('domReady');
    var ko = require('knockout');
    var koMap = require('knockout_mapping');
    var xdomain = require('xdomain');
    var parsley = require('parsley');
    var bowser = require('bowser');
    var bootstrap = require('bootstrap');
    var moment = require('moment');
    var globals = require('globals');
    var utils = require('utils');
    var createaccountViewModel = require('createaccountViewModel');

    var html5shiv;
    var respond;
    if (bowser.msie && bowser.version < 9) {
        html5shiv = require('html5shiv');
        respond = require('respond')
    }

    if ($.cookie(window.chicago_stats_globals.x_auth_token_cookie_key)) {
        $.removeCookie(window.chicago_stats_globals.x_auth_token_cookie_key);
        $.removeCookie(window.chicago_stats_globals.uuid_cookie_key);
        $.removeCookie(window.chicago_stats_globals.roles_key);
    }

    domReady(function () {
        //Create Account Functionality

        new createaccountViewModel();
    });

});
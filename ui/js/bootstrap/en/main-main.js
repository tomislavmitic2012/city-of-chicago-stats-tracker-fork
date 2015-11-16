/* chicagostatstracker.com - v1.0.0 - 02/01/2015
 * http://www.chicagostatstracker.com/
 * Copyright (c) 2014 chicagostatstracker.com */

require.config({
    baseUrl: 'js',
    shim: {
        parsley: {
            deps: ['jquery']
        },
        bootstrap: {
            deps: ['jquery']
        },
        bootstraptable: {
            deps: ['jquery', 'bootstrap']
        },
        bootstraptable_en: {
            deps: ['jquery', 'bootstrap', 'bootstraptable']
        },
        bootstraptable_export: {
            deps: ['jquery', 'bootstrap', 'bootstraptable']
        },
        bootstraptable_filter: {
            deps: ['jquery', 'bootstrap', 'bootstraptable']
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
        },
        tableExport : {
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
        bootstraptable: 'libs/bootstrap-table',
        bootstraptable_en: 'libs/bootstrap-table-en-US',
        bootstraptable_export: 'libs/bootstrap-table-export',
        bootstraptable_filter: 'libs/bootstrap-table-filter',
        moment: 'libs/moment',
        chart: 'libs/Chart',
        bowser: 'libs/bowser',
        html5shiv: 'libs/html5shiv',
        respond: 'libs/respond.min',
        text: 'libs/text',
        globals : 'utils/globals',
        utils: 'utils/utils',
        tableExport: 'utils/tableExport.min',
        filterViewModel: 'viewmodels/filterViewModel',
        myDataViewModel: 'viewmodels/myDataViewModel',
        overviewViewModel: 'viewmodels/overviewViewModel',
        chartViewModel: 'viewmodels/chartViewModel',
        myProfileViewModel: 'viewmodels/myProfileViewModel',
        navigationScroll: 'utils/navigationScroll'
    }
});

define(function (require) {

    var $ = require('jquery');
    var navigationScroll = require('navigationScroll');
    var cookie = require('cookie');
    var domReady = require('domReady');
    var ko = require('knockout');
    var koMap = require('knockout_mapping');
    var xdomain = require('xdomain');
    var parsley = require('parsley');
    var bowser = require('bowser');
    var bootstrap = require('bootstrap');
    var bootstraptable = require('bootstraptable');
    var bootstraptable_en = require('bootstraptable_en');
    var bootstraptable_export = require('bootstraptable_export');
    var bootstraptable_filter = require('bootstraptable_filter');
    var moment = require('moment');
    var chart = require('chart');
    var text = require('text');
    var globals = require('globals');
    var utils = require('utils');
    var tableExport = require('tableExport');
    var myDataViewModel = require('myDataViewModel');
    var filterViewModel = require('filterViewModel');
    var overviewViewModel = require('overviewViewModel');
    var chartViewModel = require('chartViewModel');
    var myProfileViewModel = require('myProfileViewModel');
    var html5shiv;
    var respond;
    if (bowser.msie && bowser.version < 9) {
        html5shiv = require('html5shiv');
        respond = require('respond')
    }

    if ($.cookie(window.chicago_stats_globals.x_auth_token_cookie_key) == null) {
        applicationRedirect(window.chicago_stats_globals.app_paths.signin);
    } else {
        domReady(function () {
            // Check the authenitcation token
            checkAuthenticationToken();

            // Logout functionality
            initiateLogout();

            // Call navigation scroll functions
            new navigationScroll();

            //display chart
            window.chicago_stats_app['chartViewModel'] = new chartViewModel();

            //load and add filter box
            window.chicago_stats_app['filterViewModel'] = new filterViewModel();

            //display my data set
            window.chicago_stats_app['myDataViewModel'] = new myDataViewModel();

            //display overview table
            window.chicago_stats_app['overviewViewModel'] = new overviewViewModel();
            
            //display myProfile table
            window.chicago_stats_app['myProfileViewModel'] = new myProfileViewModel();
        });
    }
});


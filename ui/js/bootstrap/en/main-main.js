/* chicagostatstracker.com - v1.0.0 - 02/01/2015
 * http://www.chicagostatstracker.com/
 * Copyright (c) 2014 chicagostatstracker.com */

require.config({
    baseUrl: 'js',
    shim: {
        parsley: {
            deps: ['jquery']
        },
        parsely_remote: {
            deps: ['jquery', 'parsley']
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
        jqmobile: {
            deps: ['jquery']
        },
        knockout: {
            deps: ['jquery']
        },
        knockout_mapping: {
            deps: ['knockout','jquery']
        }
    },
    paths: { 
        jquery: 'libs/jquery',
        jquery_metisMenu: 'libs/jquery.metisMenu',
        jqmobile: 'libs/jquery.mobile',
        knockout: 'libs/knockout',
        knockout_mapping: 'libs/knockout.mapping',
        domReady: 'libs/domReady',
        cookie: 'libs/jquery.cookie',
        xdomain: 'libs/jquery.xdomainrequest',
        parsley: 'libs/parsley',
        parsley_remote: 'libs/parsley.remote',
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
        utils: 'utils/utils',
        filterViewModel: 'libs/viewmodel/filterViewModel',
        myDataViewModel:'libs/viewmodel/myDataViewModel',
        overviewViewModel:'libs/viewmodel/overviewViewModel',
        chartViewModel:'libs/viewmodel/chartViewModel',
        navigationScroll:'libs/scripts/navigationScroll'
       
    }
});

define(function (require) {

    var $ = require('jquery');
    var navigationScroll = require('navigationScroll');
    var jqmobile = require('jqmobile');
    var cookie = require('cookie');
    var domReady = require('domReady');
    var ko = require('knockout');
    var koMap = require('knockout_mapping');
    var xdomain = require('xdomain');
    var parsley = require('parsley');
    var parsley_remote = require('parsley_remote');
    var bowser = require('bowser');
    var bootstrap = require('bootstrap');
    var bootstraptable = require('bootstraptable');
    var bootstraptable_en = require('bootstraptable_en');
    var bootstraptable_export = require('bootstraptable_export');
    var bootstraptable_filter = require('bootstraptable_filter');
    var moment = require('moment');
    var chart = require('chart');
    var text = require('text');
    var utils = require('utils');
    var myDataViewModel = require('myDataViewModel');
    var filterViewModel = require('filterViewModel');
    var overviewViewModel = require('overviewViewModel');
    var chartViewModel = require('chartViewModel');
    var html5shiv;
    var respond;
    if (bowser.msie && bowser.version < 9) {
        html5shiv = require('html5shiv');
        respond = require('respond')
    }

    domReady(function () {

        // Call navigation scroll functions
        navigationScroll(); 

        //display chart
        chartViewModel();

        //load and add filter box
        filterViewModel();

        //display my data set
        myDataViewModel();

        //display overview table
        overviewViewModel();
       

    });
    
});


/* chicagostatstracker.com - v1.0.0 - 04/14/2015
 * http://www.chicagostatstracker.com/
 * Copyright (c) 2015 chicagostatstracker.com */

define(['knockout', 'jquery', 'knockout_mapping'], function(ko, $, mappping) {

    return function _siginViewModel() {

        var self = this;
        self.email = ko.observable();
        self.password = ko.observable();

        self.login = function(viewmodel, e) {
            e.preventDefault();
            var p = new Parsley('#login_form');
            if (p.validate()) {
                loginUser(self.email(), self.password());
            }
            return false;
        };

        init.call(self);

        function init() {
            ko.applyBindings(self, $('#login')[0]);
        }
    };
});
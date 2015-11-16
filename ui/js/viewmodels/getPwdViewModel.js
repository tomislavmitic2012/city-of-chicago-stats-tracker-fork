/**
 * Tu Vo 4/27/2015
 */


define(['knockout', 'jquery', 'knockout_mapping'], function(ko, $, mappping) {

    return function _retrievePasswordViewModel() {

        var self = this;
        self.email = ko.observable();
        self.submitEmail = function(viewmodel, e) {
            e.preventDefault();
            var p = new Parsley('#password_form');
            if (p.validate()) {
                getUserEmail(self.email());
            }
            return false;
        };

        init.call(self);

        function init() {
            ko.applyBindings(self, $('#getpassword')[0]);
        }
    };
});
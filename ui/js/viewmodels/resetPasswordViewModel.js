/**
 * Created by Tu Vo on 5/1/15.
 */

define(['knockout', 'jquery', 'knockout_mapping'], function(ko, $, mappping) {

    return function _createaccountViewModel() {

        var p = new Parsley('#resetPassword_form');

        var self = this;
        self.securityQuestion = ko.observable();
        self.password = ko.observable();
        self.password2 = ko.observable();

        self.uuid = ko.observable(getParameter('uuid'));

        self.resetPasswrd = function(viewmodel, e) {
            e.preventDefault();

            if(p.validate()) {
                resetPassword( self.uuid(), self.securityQuestion(), self.password());
            }
            return false;

        };

        init.call(self);

        function init() {

            ko.applyBindings(self, $('#resetPassword')[0]);
        }

    }

});

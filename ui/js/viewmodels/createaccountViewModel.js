/**
 * Created by adampodraza on 4/19/15.
 */

define(['knockout', 'jquery', 'knockout_mapping'], function(ko, $, mappping) {

    return function _createaccountViewModel() {

        var p = new Parsley('#createaccount_form');

        var self = this;
        self.email = ko.observable();
        self.password = ko.observable();
        self.password2 = ko.observable();

        self.uuid = ko.observable(generateUUID());
        self.firstname = ko.observable();
        self.lastname = ko.observable();

        self.createAccount = function(viewmodel, e) {
            e.preventDefault();

            if(p.validate()) {
                createUser(self.email(), self.password(), self.uuid(), self.firstname(), self.lastname());
            }
            return false;

        };

        init.call(self);

        function init() {

            ko.applyBindings(self, $('#createaccount')[0]);
        }

    }

});

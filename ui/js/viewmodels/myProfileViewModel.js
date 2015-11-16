/**
 * Created By Tu,Vo 5/2/2015
 */
/* chicagostatstracker.com - v1.0.0 - 04/14/2015
 * http://www.chicagostatstracker.com/
 * Copyright (c) 2015 chicagostatstracker.com */

define(['knockout', 'jquery', 'knockout_mapping'], function(ko, $, mappping) {

    return function _myProfileViewModel() {

        var data = {};
        var uuidTkn =  $.cookie(window.chicago_stats_globals.uuid_cookie_key);

        ajaxGet(data, [window.chicago_stats_globals.middleware_resources['get_user_uuid'],
                       ['uuid',uuidTkn].join('=')].join('?'),
    	  function(data, textStatus, xhr) {
            
            var self = this;
          
            self.email = ko.observable(data.email);
            self.firstName = ko.observable(data.first_name);
            self.lastName = ko.observable(data.last_name);
            self.pwdNew = ko.observable();
            self.pwdOld = ko.observable();
                
            self.save_changes = function(viewmodel, e) {
            	  e.preventDefault();
                  var p = new Parsley('#profile_form');
                  if(p.validate()){
                	  update_user(self.email,self.firstName,self.lastName,self.pwdOld,self.pwdNew,uuidTkn);      
                  }
            };
            
            init.call(self);
            
            function init() {
                ko.applyBindings(self, $('#myProfile')[0]);    
            }        
        },function(data, textStatus, xhr) {
            console.log(data);
            console.log(textStatus);
            console.log(xhr);
        });
    }    
    function update_user(email,first,last,oldpwd,newpwd,uuidTkn){

        var d = {
                email : email,
                first : first,
                last : last,
                oldpassword : oldpwd,
                password : newpwd,
                uuid : uuidTkn
        }
        formPost(d,window.chicago_stats_globals.middleware_resources.update_user, function(data, textStatus, xhr) {
            console.log(data);
            console.log(textStatus);
            console.log(xhr);
            showModal(window.chicago_stats_globals.PROFILE_UPDATE);
         
        },function(data, textStatus, xhr) {
            console.log(data);
            console.log(textStatus);
            console.log(xhr);
            showModal(window.chicago_stats_globals.INCORRECT_PASSWORD,
                [data['status'], ': ' , xhr].join(''))
        });
    }
    
});
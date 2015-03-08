/* chicagostatstracker.com - v1.0.0 - 02/01/2015
 * http://www.chicagostatstracker.com/
 * Copyright (c) 2014 chicagostatstracker.com */

if ( window.XDomainRequest ) {
    var xdr;

    $.ajax = function(obj) {
        var self = this;
        var url = obj.url;
        xdr = new XDomainRequest();
        if (xdr) {
            xdr.onload = function() {
                if (xdr.responseText) {
                    var data = jQuery.parseJSON(xdr.responseText);
                }
                obj.success.call(self, data);
            }
            xdr.onerror = obj.error
            xdr.open('POST', url);
            xdr.send(obj.data);
        }
    };
}
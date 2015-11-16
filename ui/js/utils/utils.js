/* chicagostatstracker.com - v1.0.0 - 02/01/2015
 * http://www.chicagostatstracker.com/
 * Copyright (c) 2014 chicagostatstracker.com */

function createUser(email, password, uuid, firstname, lastname) {
    var data = {
        username : email,
        password : password,
        uuid : uuid,
        firstName : firstname,
        lastName : lastname
    };
    formPost(data, window.chicago_stats_globals.middleware_resources.create_account, function(data, textStatus, xhr) {

        console.log(data);
        console.log(textStatus);
        console.log(xhr);

        loginUser(email, password);
    });

}
function getUserEmail(email){
    var data= {
       email: email
    };
    formPost(data, window.chicago_stats_globals.middleware_resources.get_user_email, function(data, textStatus, xhr) {

        console.log(data);
        console.log(textStatus);
        console.log(xhr);

        if(data['email']!=null){
            showModal(window.chicago_stats_globals.GENERAL_MODAL_TITLE,
                    window.chicago_stats_globals.EMAIL_SENT_TO_ADDRESS);
        }else{
            showModal(window.chicago_stats_globals.GENERAL_MODAL_TITLE,
                    window.chicago_stats_globals.UNABLE_TO_FIND_EMAIL_LISTED);
        }
    });
};

function resetPassword(uuid, question, password){
    var data= {
        uuid: uuid,
        question: question,
        password: password
    };
    formPost(data, window.chicago_stats_globals.middleware_resources.reset_password, function(data, textStatus, xhr) {

        console.log(data);
        console.log(textStatus);
        console.log(xhr);
        
        if(data['uuid']){
            applicationRedirect(window.chicago_stats_globals.app_paths.signin);
        }
    },function(data, textStatus, xhr) {
        console.log(data);
        console.log(textStatus);
        console.log(xhr);
        showModal(window.chicago_stats_globals.INCORRECT_SECURITY_QUESTION,
            [data['status'], ': ' , xhr].join(''))
    });
    console.log(uuid);
    console.log(question);
};

function loginUser(email, password) {
    var data = {
        username : email,
        password : password
    };
    formPost(data, window.chicago_stats_globals.middleware_resources.authenticate, function(data, textStatus, xhr) {
        console.log(data);
        console.log(textStatus);
        console.log(xhr);
        if (data['token']) {
            $.cookie(window.chicago_stats_globals.x_auth_token_cookie_key, data['token']);
            getUserSecurityContext(window.chicago_stats_globals.middleware_resources.authenticate, function(data, textStatus, xhr) {
                console.log(data);
                console.log(textStatus);
                console.log(xhr);
                $.cookie(window.chicago_stats_globals.uuid_cookie_key, data['uuid']);
                $.cookie(window.chicago_stats_globals.roles_key, JSON.stringify(data['roles']));
                applicationRedirect(window.chicago_stats_globals.app_paths.dashboard);
            }, function(data, textStatus, xhr) {
                console.log(data);
                console.log(textStatus);
                console.log(xhr);
                showModal(window.chicago_stats_globals.GENERAL_MODAL_TITLE,
                    window.chicago_stats_globals.UNABLE_TO_GET_USER_SECURITY_CONTEXT_MESSAGE);
            });
        } else {
            showModal(window.chicago_stats_globals.GENERAL_MODAL_TITLE,
                window.chicago_stats_globals.UNABLE_TO_CREATE_TOKEN_MESSAGE);
        }
    }, function(data, textStatus, xhr) {
        console.log(data);
        console.log(textStatus);
        console.log(xhr);
        showModal(window.chicago_stats_globals.GENERAL_MODAL_TITLE,
            [data['status'], ': ' , xhr].join(''))
    });
}

function checkAuthenticationToken() {
    var interval = setInterval(function() {
        ajaxGetNoQueryParams(window.chicago_stats_globals.middleware_resources.authenticate, function (data, textStatus, xhr) {
            console.log(['%% Authentication token ',
                $.cookie(window.chicago_stats_globals.x_auth_token_cookie_key),
                ' still valid %%'].join(''));
        }, function (data, textStatus, xhr) {
            clearInterval(interval);
            removeCookies();
            applicationRedirect(window.chicago_stats_globals.app_paths.signin);
        });
    }, window.chicago_stats_globals.CHECK_AUTH_TOKEN_INTERVAL);
}

function getUserSecurityContext(resource, cbSuccess, cbFail) {
    ajaxGetNoQueryParams(resource, cbSuccess, cbFail);
}

function formPost(data, resource, cbSuccess, cbFail) {
    $.ajax({
        method : 'POST',
        url : [window.chicago_stats_globals.middleware_url, resource].join(''),
        contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
        data : data
    }).done(function(data, textStatus, xhr) {
        if (cbSuccess) {
            cbSuccess(data, textStatus, xhr);
        }
    }).fail(function(data, textStatus, xhr) {
        if (cbFail) {
            cbFail(data, textStatus, xhr);
        }
    });
}

function ajaxPost(data, resource, cbSuccess, cbFail) {
    if ($.cookie(window.chicago_stats_globals.x_auth_token_cookie_key) != null) {
        var headers = {};
        headers[window.chicago_stats_globals.x_auth_token_key] = $.cookie(window.chicago_stats_globals.x_auth_token_cookie_key);
        $.ajax({
            method: 'POST',
            url: [window.chicago_stats_globals.middleware_url, resource].join(''),
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data),
            headers : headers
        }).done(function (data, textStatus, xhr) {
            if (cbSuccess) {
                cbSuccess(data, textStatus, xhr);
            }
        }).fail(function (data, textStatus, xhr) {
            if (cbFail) {
                cbFail(data, textStatus, xhr);
            }
        });
    } else {
        applicationRedirect(window.chicago_stats_globals.app_paths.signin);
    }
}

function ajaxGetNoQueryParams(resource, cbSuccess, cbFail) {
    if ($.cookie(window.chicago_stats_globals.x_auth_token_cookie_key) != null) {
        var headers = {};
        headers[window.chicago_stats_globals.x_auth_token_key] = $.cookie(window.chicago_stats_globals.x_auth_token_cookie_key);
        $.ajax({
            method: 'GET',
            url: [window.chicago_stats_globals.middleware_url, resource].join(''),
            contentType: 'application/json; charset=UTF-8',
            headers : headers
        }).done(function (data, textStatus, xhr) {
            if (cbSuccess) {
                cbSuccess(data, textStatus, xhr);
            }
        }).fail(function (data, textStatus, xhr) {
            if (cbFail) {
                cbFail(data, textStatus, xhr);
            }
        });
    } else {
        applicationRedirect(window.chicago_stats_globals.app_paths.signin);
    }
}

function ajaxMultiGet(resources, cbSuccess, cbFail) {
    if ($.cookie(window.chicago_stats_globals.x_auth_token_cookie_key) != null) {
        $.when.all(auxilaryDeferreds(resources, 'GET')).then(cbSuccess ? cbSuccess : function() {},
            cbFail ? cbFail : function(data) {});
    } else {
        applicationRedirect(window.chicago_stats_globals.app_paths.signin);
    }
}

function auxilaryDeferreds(resources, method) {
    var headers = {};
    headers[window.chicago_stats_globals.x_auth_token_key] = $.cookie(window.chicago_stats_globals.x_auth_token_cookie_key);
    var defereds = [];
    resources.forEach(function (resource) {
        defereds.push($.ajax({
            method : method
            , url: [window.chicago_stats_globals.middleware_url, resource].join('')
            , contentType: 'application/json; charset=UTF-8'
            , headers : headers
        }));
    });
    return defereds;
}

function ajaxGet(params, resource, cbSuccess, cbFail) {
    if ($.cookie(window.chicago_stats_globals.x_auth_token_cookie_key) != null) {
        var headers = {};
        headers[window.chicago_stats_globals.x_auth_token_key] = $.cookie(window.chicago_stats_globals.x_auth_token_cookie_key);
        var query = buildQueryString(params);
        var url = [window.chicago_stats_globals.middleware_url, resource].join('');
        $.ajax({
            method: 'GET',
            url: [url, query].join(''),
            contentType: 'application/json; charset=UTF-8',
            headers : headers
        }).done(function (data, textStatus, xhr) {
            cbSuccess(data, textStatus, xhr);
        }).fail(function (data, textStatus, xhr) {
            cbFail(data, textStatus, xhr);
        });
    } else {
        applicationRedirect(window.chicago_stats_globals.app_paths.signin);
    }
}

function buildQueryString(params) {
    var q = [];
    if (Object.prototype.toString.call(params) === '[object Object]') {
        for (var k in params) {
            if (params.hasOwnProperty(k)) {
                var sub = [];
                sub.push(k);
                sub.push(params[k]);
                q.push(sub.join('='));
            }
        }
    } else if (Object.prototype.toString.call(params) === '[object Array]') {
        params.forEach(function(datum) {
            q.push(datum);
        });
    }
    return encodeURI(q.join('&'));
}

function applicationRedirect(path) {
    window.location = [window.location.protocol, '//',
        window.location.host, path].join('')
}

function showModal(title, body, isStatic) {
    $('#myModal .modal-dialog').removeClass('modal-lg');
    $('#modal_title').html(null).html(title);
    $('#modal_body').html(null).html(body);
    $('#myModal').modal({
        'backdrop' : isStatic ? 'static' : true
    });
}

function showModalWithTable(title, columns, data, isStatic) {
    $('#myModal .modal-dialog').addClass('modal-lg');
    $('#modal_title').html(null).html(title);
    $('#modal_body').html(null).html('<table id="table"></table>');
    $('#modal_body #table').bootstrapTable({
        striped : true
        , columns : columns
        , data : data
        , pagination : true
    });
    $('#modal_body #table').on('click', '.violations', function(e) {
        e.preventDefault();
        $(e.target).popover('show')
        return false;
    });
    $('#myModal').modal({
        'backdrop' : isStatic ? 'static' : true
    });
}

function showAlert(sectionId, msg) {
    $(sectionId).append([
        '<div class="alert alert-warning alert-dismissible fade in" role="alert">',
        '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>',
        msg, '</div>'].join(''));
}

function createTable(columns, data, table_id, cb, msg) {
    var self = this;
    if (data.length == 0) {
        showModal(window.chicago_stats_globals.GENERAL_MODAL_TITLE, msg);
        return;
    }
    $(table_id).bootstrapTable('destroy');
    $(table_id).bootstrapTable({
        striped : true
        , columns : columns
        , data : data
        , pagination : true
        , sortable : true
        , sortOrder : 'desc'
    });
    if (cb) {
        cb.call(self)
    }
}

function initiateLogout() {
    $('#logout').on('click', function(e) {
        e.preventDefault();
        removeCookies();
        applicationRedirect(window.chicago_stats_globals.app_paths.signin);
        return false;
    });
}

function removeCookies() {
    $.removeCookie(window.chicago_stats_globals.x_auth_token_cookie_key);
    $.removeCookie(window.chicago_stats_globals.uuid_cookie_key);
    $.removeCookie(window.chicago_stats_globals.roles_key);
}

/** Extend jQuery with a when.all method **/
if ($.when.all===undefined) {
    $.when.all = function(deferreds) {
        var deferred = new $.Deferred();
        $.when.apply($, deferreds).then(
            function() {
                deferred.resolve(Array.prototype.slice.call(arguments));
            },
            function() {
                deferred.fail(Array.prototype.slice.call(arguments));
            });

        return deferred;
    }
}
/** pull parameter from URL*/
function getParameter(theParameter) { 
    var params = window.location.search.substr(1).split('&');

    for (var i = 0; i < params.length; i++) {
        var p=params[i].split('=');
        if (p[0] == theParameter) {
            return decodeURIComponent(p[1]);
        }
      }
      return false;
    }

function generateUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
        return v.toString(16);
    });
}

// scroll function
function scrollToID(id, speed){
    var offSet = 50;
    var targetOffset = $(id).offset().top - offSet;
    $('html,body').animate({ scrollTop: targetOffset }, speed);
}

function filterData(data, removeVal) {
    return data.filter(function (val) {
        return val !== removeVal;
    });
}

function buildQuery(items) {
    var that = this;
    var queryScafold = {};
    items.forEach(function(datum) {
        if (queryScafold[datum.filterKey()] == null) {
            queryScafold[datum.filterKey()] = [];
        }
        if (datum.filterValue()) {
            if (window.chicago_stats_globals.filter_dataset_types.STRING.indexOf(datum.filterKey()) != -1) {
                queryScafold[datum.filterKey()].push(datum.filterValue());
            } else if (window.chicago_stats_globals.filter_dataset_types.INT.indexOf(datum.filterKey()) != -1) {
                queryScafold[datum.filterKey()].push(parseInt(datum.filterValue()));
            }
        }
    });
    console.log(queryScafold);
    var query = {};
    var obj;
    for (var n in queryScafold) {
        obj = {};
        if (queryScafold.hasOwnProperty(n)) {
            if (queryScafold[n].length > 1) {
                obj[window.chicago_stats_globals.filter_comparison] = queryScafold[n];
                query[n] = obj;
            } else {
                query[n] = queryScafold[n][0]
            }
        }
    }
    console.log(query);
    return [JSON.stringify(query)];
}
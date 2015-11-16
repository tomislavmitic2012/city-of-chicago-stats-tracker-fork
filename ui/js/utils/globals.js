/* chicagostatstracker.com - v1.0.0 - 02/01/2015
 * http://www.chicagostatstracker.com/
 * Copyright (c) 2014 chicagostatstracker.com */

window.chicago_stats_globals = {};
window.chicago_stats_globals.middleware_url = 'https://middleware.chicagostatstracker.com:8443/socrata_chicago_data_access';

window.chicago_stats_globals.middleware_resources = {
      'update_user' : '/authenticate/update_user'
    , 'get_user_uuid' : '/authenticate/get_user_uuid'
    , 'authenticate' : '/authenticate'
    , 'mongo/getCollectionNames' : '/mongo/getCollectionNames'
    , 'mongo/getCollectionByNames' : '/mongo/getCollectionByNames'
    , 'favoriteDatasets/get_favoriteDatasetsList_by_Uuid' : '/favoriteDatasets/get_favoriteDatasetsList_by_Uuid'
    , 'favoriteDatasets/create_favoriteDatasets' : '/favoriteDatasets/create_favoriteDatasets'
    , 'create_account' : '/authenticate/create_user'
    , 'get_user_email' : '/authenticate/get_user_email'
    , 'reset_password' : '/authenticate/reset_password'
    , 'mongo/findAllByParams/crimes' : '/mongo/findAllByParams/crimes?params='
    , 'mongo/findAllByParams/foodinspections' : '/mongo/findAllByParams/foodinspections?params='
    , 'mongo/findAllByParams/potholes' : '/mongo/findAllByParams/potholes?params='
    , 'mongo/findAllByParams/salaries' : '/mongo/findAllByParams/salaries?params='
    , 'chart' : '/chart/getCollectionByNames'

};

window.chicago_stats_globals.app_paths = {
    'signin' : '/signin'
    , 'dashboard' : '/dashboard'
    , 'createaccount' : '/createaccount'
    , 'getpassword' : '/getpassword'
    , 'resetpassword' :'/resetpassword'
};

window.chicago_stats_globals.collection_info = {
    'crimes' : {
        'resource' : '/mongo/findTop50/crimes'
        , 'title' : 'Chicago Crimes Dataset'
        , 'explanation' : 'This dataset represents crime data within the City of Chicago since 2001.'
    }
    , 'foodinspections' : {
        'resource' : '/mongo/findTop50/foodinspections'
        , 'title' : 'Food Inspection Dataset'
        , 'explanation' : 'This dataset represents all food inspections within the City of Chicago.'
    }
    , 'potholes' : {
        'resource' : '/mongo/findTop50/potholes'
        , 'title' : 'Pot holes Dataset'
        , 'explanation' : 'This data set represents potholes repaired in the last week.'
    }
    , 'salaries' : {
        'resource' : '/mongo/findTop50/salaries'
        , 'title' : 'City of Chicago Employee Salary Dataset'
        , 'explanation' : 'This dataset represents current City of Chicago employee salaries.'
    }
};

window.chicago_stats_globals.top_50_table_props = {
    '/mongo/findTop50/crimes' : {
        columns : [{
            'field' : 'arrest'
            , 'title' : 'Arrest'
        }, {
            'field' : 'beat'
            , 'title' : 'Beat'
        }, {
            'field' : 'block'
            , 'title' : 'Block'
        }, {
            'field' : 'ward'
            , 'title' : 'Ward'
        }, {
            'field' : 'case_number'
            , 'title' : 'Case Number'
        }, {
            'field' : 'date'
            , 'title' : 'Date'
        }, {
            'field' : 'primary_type'
            , 'title' : 'Primary Type'
        }, {
            'field' : 'description'
            , 'title' : 'Description'
        }, {
            'field' : 'location_description'
            , 'title' : 'Location Description'
        }]
    }
    , '/mongo/findTop50/foodinspections' : {
        columns : [{
            'field' : 'aka_name'
            , 'title' : 'AKA Name'
        }, {
            'field' : 'address'
            , 'title' : 'Address'
        }, {
            'field' : 'city'
            , 'title' : 'City'
        }, {
            'field' : 'state'
            , 'title' : 'state'
        }, {
            'field' : 'zip'
            , 'title' : 'Zip'
        }, {
            'field' : 'facility_type'
            , 'title' : 'Facility Type'
        }, {
            'field' : 'inspection_date'
            , 'title' : 'Inspection Date'
        }, {
            'field' : 'inspection_type'
            , 'title' : 'Inspection Type'
        }, {
            'field' : 'inspection_id'
            , 'title' : 'Inspection ID'
        } , {
            'field' : 'results'
            , 'title' : 'Results'
        }, {
            'field' : 'violations'
            , 'title' : 'Violations'
            , 'formatter' : function(value) {
                return ['<a tabindex="0" class="btn btn-lg btn-danger violations"',
                        'role="button" data-toggle="popover"  data-placement="left"',
                        'data-trigger="focus" title="Violations" data-container="body"',
                        'data-content="', value, '">Violation</a>'].join('');
            }
        }]
    }
    , '/mongo/findTop50/potholes' : {
        columns : [{
            'field' : 'creation_date'
            , 'title' : 'Creation Date'
        }, {
            'field' : 'completion_date'
            , 'title' : 'Completion Date'
        }, {
            'field' : 'street_address'
            , 'title' : 'Street Address'
        }, {
            'field' : 'zip'
            , 'title' : 'Zip'
        }, {
            'field' : 'ward'
            , 'title' : 'Ward'
        }, {
            'field' : 'type_of_service_request'
            , 'title' : 'Type Of Service Request'
        }, {
            'field' : 'service_request_number'
            , 'title' : 'Service Request Number'
        }, {
            'field' : 'status'
            , 'title' : 'Status'
        }, {
            'field' : 'current_activity'
            , 'title' : 'Current Activity'
        }, {
            'field' : 'most_recent_action'
            , 'title' : 'Most Recent Action'
        }, {
            'field' : 'number_of_potholes_filled_on_block'
            , 'title' : 'No. Of Potholes Filled On Block'
        }]
    }
    , '/mongo/findTop50/salaries' : {
        columns : [{
            'field' : 'department'
            , 'title' : 'Department'
        }, {
            'field' : 'employee_annual_salary'
            , 'title' : 'Employee Annual Salary'
        }, {
            'field' : 'name'
            , 'title' : 'Name'
        }, {
            'field' : 'job_titles'
            , 'title' : 'Position Title'
        }]
    }
};

window.chicago_stats_globals.find_all_by_params_props = {
    '/mongo/findAllByParams/crimes': {
        columns: [{
            'field' : 'arrest'
            , 'title' : 'Arrest'
            , 'sortable' : true
        }, {
            'field' : 'beat'
            , 'title' : 'Beat'
            , 'sortable' : true
        }, {
            'field' : 'block'
            , 'title' : 'Block'
            , 'sortable' : true
        }, {
            'field' : 'ward'
            , 'title' : 'Ward'
            , 'sortable' : true
        }, {
            'field' : 'case_number'
            , 'title' : 'Case Number'
            , 'sortable' : true
        }, {
            'field' : 'date'
            , 'title' : 'Date'
            , 'sortable' : true
        }, {
            'field' : 'primary_type'
            , 'title' : 'Primary Type'
            , 'sortable' : true
        }, {
            'field' : 'description'
            , 'title' : 'Description'
            , 'sortable' : true
        }, {
            'field' : 'location_description'
            , 'title' : 'Location Description'
            , 'sortable' : true
        }]
    }
    , '/mongo/findAllByParams/foodinspections' : {
        columns : [{
            'field' : 'aka_name'
            , 'title' : 'AKA Name'
            , 'sortable' : true
        }, {
            'field' : 'address'
            , 'title' : 'Address'
            , 'sortable' : true
        }, {
            'field' : 'city'
            , 'title' : 'City'
            , 'sortable' : true
        }, {
            'field' : 'state'
            , 'title' : 'state'
            , 'sortable' : true
        }, {
            'field' : 'zip'
            , 'title' : 'Zip'
            , 'sortable' : true
        }, {
            'field' : 'facility_type'
            , 'title' : 'Facility Type'
            , 'sortable' : true
        }, {
            'field' : 'inspection_date'
            , 'title' : 'Inspection Date'
            , 'sortable' : true
        }, {
            'field' : 'inspection_type'
            , 'title' : 'Inspection Type'
            , 'sortable' : true
        }, {
            'field' : 'inspection_id'
            , 'title' : 'Inspection ID'
            , 'sortable' : true
        } , {
            'field' : 'results'
            , 'title' : 'Results'
            , 'sortable' : true
        }, {
            'field' : 'violations'
            , 'title' : 'Violations'
            , 'formatter' : function(value) {
                return ['<a tabindex="0" class="btn btn-lg btn-danger violations"',
                    'role="button" data-toggle="popover"  data-placement="left"',
                    'data-trigger="focus" title="Violations" data-container="body"',
                    'data-content="', value, '">Violation</a>'].join('');
            }
        }]
    }
    , '/mongo/findAllByParams/potholes' : {
        columns : [{
            'field' : 'creation_date'
            , 'title' : 'Creation Date'
            , 'sortable' : true
        }, {
            'field' : 'completion_date'
            , 'title' : 'Completion Date'
            , 'sortable' : true
        }, {
            'field' : 'street_address'
            , 'title' : 'Street Address'
            , 'sortable' : true
        }, {
            'field' : 'zip'
            , 'title' : 'Zip'
            , 'sortable' : true
        }, {
            'field' : 'ward'
            , 'title' : 'Ward'
            , 'sortable' : true
        }, {
            'field' : 'type_of_service_request'
            , 'title' : 'Type Of Service Request'
            , 'sortable' : true
        }, {
            'field' : 'service_request_number'
            , 'title' : 'Service Request Number'
            , 'sortable' : true
        }, {
            'field' : 'status'
            , 'title' : 'Status'
            , 'sortable' : true
        }, {
            'field' : 'current_activity'
            , 'title' : 'Current Activity'
            , 'sortable' : true
        }, {
            'field' : 'most_recent_action'
            , 'title' : 'Most Recent Action'
            , 'sortable' : true
        }, {
            'field' : 'number_of_potholes_filled_on_block'
            , 'title' : 'No. Of Potholes Filled On Block'
            , 'sortable' : true
        }]
    }
    , '/mongo/findAllByParams/salaries' : {
        columns : [{
            'field' : 'department'
            , 'title' : 'Department'
            , 'sortable' : true
        }, {
            'field' : 'employee_annual_salary'
            , 'title' : 'Employee Annual Salary'
            , 'sortable' : true
        }, {
            'field' : 'name'
            , 'title' : 'Name'
            , 'sortable' : true
        }, {
            'field' : 'job_titles'
            , 'title' : 'Position Title'
            , 'sortable' : true
        }]
    }
};

window.chicago_stats_globals.filter_dataset_types = {
    'STRING' : ['arrest', 'block', 'case_number',
                'date', 'primary_type', 'description',
                'location_description', 'aka_name', 'address',
                'city', 'state', 'facility_type', 'inspection_date',
                'inspection_type', 'results', 'department',
                'employee_annual_salary', 'name', 'job_titles',
                'creation_date', 'completion_date', 'street_address',
                'type_of_service_request', 'service_request_number',
                'status', 'current_activity', 'most_recent_action']
    , 'INT' : ['beat', 'ward', 'zip', 'inspection_id', 'number_of_potholes_filled_on_block']
};

window.chicago_stats_globals.filter_dataset = {
    'crimes' : {
        'filter_keys' : [{
                value : 'arrest'
                , text : 'Arrest'
            }, {
                value : 'beat'
                , text : 'Beat'
            }, {
                value : 'block'
                , text : 'Block'
            }, {
                value : 'ward'
                , text : 'Ward'
            }, {
                value : 'case_number'
                , text : 'Case Number'
            }, {
                value : 'date'
                , text : 'Date'
            }, {
                value : 'primary_type'
                , text : 'Primary Type'
            }, {
                value : 'description'
                , text : 'Description'
            }, {
                value : 'location_description'
                , text : 'Location Description'
            }]
    }
    , 'foodinspections' : {
        'filter_keys' : [{
                value : 'aka_name'
                , text : 'AKA Name'
            }, {
                value : 'address'
                , text : 'Address'
            }, {
                value : 'city'
                , text : 'City'
            }, {
                value : 'state'
                , text : 'State'
            }, {
                value : 'zip'
                , text : 'Zip'
            }, {
                value : 'facility_type'
                , text : 'Facility Type'
            },
            {
                value : 'inspection_date'
                , text : 'Inspection Date'
            }, {
                value : 'inspection_type'
                , text : 'Inspection Type'
            }, {
                value : 'inspection_id'
                , text : 'Inspection ID'
            }, {
                value : 'results'
                , text : 'Results'
            }]
    }
    , 'salaries' : {
        'filter_keys' : [{
                value : 'department'
                , text: 'Department'
            }, {
                value : 'employee_annual_salary'
                , text: 'Employee Annual Salary'
            }, {
                value : 'name'
                , text : 'Name'
            }, {
                value : 'job_titles'
                , text : 'Job Title'
            }]
    }
    , 'potholes' : {
        'filter_keys' : [{
                value : 'creation_date'
                , text : 'Creation Date'
            }, {
                value : 'completion_date'
                , text : 'Completion Date'
            }, {
                value : 'street_address'
                , text : 'Street Address'
            }, {
                value : 'zip'
                , text : 'Zip'
            }, {
                value : 'ward'
                , text : 'Ward'
            }, {
                value : 'type_of_service_request'
                , text : 'Type of Service Request'
            }, {
                value : 'service_request_number'
                , text : 'Service Request Number'
            }, {
                value : 'status'
                , text : 'Status'
            }, {
                value : 'current_activity'
                , text : 'Current Activity'
            }, {
                value : 'most_recent_action'
                , text : 'Most Recent Action'
            }, {
                value : 'number_of_potholes_filled_on_block'
                , text : 'Number of Potholes Filled on Block'
            }]
    }
};

window.chicago_stats_globals.filter_comparison = '$in';

window.chicago_stats_globals.favorite_datasets = {
    columns : [{
        'field' : 'collection'
        , 'title' : 'Collection'
        , 'sortable' : true
    }, {
        'field' : 'query'
        , 'title' : 'My Query Searches'
    }, {
        'field' : 'notes'
        , 'title' : 'Description'
        , 'sortable' : true
    }, {
        'field' : 'created_date'
        , 'title' : 'Date'
        , 'sortable' : true
    }]
};

window.chicago_stats_globals.x_auth_token_key = 'X-Auth-Token';
window.chicago_stats_globals.x_auth_token_cookie_key = 'x_auth_token_key';
window.chicago_stats_globals.CHECK_AUTH_TOKEN_INTERVAL = 300000;

window.chicago_stats_globals.uuid_cookie_key = 'user_uuid';
window.chicago_stats_globals.roles_key = 'roles_key';

window.chicago_stats_globals.EMAIL_SENT_TO_ADDRESS = 'An email has been sent to the email address';
window.chicago_stats_globals.INCORRECT_SECURITY_QUESTION = 'Security question is incorrect';
window.chicago_stats_globals.INCORRECT_PASSWORD = 'Incorrect password';
window.chicago_stats_globals.PROFILE_UPDATE = 'Updated profile';



window.chicago_stats_globals.GENERAL_MODAL_TITLE = 'Chicago Stats Tracker';
window.chicago_stats_globals.UNABLE_TO_FIND_EMAIL_LISTED = 'The email entered couldnt be found';
window.chicago_stats_globals.UNABLE_TO_CREATE_TOKEN_MESSAGE = 'Unable to login';
window.chicago_stats_globals.UNABLE_TO_GET_USER_SECURITY_CONTEXT_MESSAGE = 'Unable to get user security context';

window.chicago_stats_globals.SAVED_DATASET_EMPTY = 'Saved Dataset is empty.';
window.chicago_stats_globals.DATASET_EMPTY = 'Dataset is empty.';
window.chicago_stats_globals.PLEASE_WAIT = 'Please Wait.';

window.chicago_stats_globals.NO_FAVORITE_DATASETS = 'You have no favorite datasets. Go to <a href="#" class="scroll-link" data-id="filterData">Filter Data</a> to create a dataset.';

window.chicago_stats_globals.VIEW_DATASET = 'View Dataset';

window.chicago_stats_globals.FAVORITE_DATASET_NOTE = ['Favorite data set created for collection: ', null];

window.chicago_stats_globals.SAVED_DATASETS_SCAFFOLD = '<table id="saved_searches_table"></table>';

/************************
 * Chicago Stats App    *
 ************************/
window.chicago_stats_app = {};

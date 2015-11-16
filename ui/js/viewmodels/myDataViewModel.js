// Main viewmodel class
define(['knockout','jquery','knockout_mapping'], function(ko,$ , mappping) {
    return function _mydataViewModel() {

        var self = this;

        var _mydata = [];
        var _availableDatasets = [];
        var mydataset = document.getElementById("myDataSet");
        var savedSearchesTable = '#saved_searches_table';
        var _mydataViewModel = {
            'availableDatasets' : []
        };

        self.cleanAndApplyBindings = function(viewModel, data) {
            ko.cleanNode(mydataset);
            // Destroy all children nodes and recreate the table for insertion.
            $('#saved_searches').html(null).html(window.chicago_stats_globals.SAVED_DATASETS_SCAFFOLD);
            ko.applyBindings(viewModel, mydataset);
            createTable.call(self, window.chicago_stats_globals.favorite_datasets.columns,
                data, savedSearchesTable, function() {
                    initializeEvents.call(self);
                }, window.chicago_stats_globals.SAVED_DATASET_EMPTY);
        };

        self.refreshAvailableDatasets = function() {
            _mydata.splice(0);
            _availableDatasets.splice(0);
            _mydataViewModel['availableDatasets'].splice(0);
            ko.cleanNode(mydataset);
            // Destroy all children nodes and recreate the table for insertion.
            $('#saved_searches').html(null).html(window.chicago_stats_globals.SAVED_DATASETS_SCAFFOLD);
            ko.applyBindings(_mydataViewModel, mydataset);
            getAvailableDatasets.call(self);
        };

        function init() {
            getAvailableDatasets.call(self);
            initializeTop50.call(self);
        }

        function initializeEvents() {
            $('body').off('click', '.table-scroll-link');
            $('body').on('click', '.table-scroll-link', function(e) {
                e.preventDefault();
                $('#overviewNavLink').removeClass('disabled');
                window.chicago_stats_app['overviewViewModel'].createOverviewTable(
                    $(e.target).data('query').split('|')[0],
                    [$(e.target).data('query').split('|')[1]]);
                return false;
            })
        }

        function getAvailableDatasets() {
            ajaxMultiGet.call(self,
                [window.chicago_stats_globals.middleware_resources['mongo/getCollectionNames'],
                    [window.chicago_stats_globals.middleware_resources
                        ['favoriteDatasets/get_favoriteDatasetsList_by_Uuid'],
                            ['uuid', $.cookie(window.chicago_stats_globals.uuid_cookie_key)].join('=')].join('?')],
                function(data) {
                    var filteredData = filterData(data[0][0], 'system.indexes');
                    filteredData.forEach(function(datum) {
                        _availableDatasets.push(
                            window.chicago_stats_globals.collection_info[datum])
                    });
                    _mydataViewModel['availableDatasets'] = mappping.fromJS(_availableDatasets);

                    var favDataSets = data[1][0];
                    if (favDataSets != 0) {
                        favDataSets.forEach(function(datum) {
                            var obj = {
                                created_date : moment(datum['created_date']).format('MM/DD/YYYY HH:MM:SS')
                                , notes : datum['notes']
                                , query : ['<a href="#" class="table-scroll-link" data-id="overView" data-query="',
                                    datum['query'].replace(/"/gi, "'"), '">',
                                    window.chicago_stats_globals.VIEW_DATASET, '</a>'].join('')
                                , collection : datum['query'].split('|')[0]
                            };
                            _mydata.push(obj);
                        });
                    } else {
                        showAlert('#saved_searches', window.chicago_stats_globals.NO_FAVORITE_DATASETS);
                        $('#overviewNavLink').addClass('disabled');
                    }

                    self.cleanAndApplyBindings(_mydataViewModel, _mydata);
                }, function(data) {
                    console.log(data);
                    showModal(window.chicago_stats_globals.GENERAL_MODAL_TITLE,
                        [data[0][2]['status'], ': ' , data[0][2]['responseText']].join(''));
                });
        }

        function initializeTop50() {
            $('body').on('click', '.panel-title a', function (e) {
                e.preventDefault();
                var resource = $(e.target).data('resource');
                ajaxGetNoQueryParams(resource, function(data, textStatus, xhr) {
                    console.log(data);
                    console.log(textStatus);
                    console.log(xhr);
                    showModalWithTable(
                        window.chicago_stats_globals.GENERAL_MODAL_TITLE,
                        window.chicago_stats_globals.top_50_table_props[resource]['columns'],
                        data);
                }, function(data, textStatus, xhr) {
                    console.log(data);
                    console.log(textStatus);
                    console.log(xhr);
                    showModal(window.chicago_stats_globals.GENERAL_MODAL_TITLE,
                        [xhr['status'], ': ' , xhr['responseText']].join(''));
                });
                return false;
            });
        }

        init.call(self);
    }
});
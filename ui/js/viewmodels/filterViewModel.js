
define(['knockout','knockout_mapping'], function(ko,mapping) {

    return function FilterViewModel() {

        var datasets = [];
        var filterDataElem = document.getElementById("filterData");

        var self = this;

        self.filterItems = ko.observableArray();
        self.datasetDropdown = ko.observableArray(datasets);
        self.dataset = ko.observable();

        self.initializeSelectedDataset = function(viewModel, e) {
            e.preventDefault();
            self.filterItems.removeAll();
            addItemLine.call(self, $(e.target).val());
            return false;
        };

        self.filterItemLine = function(viewModel, e) {
            addItemLine(self.dataset());
        };

        self.removeFilterLine = function() {
            self.filterItems.remove(this);
            if (self.filterItems().length == 0) {
                self.dataset(null);
            }
        };

        self.cleanAndApplyBindings = function() {
            ko.cleanNode(filterDataElem);
            ko.applyBindings(self, filterDataElem);
        };

        self.createDataSet = function(viewmodel, e) {
            e.preventDefault();
            showModal(window.chicago_stats_globals.GENERAL_MODAL_TITLE, window.chicago_stats_globals.PLEASE_WAIT, true);
            var query = buildQuery.call(self, self.filterItems());
            ajaxGet(
                query,
                window.chicago_stats_globals.middleware_resources[
                    ['mongo', 'findAllByParams', self.dataset()].join('/')],
                    function (data, textStatus, xhr) {
                        console.log(textStatus);
                        console.log(xhr);
                        if (data.length > 0) {
                            var notes = JSON.parse(JSON.stringify(window.chicago_stats_globals.FAVORITE_DATASET_NOTE));
                            notes[1] = self.dataset();
                            var fde = {
                                'id': null
                                , 'user_id': null
                                , 'query': [self.dataset(), query[0]].join('|')
                                , 'notes': notes.join('')
                            };
                            ajaxPost(fde,
                                window.chicago_stats_globals.middleware_resources['favoriteDatasets/create_favoriteDatasets'],
                                function (d, txtStts, x) {
                                    console.log(data);
                                    console.log("--------------");
                                    console.log(d);
                                    console.log(txtStts);
                                    console.log(x);
                                    window.chicago_stats_app['myDataViewModel']['refreshAvailableDatasets']();
                                    window.chicago_stats_app['overviewViewModel']['drawOverviewTable'](
                                        self.dataset(), data);
                                    $('#myModal').modal('hide');
                                }, function (d, txtStts, x) {
                                    showModal(window.chicago_stats_globals.GENERAL_MODAL_TITLE,
                                        [x['status'], ': ', x['responseText']].join(''));
                                });
                        } else {
                            showModal(window.chicago_stats_globals.GENERAL_MODAL_TITLE,
                                window.chicago_stats_globals.DATASET_EMPTY);
                        }
                    },
                    function (data, textStatus, xhr) {
                        showModal(window.chicago_stats_globals.GENERAL_MODAL_TITLE,
                            [xhr['status'], ': ' , xhr['responseText']].join(''));
                    });
            return false;
        };

        init.call(self);

        function addItemLine(dataset) {
            if (dataset === '') { return; }
            self.filterItems.push(filterInputs(window.chicago_stats_globals.filter_dataset[dataset]['filter_keys']));
        }

        function filterInputs(keys) {
            return {
                filterDropdown : ko.observableArray(keys)
                , filterKey : ko.observable()
                , filterValue : ko.observable()
            };
        }

        function init() {
            ajaxMultiGet.call(self,
                [window.chicago_stats_globals.middleware_resources['mongo/getCollectionNames']],
                function(data) {
                    var filteredData = filterData(data[0], 'system.indexes');
                    datasets.splice(0);
                    filteredData.forEach(function(datum) {
                        datasets.push(datum);
                    });

                    self.cleanAndApplyBindings();
                }, function(data) {
                    console.log(data);
                    showModal(window.chicago_stats_globals.GENERAL_MODAL_TITLE,
                        [data[0][2]['status'], ': ' , data[0][2]['responseText']].join(''));
                });
        }
    }
});
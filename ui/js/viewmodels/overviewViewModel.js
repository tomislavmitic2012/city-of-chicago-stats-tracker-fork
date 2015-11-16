// Main viewmodels class
define(['knockout', 'knockout_mapping', 'jquery', 'bootstraptable_export'], function(ko, mapping, $, tableExport) {

    return function overViewViewModel() {
        var self = this;
        self.createOverviewTable = function(collection, query) {
            showModal(window.chicago_stats_globals.GENERAL_MODAL_TITLE, window.chicago_stats_globals.PLEASE_WAIT, true);
            ajaxGet(query, window.chicago_stats_globals.middleware_resources[
                        ['mongo', 'findAllByParams', collection].join('/')],
                function (data, textStatus, xhr) {
                    console.log(data);
                    console.log(textStatus);
                    console.log(xhr);
                    tableBootstrap.call(self, collection, data);
                },
                function (data, textStatus, xhr) {
                    showModal(window.chicago_stats_globals.GENERAL_MODAL_TITLE,
                        [xhr['status'], ': ' , xhr['responseText']].join(''));
                });
        }

        self.drawOverviewTable = function(collection, data) {
            tableBootstrap.call(self, collection, data)
        };

        function tableBootstrap(collection, data) {
            createTable.call(self, window.chicago_stats_globals.find_all_by_params_props[
                    ['/mongo', 'findAllByParams', collection].join('/')]['columns'],
                data, '#overView-table', function () {
                    $('#overview_alert').alert('close');
                    scrollToID('#overView', 750);
                    $('#overView #overView-table').on('click', '.violations', function (e) {
                        e.preventDefault();
                        $(e.target).popover('show');
                        return false;
                    });
                    $('#myModal').modal('hide');
                });
        }
    };
});
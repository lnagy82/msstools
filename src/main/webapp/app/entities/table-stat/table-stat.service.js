(function() {
    'use strict';
    angular
        .module('msstoolsApp')
        .factory('TableStat', TableStat);

    TableStat.$inject = ['$resource', 'DateUtils'];

    function TableStat ($resource, DateUtils) {
        var resourceUrl =  'api/table-stats/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.lastVacuum = DateUtils.convertDateTimeFromServer(data.lastVacuum);
                        data.lastAutovacuum = DateUtils.convertDateTimeFromServer(data.lastAutovacuum);
                        data.lastAnalyze = DateUtils.convertDateTimeFromServer(data.lastAnalyze);
                        data.lastAutoanalyze = DateUtils.convertDateTimeFromServer(data.lastAutoanalyze);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

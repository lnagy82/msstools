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
                        data.updateTime = DateUtils.convertLocalDateFromServer(data.updateTime);
                        data.lastVacuum = DateUtils.convertLocalDateFromServer(data.lastVacuum);
                        data.lastAutovacuum = DateUtils.convertLocalDateFromServer(data.lastAutovacuum);
                        data.lastAnalyze = DateUtils.convertLocalDateFromServer(data.lastAnalyze);
                        data.lastAutoanalyze = DateUtils.convertLocalDateFromServer(data.lastAutoanalyze);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.updateTime = DateUtils.convertLocalDateToServer(copy.updateTime);
                    copy.lastVacuum = DateUtils.convertLocalDateToServer(copy.lastVacuum);
                    copy.lastAutovacuum = DateUtils.convertLocalDateToServer(copy.lastAutovacuum);
                    copy.lastAnalyze = DateUtils.convertLocalDateToServer(copy.lastAnalyze);
                    copy.lastAutoanalyze = DateUtils.convertLocalDateToServer(copy.lastAutoanalyze);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.updateTime = DateUtils.convertLocalDateToServer(copy.updateTime);
                    copy.lastVacuum = DateUtils.convertLocalDateToServer(copy.lastVacuum);
                    copy.lastAutovacuum = DateUtils.convertLocalDateToServer(copy.lastAutovacuum);
                    copy.lastAnalyze = DateUtils.convertLocalDateToServer(copy.lastAnalyze);
                    copy.lastAutoanalyze = DateUtils.convertLocalDateToServer(copy.lastAutoanalyze);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('msstoolsApp')
        .factory('System', System);

    System.$inject = ['$resource'];

    function System ($resource) {
        var resourceUrl =  'api/systems/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

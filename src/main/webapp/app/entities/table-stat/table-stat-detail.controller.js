(function() {
    'use strict';

    angular
        .module('msstoolsApp')
        .controller('TableStatDetailController', TableStatDetailController);

    TableStatDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TableStat', 'System'];

    function TableStatDetailController($scope, $rootScope, $stateParams, previousState, entity, TableStat, System) {
        var vm = this;

        vm.tableStat = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('msstoolsApp:tableStatUpdate', function(event, result) {
            vm.tableStat = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

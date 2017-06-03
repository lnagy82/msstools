(function() {
    'use strict';

    angular
        .module('msstoolsApp')
        .controller('TableStatDeleteController',TableStatDeleteController);

    TableStatDeleteController.$inject = ['$uibModalInstance', 'entity', 'TableStat'];

    function TableStatDeleteController($uibModalInstance, entity, TableStat) {
        var vm = this;

        vm.tableStat = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TableStat.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

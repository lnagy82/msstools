(function() {
    'use strict';

    angular
        .module('msstoolsApp')
        .controller('SystemDeleteController',SystemDeleteController);

    SystemDeleteController.$inject = ['$uibModalInstance', 'entity', 'System'];

    function SystemDeleteController($uibModalInstance, entity, System) {
        var vm = this;

        vm.system = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            System.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

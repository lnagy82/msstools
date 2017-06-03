(function() {
    'use strict';

    angular
        .module('msstoolsApp')
        .controller('SystemDialogController', SystemDialogController);

    SystemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'System'];

    function SystemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, System) {
        var vm = this;

        vm.system = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.system.id !== null) {
                System.update(vm.system, onSaveSuccess, onSaveError);
            } else {
                System.save(vm.system, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('msstoolsApp:systemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

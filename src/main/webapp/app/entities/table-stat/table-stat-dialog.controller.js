(function() {
    'use strict';

    angular
        .module('msstoolsApp')
        .controller('TableStatDialogController', TableStatDialogController);

    TableStatDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TableStat', 'System'];

    function TableStatDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TableStat, System) {
        var vm = this;

        vm.tableStat = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.systems = System.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tableStat.id !== null) {
                TableStat.update(vm.tableStat, onSaveSuccess, onSaveError);
            } else {
                TableStat.save(vm.tableStat, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('msstoolsApp:tableStatUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.lastVacuum = false;
        vm.datePickerOpenStatus.lastAutovacuum = false;
        vm.datePickerOpenStatus.lastAnalyze = false;
        vm.datePickerOpenStatus.lastAutoanalyze = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

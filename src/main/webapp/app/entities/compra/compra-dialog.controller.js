(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('CompraDialogController', CompraDialogController);

    CompraDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Compra', 'CompraLinea', 'Proveedor'];

    function CompraDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Compra, CompraLinea, Proveedor) {
        var vm = this;

        vm.compra = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.compralineas = CompraLinea.query();
        vm.proveedors = Proveedor.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.compra.id !== null) {
                Compra.update(vm.compra, onSaveSuccess, onSaveError);
            } else {
                Compra.save(vm.compra, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tecniIndustrialApp:compraUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fecha = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

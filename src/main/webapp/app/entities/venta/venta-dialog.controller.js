(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('VentaDialogController', VentaDialogController);

    VentaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Venta', 'VentaLinea', 'Cliente'];

    function VentaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Venta, VentaLinea, Cliente) {
        var vm = this;

        vm.venta = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.ventalineas = VentaLinea.query();
        vm.clientes = Cliente.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.venta.id !== null) {
                Venta.update(vm.venta, onSaveSuccess, onSaveError);
            } else {
                Venta.save(vm.venta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tecniIndustrialApp:ventaUpdate', result);
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

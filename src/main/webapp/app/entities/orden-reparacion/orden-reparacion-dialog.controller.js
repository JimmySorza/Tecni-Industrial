(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('OrdenReparacionDialogController', OrdenReparacionDialogController);

    OrdenReparacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'OrdenReparacion', 'OrdenLinea', 'Tecnico', 'Estado', 'Cliente'];

    function OrdenReparacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, OrdenReparacion, OrdenLinea, Tecnico, Estado, Cliente) {
        var vm = this;

        vm.ordenReparacion = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.ordenlineas = OrdenLinea.query();
        vm.tecnicos = Tecnico.query();
        vm.estados = Estado.query();
        vm.clientes = Cliente.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ordenReparacion.id !== null) {
                OrdenReparacion.update(vm.ordenReparacion, onSaveSuccess, onSaveError);
            } else {
                OrdenReparacion.save(vm.ordenReparacion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tecniIndustrialApp:ordenReparacionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fecha = false;
        vm.datePickerOpenStatus.fechaPrometido = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

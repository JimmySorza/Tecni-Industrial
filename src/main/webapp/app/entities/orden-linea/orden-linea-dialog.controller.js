(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('OrdenLineaDialogController', OrdenLineaDialogController);

    OrdenLineaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'OrdenLinea', 'OrdenReparacion', 'Producto'];

    function OrdenLineaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, OrdenLinea, OrdenReparacion, Producto) {
        var vm = this;

        vm.ordenLinea = entity;
        vm.clear = clear;
        vm.save = save;
        vm.ordenreparacions = OrdenReparacion.query();
        vm.productos = Producto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ordenLinea.id !== null) {
                OrdenLinea.update(vm.ordenLinea, onSaveSuccess, onSaveError);
            } else {
                OrdenLinea.save(vm.ordenLinea, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tecniIndustrialApp:ordenLineaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

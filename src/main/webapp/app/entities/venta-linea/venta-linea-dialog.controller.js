(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('VentaLineaDialogController', VentaLineaDialogController);

    VentaLineaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'VentaLinea', 'Venta', 'Producto'];

    function VentaLineaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, VentaLinea, Venta, Producto) {
        var vm = this;

        vm.ventaLinea = entity;
        vm.clear = clear;
        vm.save = save;
        vm.ventas = Venta.query();
        vm.productos = Producto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ventaLinea.id !== null) {
                VentaLinea.update(vm.ventaLinea, onSaveSuccess, onSaveError);
            } else {
                VentaLinea.save(vm.ventaLinea, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tecniIndustrialApp:ventaLineaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

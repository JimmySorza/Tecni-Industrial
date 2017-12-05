(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('CompraLineaDialogsController', CompraLineaDialogsController);

    CompraLineaDialogsController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CompraLinea', 'Compra', 'Producto'];

    function CompraLineaDialogsController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CompraLinea, Compra, Producto) {
        var vm = this;

        vm.compraLinea = entity;
        vm.clear = clear;
        vm.save = save;
        vm.compras = Compra.query();
        vm.productos = Producto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.compraLinea.id !== null) {
                CompraLinea.update(vm.compraLinea, onSaveSuccess, onSaveError);
            } else {
                CompraLinea.save(vm.compraLinea, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tecniIndustrialApp:compraLineaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

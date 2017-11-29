(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('CompraLineaDeleteController',CompraLineaDeleteController);

    CompraLineaDeleteController.$inject = ['$uibModalInstance', 'entity', 'CompraLinea'];

    function CompraLineaDeleteController($uibModalInstance, entity, CompraLinea) {
        var vm = this;

        vm.compraLinea = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CompraLinea.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

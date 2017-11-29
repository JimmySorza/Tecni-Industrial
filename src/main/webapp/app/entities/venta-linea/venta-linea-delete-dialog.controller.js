(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('VentaLineaDeleteController',VentaLineaDeleteController);

    VentaLineaDeleteController.$inject = ['$uibModalInstance', 'entity', 'VentaLinea'];

    function VentaLineaDeleteController($uibModalInstance, entity, VentaLinea) {
        var vm = this;

        vm.ventaLinea = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            VentaLinea.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

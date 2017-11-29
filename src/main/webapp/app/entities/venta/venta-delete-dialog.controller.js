(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('VentaDeleteController',VentaDeleteController);

    VentaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Venta'];

    function VentaDeleteController($uibModalInstance, entity, Venta) {
        var vm = this;

        vm.venta = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Venta.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

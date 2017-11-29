(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('CompraDeleteController',CompraDeleteController);

    CompraDeleteController.$inject = ['$uibModalInstance', 'entity', 'Compra'];

    function CompraDeleteController($uibModalInstance, entity, Compra) {
        var vm = this;

        vm.compra = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Compra.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

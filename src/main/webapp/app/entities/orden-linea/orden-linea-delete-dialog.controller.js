(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('OrdenLineaDeleteController',OrdenLineaDeleteController);

    OrdenLineaDeleteController.$inject = ['$uibModalInstance', 'entity', 'OrdenLinea'];

    function OrdenLineaDeleteController($uibModalInstance, entity, OrdenLinea) {
        var vm = this;
        console.log("dsdsdsdsdsds");
        console.log(entity);
        vm.ordenLinea = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OrdenLinea.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }

    }
})();

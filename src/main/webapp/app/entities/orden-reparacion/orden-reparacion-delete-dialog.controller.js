(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('OrdenReparacionDeleteController',OrdenReparacionDeleteController);

    OrdenReparacionDeleteController.$inject = ['$uibModalInstance', 'entity', 'OrdenReparacion'];

    function OrdenReparacionDeleteController($uibModalInstance, entity, OrdenReparacion) {
        var vm = this;

        vm.ordenReparacion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OrdenReparacion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

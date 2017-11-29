(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('TecnicoDeleteController',TecnicoDeleteController);

    TecnicoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tecnico'];

    function TecnicoDeleteController($uibModalInstance, entity, Tecnico) {
        var vm = this;

        vm.tecnico = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tecnico.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

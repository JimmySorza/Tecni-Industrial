(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('TecnicoController', TecnicoController);

    TecnicoController.$inject = ['Tecnico'];

    function TecnicoController(Tecnico) {

        var vm = this;

        vm.tecnicos = [];

        loadAll();

        function loadAll() {
            Tecnico.query(function(result) {
                vm.tecnicos = result;
                vm.searchQuery = null;
            });
        }
    }
})();

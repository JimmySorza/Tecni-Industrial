(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('OrdenLineaController', OrdenLineaController);

    OrdenLineaController.$inject = ['OrdenLinea'];

    function OrdenLineaController(OrdenLinea) {

        var vm = this;

        vm.ordenLineas = [];

        loadAll();

        function loadAll() {
            OrdenLinea.query(function(result) {
                vm.ordenLineas = result;
                vm.searchQuery = null;
            });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('VentaLineaController', VentaLineaController);

    VentaLineaController.$inject = ['VentaLinea'];

    function VentaLineaController(VentaLinea) {

        var vm = this;

        vm.ventaLineas = [];

        loadAll();

        function loadAll() {
            VentaLinea.query(function(result) {
                vm.ventaLineas = result;
                vm.searchQuery = null;
            });
        }
    }
})();

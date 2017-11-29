(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('CompraLineaController', CompraLineaController);

    CompraLineaController.$inject = ['CompraLinea'];

    function CompraLineaController(CompraLinea) {

        var vm = this;

        vm.compraLineas = [];

        loadAll();

        function loadAll() {
            CompraLinea.query(function(result) {
                vm.compraLineas = result;
                vm.searchQuery = null;
            });
        }
    }
})();

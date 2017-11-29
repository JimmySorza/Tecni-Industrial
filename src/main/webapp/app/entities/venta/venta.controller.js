(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('VentaController', VentaController);

    VentaController.$inject = ['Venta'];

    function VentaController(Venta) {

        var vm = this;

        vm.ventas = [];

        loadAll();

        function loadAll() {
            Venta.query(function(result) {
                vm.ventas = result;
                vm.searchQuery = null;
            });
        }
    }
})();

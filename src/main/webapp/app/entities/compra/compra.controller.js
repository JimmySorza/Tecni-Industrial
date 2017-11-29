(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('CompraController', CompraController);

    CompraController.$inject = ['Compra'];

    function CompraController(Compra) {

        var vm = this;

        vm.compras = [];

        loadAll();

        function loadAll() {
            Compra.query(function(result) {
                vm.compras = result;
                vm.searchQuery = null;
            });
        }
    }
})();

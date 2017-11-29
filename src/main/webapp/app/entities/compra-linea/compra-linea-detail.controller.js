(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('CompraLineaDetailController', CompraLineaDetailController);

    CompraLineaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CompraLinea', 'Compra', 'Producto'];

    function CompraLineaDetailController($scope, $rootScope, $stateParams, previousState, entity, CompraLinea, Compra, Producto) {
        var vm = this;

        vm.compraLinea = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tecniIndustrialApp:compraLineaUpdate', function(event, result) {
            vm.compraLinea = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

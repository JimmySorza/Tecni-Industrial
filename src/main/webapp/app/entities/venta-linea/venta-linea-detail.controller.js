(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('VentaLineaDetailController', VentaLineaDetailController);

    VentaLineaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'VentaLinea', 'Venta', 'Producto'];

    function VentaLineaDetailController($scope, $rootScope, $stateParams, previousState, entity, VentaLinea, Venta, Producto) {
        var vm = this;

        vm.ventaLinea = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tecniIndustrialApp:ventaLineaUpdate', function(event, result) {
            vm.ventaLinea = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

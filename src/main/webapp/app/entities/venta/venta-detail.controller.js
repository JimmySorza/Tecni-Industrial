(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('VentaDetailController', VentaDetailController);

    VentaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Venta', 'VentaLinea', 'Cliente'];

    function VentaDetailController($scope, $rootScope, $stateParams, previousState, entity, Venta, VentaLinea, Cliente) {
        var vm = this;

        vm.venta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tecniIndustrialApp:ventaUpdate', function(event, result) {
            vm.venta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

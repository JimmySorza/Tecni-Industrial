(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('OrdenLineaDetailController', OrdenLineaDetailController);

    OrdenLineaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OrdenLinea', 'OrdenReparacion', 'Producto'];

    function OrdenLineaDetailController($scope, $rootScope, $stateParams, previousState, entity, OrdenLinea, OrdenReparacion, Producto) {
        var vm = this;

        vm.ordenLinea = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tecniIndustrialApp:ordenLineaUpdate', function(event, result) {
            vm.ordenLinea = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

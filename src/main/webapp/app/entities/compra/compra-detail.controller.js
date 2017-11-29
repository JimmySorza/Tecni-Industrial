(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('CompraDetailController', CompraDetailController);

    CompraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Compra', 'CompraLinea', 'Proveedor'];

    function CompraDetailController($scope, $rootScope, $stateParams, previousState, entity, Compra, CompraLinea, Proveedor) {
        var vm = this;

        vm.compra = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tecniIndustrialApp:compraUpdate', function(event, result) {
            vm.compra = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

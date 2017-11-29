(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('ProductoDetailController', ProductoDetailController);

    ProductoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Producto', 'Categoria'];

    function ProductoDetailController($scope, $rootScope, $stateParams, previousState, entity, Producto, Categoria) {
        var vm = this;

        vm.producto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tecniIndustrialApp:productoUpdate', function(event, result) {
            vm.producto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

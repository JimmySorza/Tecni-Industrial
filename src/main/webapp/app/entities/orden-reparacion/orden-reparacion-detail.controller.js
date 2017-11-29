(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('OrdenReparacionDetailController', OrdenReparacionDetailController);

    OrdenReparacionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OrdenReparacion', 'OrdenLinea', 'Tecnico', 'Estado', 'Cliente'];

    function OrdenReparacionDetailController($scope, $rootScope, $stateParams, previousState, entity, OrdenReparacion, OrdenLinea, Tecnico, Estado, Cliente) {
        var vm = this;

        vm.ordenReparacion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tecniIndustrialApp:ordenReparacionUpdate', function(event, result) {
            vm.ordenReparacion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

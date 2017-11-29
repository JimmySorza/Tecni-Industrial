(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('TecnicoDetailController', TecnicoDetailController);

    TecnicoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tecnico'];

    function TecnicoDetailController($scope, $rootScope, $stateParams, previousState, entity, Tecnico) {
        var vm = this;

        vm.tecnico = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tecniIndustrialApp:tecnicoUpdate', function(event, result) {
            vm.tecnico = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('OrdenReparacionDetailController', OrdenReparacionDetailController);

    OrdenReparacionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OrdenReparacion', 'OrdenLinea', 'Tecnico', 'Estado', 'Cliente', '$uibModal'];

    function OrdenReparacionDetailController($scope, $rootScope, $stateParams, previousState, entity, OrdenReparacion, OrdenLinea, Tecnico, Estado, Cliente, $uibModal) {
        var vm = this;

        vm.ordenReparacion = entity;
        vm.previousState = previousState.name;

        vm.ordenLineas=[];

        loadAll();

        function loadAll() {
            OrdenLinea.queryByOrdenReparacion({id: vm.ordenReparacion.id}, function (result) {
                vm.ordenLineas=result;
                vm.searchQuery = null;
            });
        }
        vm.crearLinea=function(){
            $uibModal.open({
                templateUrl: 'app/entities/orden-reparacion/orden-linea-dialog.html',
                controller: 'OrdenLineaDialogsController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            cantidad: null,
                            id: null,
                            producto : null,
                            ordenReparacion: vm.ordenReparacion
                        };
                    }
                }
            }).result.then(function() {
                loadAll();
            }, function() {
                loadAll();
            });
        }

        vm.totalGeneral=function(){
            var total = 0;
            angular.forEach(vm.ordenLineas,function(value,key){
                total = total + (value.producto.precioVenta*value.cantidad);
            });
            return total;
        }

        vm.mostrarDialog=function(elim){
            $uibModal.open({
                templateUrl: 'app/entities/orden-linea/orden-linea-delete-dialog.html',
                controller: 'OrdenLineaDeleteController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    entity: function(){
                        return elim
                    }
                }
            }).result.then(function() {
                loadAll();
            }, function() {
                loadAll();
            });
        }
        var unsubscribe = $rootScope.$on('tecniIndustrialApp:ordenReparacionUpdate', function(event, result) {
            vm.ordenReparacion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

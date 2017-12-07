(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('VentaDetailController', VentaDetailController);

    VentaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Venta', 'VentaLinea', 'Cliente', '$uibModal'];

    function VentaDetailController($scope, $rootScope, $stateParams, previousState, entity, Venta, VentaLinea, Cliente, $uibModal) {
        var vm = this;

        vm.venta = entity;
        vm.previousState = previousState.name;

        vm.ventaLineas=[];

        loadAll();

        function loadAll() {
            VentaLinea.queryByVenta({id: vm.venta.id}, function (result) {
                vm.ventaLineas = result;
                vm.searchQuery = null;
            });
        }

        vm.crearLinea=function(){
            $uibModal.open({
                templateUrl: 'app/entities/venta/venta-linea-dialog.html',
                controller: 'VentaLineaDialogsController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            cantidad: null,
                            id: null,
                            producto : null,
                            venta: vm.venta
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
            angular.forEach(vm.ventaLineas,function(value,key){
                total = total + (value.producto.precioVenta*value.cantidad);
            });
            return total;
        }

        vm.mostrarDialog=function(elim){
            $uibModal.open({
                templateUrl: 'app/entities/venta-linea/venta-linea-delete-dialog.html',
                controller: 'VentaLineaDeleteController',
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
        var unsubscribe = $rootScope.$on('tecniIndustrialApp:ventaUpdate', function(event, result) {
            vm.venta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

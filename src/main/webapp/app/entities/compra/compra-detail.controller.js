(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('CompraDetailController', CompraDetailController);

    CompraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Compra', 'CompraLinea', 'Proveedor','$uibModal'];

    function CompraDetailController($scope, $rootScope, $stateParams, previousState, entity, Compra, CompraLinea, Proveedor, $uibModal) {
        var vm = this;

        vm.compra = entity;
        vm.previousState = previousState.name;

        vm.compraLineas= [];

        loadAll();

        function loadAll() {
            CompraLinea.queryByCompra({id: vm.compra.id}, function (result) {
                vm.compraLineas= result;
                vm.searchQuery = null;
            });
        }

        vm.crearLinea=function(){
            $uibModal.open({
                templateUrl: 'app/entities/compra/compra-linea-dialog.html',
                controller: 'CompraLineaDialogsController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            cantidad: null,
                            id: null,
                            producto : null,
                            compra: vm.compra
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
            angular.forEach(vm.compraLineas,function(value,key){
                 total = total + (value.producto.precioVenta*value.cantidad);
            });
            return total;
        }

        vm.mostrarDialog=function(elim){
            $uibModal.open({
                    templateUrl: 'app/entities/compra-linea/compra-linea-delete-dialog.html',
                    controller: 'CompraLineaDeleteController',
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
        var unsubscribe = $rootScope.$on('tecniIndustrialApp:compraUpdate', function(event, result) {
            vm.compra = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('producto', {
            parent: 'entity',
            url: '/producto',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Productos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/producto/productos.html',
                    controller: 'ProductoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('producto-detail', {
            parent: 'producto',
            url: '/producto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Producto'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/producto/producto-detail.html',
                    controller: 'ProductoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Producto', function($stateParams, Producto) {
                    return Producto.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'producto',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('producto-detail.edit', {
            parent: 'producto-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/producto/producto-dialog.html',
                    controller: 'ProductoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Producto', function(Producto) {
                            return Producto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('producto.new', {
            parent: 'producto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/producto/producto-dialog.html',
                    controller: 'ProductoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                codigo: null,
                                nombre: null,
                                precioCosto: null,
                                precioVenta: null,
                                existencia: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('producto', null, { reload: 'producto' });
                }, function() {
                    $state.go('producto');
                });
            }]
        })
        .state('producto.edit', {
            parent: 'producto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/producto/producto-dialog.html',
                    controller: 'ProductoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Producto', function(Producto) {
                            return Producto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('producto', null, { reload: 'producto' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('producto.delete', {
            parent: 'producto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/producto/producto-delete-dialog.html',
                    controller: 'ProductoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Producto', function(Producto) {
                            return Producto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('producto', null, { reload: 'producto' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

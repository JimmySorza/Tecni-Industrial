(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('venta-linea', {
            parent: 'entity',
            url: '/venta-linea',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'VentaLineas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/venta-linea/venta-lineas.html',
                    controller: 'VentaLineaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('venta-linea-detail', {
            parent: 'venta-linea',
            url: '/venta-linea/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'VentaLinea'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/venta-linea/venta-linea-detail.html',
                    controller: 'VentaLineaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'VentaLinea', function($stateParams, VentaLinea) {
                    return VentaLinea.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'venta-linea',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('venta-linea-detail.edit', {
            parent: 'venta-linea-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/venta-linea/venta-linea-dialog.html',
                    controller: 'VentaLineaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VentaLinea', function(VentaLinea) {
                            return VentaLinea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('venta-linea.new', {
            parent: 'venta-linea',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/venta-linea/venta-linea-dialog.html',
                    controller: 'VentaLineaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cantidad: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('venta-linea', null, { reload: 'venta-linea' });
                }, function() {
                    $state.go('venta-linea');
                });
            }]
        })
        .state('venta-linea.edit', {
            parent: 'venta-linea',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/venta-linea/venta-linea-dialog.html',
                    controller: 'VentaLineaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VentaLinea', function(VentaLinea) {
                            return VentaLinea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('venta-linea', null, { reload: 'venta-linea' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('venta-linea.delete', {
            parent: 'venta-linea',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/venta-linea/venta-linea-delete-dialog.html',
                    controller: 'VentaLineaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['VentaLinea', function(VentaLinea) {
                            return VentaLinea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('venta-linea', null, { reload: 'venta-linea' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

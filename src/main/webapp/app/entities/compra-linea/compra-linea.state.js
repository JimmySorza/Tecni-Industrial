(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('compra-linea', {
            parent: 'entity',
            url: '/compra-linea',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CompraLineas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/compra-linea/compra-lineas.html',
                    controller: 'CompraLineaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('compra-linea-detail', {
            parent: 'compra-linea',
            url: '/compra-linea/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CompraLinea'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/compra-linea/compra-linea-detail.html',
                    controller: 'CompraLineaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CompraLinea', function($stateParams, CompraLinea) {
                    return CompraLinea.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'compra-linea',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('compra-linea-detail.edit', {
            parent: 'compra-linea-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/compra-linea/compra-linea-dialog.html',
                    controller: 'CompraLineaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CompraLinea', function(CompraLinea) {
                            return CompraLinea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('compra-linea.new', {
            parent: 'compra-linea',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/compra-linea/compra-linea-dialog.html',
                    controller: 'CompraLineaDialogController',
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
                    $state.go('compra-linea', null, { reload: 'compra-linea' });
                }, function() {
                    $state.go('compra-linea');
                });
            }]
        })
        .state('compra-linea.edit', {
            parent: 'compra-linea',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/compra-linea/compra-linea-dialog.html',
                    controller: 'CompraLineaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CompraLinea', function(CompraLinea) {
                            return CompraLinea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('compra-linea', null, { reload: 'compra-linea' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('compra-linea.delete', {
            parent: 'compra-linea',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/compra-linea/compra-linea-delete-dialog.html',
                    controller: 'CompraLineaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CompraLinea', function(CompraLinea) {
                            return CompraLinea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('compra-linea', null, { reload: 'compra-linea' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

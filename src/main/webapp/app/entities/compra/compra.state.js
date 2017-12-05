(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('compra', {
            parent: 'entity',
            url: '/compra',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Compras'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/compra/compras.html',
                    controller: 'CompraController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('compra-detail', {
            parent: 'compra',
            url: '/compra/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Compra'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/compra/compra-detail.html',
                    controller: 'CompraDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Compra', function($stateParams, Compra) {
                    return Compra.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'compra',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('compra-detail.edit', {
            parent: 'compra-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/compra/compra-dialog.html',
                    controller: 'CompraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Compra', function(Compra) {
                            return Compra.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('compra.new', {
            parent: 'compra',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/compra/compra-dialog.html',
                    controller: 'CompraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fecha: null,
                                numero: null,
                                total: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('compra', null, { reload: 'compra' });
                }, function() {
                    $state.go('compra');
                });
            }]
        })
            .state('compra-detail.news', {
                parent: 'compra',
                url: '/compra',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
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
                                    producto : null
                                };
                            }
                        }
                    }).result.then(function() {
                        $state.go('^', {}, { reload: false });
                    }, function() {
                        $state.go('^', {}, { reload: false });
                    });
                }]
            })
        .state('compra.edit', {
            parent: 'compra',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/compra/compra-dialog.html',
                    controller: 'CompraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Compra', function(Compra) {
                            return Compra.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('compra', null, { reload: 'compra' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('compra.delete', {
            parent: 'compra',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/compra/compra-delete-dialog.html',
                    controller: 'CompraDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Compra', function(Compra) {
                            return Compra.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('compra', null, { reload: 'compra' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

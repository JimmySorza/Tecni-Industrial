(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('orden-linea', {
            parent: 'entity',
            url: '/orden-linea',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'OrdenLineas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/orden-linea/orden-lineas.html',
                    controller: 'OrdenLineaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('orden-linea-detail', {
            parent: 'orden-linea',
            url: '/orden-linea/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'OrdenLinea'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/orden-linea/orden-linea-detail.html',
                    controller: 'OrdenLineaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'OrdenLinea', function($stateParams, OrdenLinea) {
                    return OrdenLinea.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'orden-linea',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('orden-linea-detail.edit', {
            parent: 'orden-linea-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orden-linea/orden-linea-dialog.html',
                    controller: 'OrdenLineaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrdenLinea', function(OrdenLinea) {
                            return OrdenLinea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('orden-linea.new', {
            parent: 'orden-linea',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orden-linea/orden-linea-dialog.html',
                    controller: 'OrdenLineaDialogController',
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
                    $state.go('orden-linea', null, { reload: 'orden-linea' });
                }, function() {
                    $state.go('orden-linea');
                });
            }]
        })
        .state('orden-linea.edit', {
            parent: 'orden-linea',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orden-linea/orden-linea-dialog.html',
                    controller: 'OrdenLineaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrdenLinea', function(OrdenLinea) {
                            return OrdenLinea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('orden-linea', null, { reload: 'orden-linea' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('orden-linea.delete', {
            parent: 'orden-linea',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orden-linea/orden-linea-delete-dialog.html',
                    controller: 'OrdenLineaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OrdenLinea', function(OrdenLinea) {
                            return OrdenLinea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('orden-linea', null, { reload: 'orden-linea' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

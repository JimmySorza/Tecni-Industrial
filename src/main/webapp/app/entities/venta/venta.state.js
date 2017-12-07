(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('venta', {
            parent: 'entity',
            url: '/venta',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Ventas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/venta/ventas.html',
                    controller: 'VentaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('venta-detail', {
            parent: 'venta',
            url: '/venta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Venta'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/venta/venta-detail.html',
                    controller: 'VentaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Venta', function($stateParams, Venta) {
                    return Venta.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'venta',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('venta-detail.edit', {
            parent: 'venta-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/venta/venta-dialog.html',
                    controller: 'VentaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Venta', function(Venta) {
                            return Venta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('venta.new', {
            parent: 'venta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/venta/venta-dialog.html',
                    controller: 'VentaDialogController',
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
                    $state.go('venta', null, { reload: 'venta' });
                }, function() {
                    $state.go('venta');
                });
            }]
        })
            //state venta-details.news puede sobrar! ya qye en las ordenes no fue necesario
            .state('venta-details.news', {
                parent: 'venta',
                url: '/venta',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/venta/venta-linea-dialog.html',
                        controller: 'VentaLineaDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    cantidad: null,
                                    id: null,
                                    producto: null
                                };
                            }
                        }
                    }).result.then(function() {
                        $state.go('^',{} , { reload: false });
                    }, function() {
                        $state.go('^',{} , { reload: false });
                    });
                }]
            })

        .state('venta.edit', {
            parent: 'venta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/venta/venta-dialog.html',
                    controller: 'VentaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Venta', function(Venta) {
                            return Venta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('venta', null, { reload: 'venta' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('venta.delete', {
            parent: 'venta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/venta/venta-delete-dialog.html',
                    controller: 'VentaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Venta', function(Venta) {
                            return Venta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('venta', null, { reload: 'venta' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

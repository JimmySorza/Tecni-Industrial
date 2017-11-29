(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('estado', {
            parent: 'entity',
            url: '/estado',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Estados'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/estado/estados.html',
                    controller: 'EstadoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('estado-detail', {
            parent: 'estado',
            url: '/estado/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Estado'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/estado/estado-detail.html',
                    controller: 'EstadoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Estado', function($stateParams, Estado) {
                    return Estado.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'estado',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('estado-detail.edit', {
            parent: 'estado-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/estado/estado-dialog.html',
                    controller: 'EstadoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Estado', function(Estado) {
                            return Estado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('estado.new', {
            parent: 'estado',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/estado/estado-dialog.html',
                    controller: 'EstadoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('estado', null, { reload: 'estado' });
                }, function() {
                    $state.go('estado');
                });
            }]
        })
        .state('estado.edit', {
            parent: 'estado',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/estado/estado-dialog.html',
                    controller: 'EstadoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Estado', function(Estado) {
                            return Estado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('estado', null, { reload: 'estado' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('estado.delete', {
            parent: 'estado',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/estado/estado-delete-dialog.html',
                    controller: 'EstadoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Estado', function(Estado) {
                            return Estado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('estado', null, { reload: 'estado' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

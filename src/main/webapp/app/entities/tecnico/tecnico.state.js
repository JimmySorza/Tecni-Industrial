(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tecnico', {
            parent: 'entity',
            url: '/tecnico',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Tecnicos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tecnico/tecnicos.html',
                    controller: 'TecnicoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('tecnico-detail', {
            parent: 'tecnico',
            url: '/tecnico/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Tecnico'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tecnico/tecnico-detail.html',
                    controller: 'TecnicoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Tecnico', function($stateParams, Tecnico) {
                    return Tecnico.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tecnico',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tecnico-detail.edit', {
            parent: 'tecnico-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tecnico/tecnico-dialog.html',
                    controller: 'TecnicoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tecnico', function(Tecnico) {
                            return Tecnico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tecnico.new', {
            parent: 'tecnico',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tecnico/tecnico-dialog.html',
                    controller: 'TecnicoDialogController',
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
                    $state.go('tecnico', null, { reload: 'tecnico' });
                }, function() {
                    $state.go('tecnico');
                });
            }]
        })
        .state('tecnico.edit', {
            parent: 'tecnico',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tecnico/tecnico-dialog.html',
                    controller: 'TecnicoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tecnico', function(Tecnico) {
                            return Tecnico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tecnico', null, { reload: 'tecnico' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tecnico.delete', {
            parent: 'tecnico',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tecnico/tecnico-delete-dialog.html',
                    controller: 'TecnicoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tecnico', function(Tecnico) {
                            return Tecnico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tecnico', null, { reload: 'tecnico' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

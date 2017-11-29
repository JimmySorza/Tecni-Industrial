(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('orden-reparacion', {
            parent: 'entity',
            url: '/orden-reparacion?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'OrdenReparacions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/orden-reparacion/orden-reparacions.html',
                    controller: 'OrdenReparacionController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('orden-reparacion-detail', {
            parent: 'orden-reparacion',
            url: '/orden-reparacion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'OrdenReparacion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/orden-reparacion/orden-reparacion-detail.html',
                    controller: 'OrdenReparacionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'OrdenReparacion', function($stateParams, OrdenReparacion) {
                    return OrdenReparacion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'orden-reparacion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('orden-reparacion-detail.edit', {
            parent: 'orden-reparacion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orden-reparacion/orden-reparacion-dialog.html',
                    controller: 'OrdenReparacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrdenReparacion', function(OrdenReparacion) {
                            return OrdenReparacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('orden-reparacion.new', {
            parent: 'orden-reparacion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orden-reparacion/orden-reparacion-dialog.html',
                    controller: 'OrdenReparacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fecha: null,
                                garantia: null,
                                maquina: null,
                                falla: null,
                                fechaPrometido: null,
                                accesorios: null,
                                observaciones: null,
                                informeTecnico: null,
                                total: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('orden-reparacion', null, { reload: 'orden-reparacion' });
                }, function() {
                    $state.go('orden-reparacion');
                });
            }]
        })
        .state('orden-reparacion.edit', {
            parent: 'orden-reparacion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orden-reparacion/orden-reparacion-dialog.html',
                    controller: 'OrdenReparacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrdenReparacion', function(OrdenReparacion) {
                            return OrdenReparacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('orden-reparacion', null, { reload: 'orden-reparacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('orden-reparacion.delete', {
            parent: 'orden-reparacion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orden-reparacion/orden-reparacion-delete-dialog.html',
                    controller: 'OrdenReparacionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OrdenReparacion', function(OrdenReparacion) {
                            return OrdenReparacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('orden-reparacion', null, { reload: 'orden-reparacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

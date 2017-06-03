(function() {
    'use strict';

    angular
        .module('msstoolsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('system', {
            parent: 'entity',
            url: '/system?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'msstoolsApp.system.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/system/systems.html',
                    controller: 'SystemController',
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
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('system');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('system-detail', {
            parent: 'system',
            url: '/system/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'msstoolsApp.system.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/system/system-detail.html',
                    controller: 'SystemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('system');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'System', function($stateParams, System) {
                    return System.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'system',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('system-detail.edit', {
            parent: 'system-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/system/system-dialog.html',
                    controller: 'SystemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['System', function(System) {
                            return System.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('system.new', {
            parent: 'system',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/system/system-dialog.html',
                    controller: 'SystemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                ip: null,
                                port: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('system', null, { reload: 'system' });
                }, function() {
                    $state.go('system');
                });
            }]
        })
        .state('system.edit', {
            parent: 'system',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/system/system-dialog.html',
                    controller: 'SystemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['System', function(System) {
                            return System.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('system', null, { reload: 'system' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('system.delete', {
            parent: 'system',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/system/system-delete-dialog.html',
                    controller: 'SystemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['System', function(System) {
                            return System.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('system', null, { reload: 'system' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

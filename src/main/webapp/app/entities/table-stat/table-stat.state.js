(function() {
    'use strict';

    angular
        .module('msstoolsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('table-stat', {
            parent: 'entity',
            url: '/table-stat?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'msstoolsApp.tableStat.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/table-stat/table-stats.html',
                    controller: 'TableStatController',
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
                    $translatePartialLoader.addPart('tableStat');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('table-stat-detail', {
            parent: 'table-stat',
            url: '/table-stat/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'msstoolsApp.tableStat.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/table-stat/table-stat-detail.html',
                    controller: 'TableStatDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tableStat');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TableStat', function($stateParams, TableStat) {
                    return TableStat.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'table-stat',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('table-stat-detail.edit', {
            parent: 'table-stat-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/table-stat/table-stat-dialog.html',
                    controller: 'TableStatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TableStat', function(TableStat) {
                            return TableStat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('table-stat.new', {
            parent: 'table-stat',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/table-stat/table-stat-dialog.html',
                    controller: 'TableStatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                schemaname: null,
                                relname: null,
                                seqScan: null,
                                seqTupRead: null,
                                idxScan: null,
                                idxTupFetch: null,
                                nTupIns: null,
                                nTupUpd: null,
                                nTupDel: null,
                                nTupHotUpd: null,
                                nLiveTup: null,
                                nDeadTup: null,
                                vacuumCount: null,
                                autovacuumCount: null,
                                analyzeCount: null,
                                autoanalyzeCount: null,
                                updateTime: null,
                                updateNumber: null,
                                lastVacuum: null,
                                lastAutovacuum: null,
                                lastAnalyze: null,
                                lastAutoanalyze: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('table-stat', null, { reload: 'table-stat' });
                }, function() {
                    $state.go('table-stat');
                });
            }]
        })
        .state('table-stat.edit', {
            parent: 'table-stat',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/table-stat/table-stat-dialog.html',
                    controller: 'TableStatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TableStat', function(TableStat) {
                            return TableStat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('table-stat', null, { reload: 'table-stat' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('table-stat.delete', {
            parent: 'table-stat',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/table-stat/table-stat-delete-dialog.html',
                    controller: 'TableStatDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TableStat', function(TableStat) {
                            return TableStat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('table-stat', null, { reload: 'table-stat' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

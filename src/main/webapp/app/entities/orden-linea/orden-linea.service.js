(function() {
    'use strict';
    angular
        .module('tecniIndustrialApp')
        .factory('OrdenLinea', OrdenLinea);

    OrdenLinea.$inject = ['$resource'];

    function OrdenLinea ($resource) {
        var resourceUrl =  'api/orden-lineas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

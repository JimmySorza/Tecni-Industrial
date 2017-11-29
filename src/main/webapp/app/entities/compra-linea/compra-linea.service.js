(function() {
    'use strict';
    angular
        .module('tecniIndustrialApp')
        .factory('CompraLinea', CompraLinea);

    CompraLinea.$inject = ['$resource'];

    function CompraLinea ($resource) {
        var resourceUrl =  'api/compra-lineas/:id';

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

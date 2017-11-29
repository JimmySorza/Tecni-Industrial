(function() {
    'use strict';
    angular
        .module('tecniIndustrialApp')
        .factory('CompraLinea', CompraLinea);

    CompraLinea.$inject = ['$resource'];

    function CompraLinea ($resource) {
        var resourceUrl =  'api/compra-lineas/:id';

        return $resource(resourceUrl, {}, {
            'queryByCompra':{
                url: 'api/compra-lineas/compras/:id',
                method: 'GET',
                isArray: true
            },
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

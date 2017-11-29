(function() {
    'use strict';
    angular
        .module('tecniIndustrialApp')
        .factory('VentaLinea', VentaLinea);

    VentaLinea.$inject = ['$resource'];

    function VentaLinea ($resource) {
        var resourceUrl =  'api/venta-lineas/:id';

        return $resource(resourceUrl, {}, {
            'queryByVenta':{
                url:'api/venta-lineas/ventas/:id',
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

(function() {
    'use strict';
    angular
        .module('tecniIndustrialApp')
        .factory('Tecnico', Tecnico);

    Tecnico.$inject = ['$resource'];

    function Tecnico ($resource) {
        var resourceUrl =  'api/tecnicos/:id';

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

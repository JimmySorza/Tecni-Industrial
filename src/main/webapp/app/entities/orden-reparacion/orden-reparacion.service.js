(function() {
    'use strict';
    angular
        .module('tecniIndustrialApp')
        .factory('OrdenReparacion', OrdenReparacion);

    OrdenReparacion.$inject = ['$resource', 'DateUtils'];

    function OrdenReparacion ($resource, DateUtils) {
        var resourceUrl =  'api/orden-reparacions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fecha = DateUtils.convertLocalDateFromServer(data.fecha);
                        data.fechaPrometido = DateUtils.convertLocalDateFromServer(data.fechaPrometido);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fecha = DateUtils.convertLocalDateToServer(copy.fecha);
                    copy.fechaPrometido = DateUtils.convertLocalDateToServer(copy.fechaPrometido);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fecha = DateUtils.convertLocalDateToServer(copy.fecha);
                    copy.fechaPrometido = DateUtils.convertLocalDateToServer(copy.fechaPrometido);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();

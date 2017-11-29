(function () {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();

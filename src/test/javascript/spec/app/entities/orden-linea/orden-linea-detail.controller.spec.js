'use strict';

describe('Controller Tests', function() {

    describe('OrdenLinea Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockOrdenLinea, MockOrdenReparacion, MockProducto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockOrdenLinea = jasmine.createSpy('MockOrdenLinea');
            MockOrdenReparacion = jasmine.createSpy('MockOrdenReparacion');
            MockProducto = jasmine.createSpy('MockProducto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'OrdenLinea': MockOrdenLinea,
                'OrdenReparacion': MockOrdenReparacion,
                'Producto': MockProducto
            };
            createController = function() {
                $injector.get('$controller')("OrdenLineaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'tecniIndustrialApp:ordenLineaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

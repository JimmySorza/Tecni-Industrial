'use strict';

describe('Controller Tests', function() {

    describe('OrdenReparacion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockOrdenReparacion, MockOrdenLinea, MockTecnico, MockEstado, MockCliente;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockOrdenReparacion = jasmine.createSpy('MockOrdenReparacion');
            MockOrdenLinea = jasmine.createSpy('MockOrdenLinea');
            MockTecnico = jasmine.createSpy('MockTecnico');
            MockEstado = jasmine.createSpy('MockEstado');
            MockCliente = jasmine.createSpy('MockCliente');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'OrdenReparacion': MockOrdenReparacion,
                'OrdenLinea': MockOrdenLinea,
                'Tecnico': MockTecnico,
                'Estado': MockEstado,
                'Cliente': MockCliente
            };
            createController = function() {
                $injector.get('$controller')("OrdenReparacionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'tecniIndustrialApp:ordenReparacionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

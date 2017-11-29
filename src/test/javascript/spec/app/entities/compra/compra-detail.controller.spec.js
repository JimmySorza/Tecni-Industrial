'use strict';

describe('Controller Tests', function() {

    describe('Compra Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCompra, MockCompraLinea, MockProveedor;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCompra = jasmine.createSpy('MockCompra');
            MockCompraLinea = jasmine.createSpy('MockCompraLinea');
            MockProveedor = jasmine.createSpy('MockProveedor');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Compra': MockCompra,
                'CompraLinea': MockCompraLinea,
                'Proveedor': MockProveedor
            };
            createController = function() {
                $injector.get('$controller')("CompraDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'tecniIndustrialApp:compraUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

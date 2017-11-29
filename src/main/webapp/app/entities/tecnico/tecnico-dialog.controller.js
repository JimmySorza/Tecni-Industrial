(function() {
    'use strict';

    angular
        .module('tecniIndustrialApp')
        .controller('TecnicoDialogController', TecnicoDialogController);

    TecnicoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tecnico'];

    function TecnicoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tecnico) {
        var vm = this;

        vm.tecnico = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tecnico.id !== null) {
                Tecnico.update(vm.tecnico, onSaveSuccess, onSaveError);
            } else {
                Tecnico.save(vm.tecnico, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tecniIndustrialApp:tecnicoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

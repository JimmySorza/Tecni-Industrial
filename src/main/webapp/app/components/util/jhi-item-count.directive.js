(function() {
    'use strict';

    var jhiItemCount = {
        template: '<div class="info" data-translate="global.item-count" ' +
        'translate-value-first="{{(($ctrl.page - 1) * $ctrl.itemsPerPage) == 0 ? 1 : (($ctrl.page - 1) * $ctrl.itemsPerPage + 1)}}" ' +
        'translate-value-second="{{($ctrl.page * $ctrl.itemsPerPage) < $ctrl.queryCount ? ($ctrl.page * $ctrl.itemsPerPage) : $ctrl.queryCount}}" ' +
        'translate-value-total="{{$ctrl.queryCount}}">' +
        'Mostrando {{(($ctrl.page - 1) * $ctrl.itemsPerPage) == 0 ? 1 : (($ctrl.page - 1) * $ctrl.itemsPerPage + 1)}} - ' +
        '{{($ctrl.page * $ctrl.itemsPerPage) < $ctrl.queryCount ? ($ctrl.page * $ctrl.itemsPerPage) : $ctrl.queryCount}} ' +
        'de {{$ctrl.queryCount}} items.' +
        '</div>',
        bindings: {
            page: '<',
            queryCount: '<total',
            itemsPerPage: '<'
        }
    };

    angular
        .module('tecniIndustrialApp')
        .component('jhiItemCount', jhiItemCount);
})();

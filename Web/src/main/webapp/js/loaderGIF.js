'use strict'

angular.module('loaderGif', []).directive('loaderGif', function () {
    return {
        template: '<div ng-show="isLoaderShown" style="position: fixed; z-index: 999999; left: 40%; top: 40%;"><div align="center"><img src="./img/loading.gif" style="width:220px"><br></div></div>' +
        	'<div ng-show="isLoaderShown" style="position: fixed; z-index: 999998; left: 0; top: 0; width: 100%; height: 100%; border-width: 1; border-style: ridge; background: #666666; opacity: .20;filter:Alpha(Opacity=50);"></div>'
    };
});
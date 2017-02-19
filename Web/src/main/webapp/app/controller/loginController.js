'use strict';

App.controller('LoginController', ['$rootScope', '$scope', '$state', 'LoginService', function($rootScope, $scope, $state, LoginService) {
	$scope.user = {};
	
    $scope.login = function(isValid){
    	$scope.showError = false;
    	if (isValid) {
			$rootScope.isLoaderShown = true;
			LoginService.authenticateLogin($scope.user, function(resp){
            	if (resp.status == 200 && resp.data) {
            		$state.go('/simpleObject');
            	}
            	$rootScope.isLoaderShown = false;
            }, function(resp){
            	if(resp.data.errors) {
            		for(var i=0;i<resp.data.errors.length;i++) {
            			console.log(resp.data.errors[i].error);
            		}
            	}
            	$rootScope.isLoaderShown = false;
            });
    	} else {
    		$scope.showError = true;
    	}
    };
    
}]);

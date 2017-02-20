'use strict';

App.controller('LoginController', ['$rootScope', '$scope', '$state', 'AuthenticationService', '$cookies', function($rootScope, $scope, $state, AuthenticationService, $cookies) {
	$scope.user = {};
	
    $scope.login = function(isValid){
    	$scope.showError = false;
    	if (isValid) {
			$rootScope.isLoaderShown = true;
			AuthenticationService.authenticateLogin($scope.user, function(resp){
            	if (resp.status == 200 && resp.data) {
            		$cookies.putObject('loginSession', resp.data, function(){
            			$state.go('basic');	
            		});
            	}
            	$rootScope.isLoaderShown = false;
            }, function(resp){
            	if(resp.data.errors) {
            		for(var i=0;i<resp.data.errors.length;i++) {
            			alertify.error(resp.data.errors[i].error);
            		}
            	}
            	$rootScope.isLoaderShown = false;
            });
    	} else {
    		$scope.showError = true;
    	}
    };
    
}]);

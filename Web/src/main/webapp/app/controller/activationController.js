'use strict';

App.controller('ActivationController', 
		['$rootScope', '$scope', '$state', 'UserService', '$stateParams', 
		 		function($rootScope, $scope, $state, UserService, $stateParams) {
	$scope.user = {};
	
	if($stateParams.email) {
		$scope.user.email = $stateParams.email;
	}
	
	$scope.submitActivation = function(isValid) {
		$scope.showError = false;
		if (isValid) {
			$rootScope.isLoaderShown = true;
			UserService.activate($scope.user, function(resp){
            	if (resp.status == 200) {
            		$state.go('login');
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

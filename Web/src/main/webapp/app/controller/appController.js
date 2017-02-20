'use strict';
 
App.controller('AppController', ['$rootScope', '$scope', '$state', 'AuthenticationService', function($rootScope, $scope, $state, AuthenticationService) {
	$rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
		if(toState.param && toState.param.loginSessionRequired) {
			if (!$rootScope.loginSession) {
				event.preventDefault();
				$state.go('login');
			}	
		}
	});
}]);
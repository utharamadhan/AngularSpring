'use strict';

App.factory('AuthenticationService', ['$http', '$q', function($http, $q){
    var sURL = 'http://localhost:8080/ApplicationService/auth';
    
    return {
      authenticateLogin: function(user, successCallback, failCallback) {
        return $http.post(sURL+"/authenticateLogin", user).then(successCallback, failCallback);
      },
      authenticateLoginSession: function(loginSession, successCallback, failCallback) {
    	return $http.post(sURL+"/authenticateLoginSession", loginSession).then(successCallback, failCallback);
      }
    }
}]);

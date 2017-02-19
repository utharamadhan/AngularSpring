'use strict';

App.factory('LoginService', ['$http', '$q', function($http, $q){
	
    var sURL = 'http://localhost:8080/ApplicationService/auth/authenticateLogin';
    
    return {
      authenticateLogin: function(user, successCallback, failCallback) {
        return $http.post(sURL, user).then(successCallback, failCallback);
      }
    }
    
}]);

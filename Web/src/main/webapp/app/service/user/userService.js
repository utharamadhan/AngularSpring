'use strict';

App.factory('UserService', ['$http', '$q', function($http, $q){
	
    var sURL = 'http://localhost:8080/ApplicationService/user';
    
    return {
      register: function(user, successCallback, failCallback) {
        return $http.post(sURL + "/create", user).then(successCallback, failCallback);
      }
    }
    
}]);

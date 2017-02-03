'use strict';
 
App.factory('SimpleObjectService', ['$http', '$q', function($http, $q){
    var sURL = 'http://localhost:8080/ApplicationService/simpleObject';
    
    return {
         
    fecthAllSimpleObjects: function() {
            return $http.get(sURL + '/listAll')
            .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching SimpleObjects');
                        return $q.reject(errResponse);
                    }
            );
        },
     
    createSimpleObject: function(simpleObject){
            return $http.post(sURL + '/create', simpleObject)
            .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while creating SimpleObjects');
                        return $q.reject(errResponse);
                    }
            );
        },
     
        updateSimpleObject: function(simpleObject, id){
        	console.log(simpleObject);
            return $http.put(sURL + '/update', simpleObject)
            .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while updating SimpleObjects');
                        return $q.reject(errResponse);
                    }
            );
        },
     
        deleteSimpleObject: function(id){
            return $http.delete(sURL+"/delete?objectPKs="+id)
            .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while deleting SimpleObjects');
                        return $q.reject(errResponse);
                    }
            );
        }
        
    };
 
}]);
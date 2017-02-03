'use strict';
 
App.controller('AppController', ['$scope', 'SimpleObjectService', function($scope, SimpleObjectService) {
          var self = this;
          self.simpleObject={pkSimpleObject:null,name:''};
          self.simpleObjects=[];
          
          self.fetchAll = function(){
              SimpleObjectService.fecthAllSimpleObjects()
                  .then(
                       function(d) {
                            self.simpleObjects = d;
                       },
                        function(errResponse){
                            console.error('Error while fetching Currencies');
                        }
                   );
          };
            
          self.create = function(simpleObject){
              SimpleObjectService.createSimpleObject(simpleObject)
                      .then(
                      self.fetchAll, 
                              function(errResponse){
                                   console.error('Error while creating SimpleObject.');
                              } 
                  );
          };
 
         self.update = function(simpleObject, id){
              SimpleObjectService.updateSimpleObject(simpleObject, id)
                      .then(
                              self.fetchAll, 
                              function(errResponse){
                                   console.error('Error while updating SimpleObject.');
                              } 
                  );
          };
 
         self.deleteObj = function(id){
              SimpleObjectService.deleteSimpleObject(id)
                      .then(
                              self.fetchAll, 
                              function(errResponse){
                                   console.error('Error while deleting SimpleObject.');
                              } 
                  );
          };
 
          self.fetchAll();
 
          self.submit = function() {
              if(self.simpleObject.pkSimpleObject===null){
                  self.simpleObject.typeLookup = {'pkLookup' : 1};
                  self.create(self.simpleObject);
              }else{
                  self.update(self.simpleObject, self.simpleObject.pkSimpleObject);
                  console.log('SimpleObject updated with pkSimpleObject ', self.simpleObject.pkSimpleObject);
              }
              self.reset();
          };
               
          self.edit = function(pkSimpleObject){
              for(var i = 0; i < self.simpleObjects.length; i++){
                  if(self.simpleObjects[i].pkSimpleObject === pkSimpleObject) {
                     self.simpleObject = angular.copy(self.simpleObjects[i]);
                     break;
                  }
              }
          }
               
          self.remove = function(id){
              if(self.simpleObject.id === id) {
                  self.reset();
              }
              self.deleteObj(id);
          }
 
          self.reset = function(){
              self.simpleObject={pkSimpleObject:null,name:''};
              $scope.myForm.$setPristine(); //reset Form
          }
 
      }]);
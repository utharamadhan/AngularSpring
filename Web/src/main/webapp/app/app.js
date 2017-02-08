'use strict';
 
var App = angular.module('myApp',['ui.router']);

App.config(function($stateProvider, $urlRouterProvider){
	
	$urlRouterProvider.otherwise('/simpleObject');
	
	$stateProvider.state('simpleObject', {
		url: '/simpleObject',
		templateUrl: './views/simpleObject/simpleObjectList.html',
		controller: 'SimpleObjectController as ctrl'
	});
	
});
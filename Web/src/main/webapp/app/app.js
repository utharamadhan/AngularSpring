'use strict';

var App = angular.module('myApp', ['ui.router', 'loaderGif']).run(function($rootScope){
	$rootScope.isLoaderShown = false;
	$rootScope.bodyClass = 'ng-scope';
});

App.config(function($stateProvider, $urlRouterProvider){
	$urlRouterProvider.otherwise('/login');

	$stateProvider.state('simpleObject', {
		url: '/simpleObject',
		templateUrl: './views/simpleObject/simpleObjectList.html',
		controller: 'SimpleObjectController as ctrl'
	});

	$stateProvider.state('login', {
		url: '/login',
		templateUrl: './views/login.html',
		controller: 'LoginController as ctrl'
	});
	
	$stateProvider.state('signup', {
		url: '/signup',
		templateUrl : './views/signup.html',
		controller: 'SignUpController as ctrl'
	});
	
});
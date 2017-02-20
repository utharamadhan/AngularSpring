'use strict';

var App = angular.module('myApp', ['ui.router', 'loaderGif', 'ngCookies']).run(function($rootScope, $cookies){
	$rootScope.isLoaderShown = false;
	$rootScope.bodyClass = 'ng-scope';
	$rootScope.loginSession = $cookies.getObject("loginSession");
});

App.config(function($stateProvider, $urlRouterProvider){
	$urlRouterProvider.otherwise('/login');

	$stateProvider.state('basic', {
		url: '/basic',
		templateUrl: './views/webPage/basic.html',
		controller: 'BasicWebController as ctrl',
		param: {
			'loginSessionRequired': true
		}
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
	
	$stateProvider.state('activation', {
		url: '/activation/:email',
		templateUrl : './views/activation.html',
		controller : 'ActivationController as ctrl'
	});
	
});
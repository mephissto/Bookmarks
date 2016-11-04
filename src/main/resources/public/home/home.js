'use strict';

angular.module('myApp.home', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl: 'home/home.html',
		controller: 'HomeCtrl'
	});
}])

.controller('HomeCtrl', ['$scope', '$http', HomeCtrl]);

function HomeCtrl($scope, $http) {

	$scope.categories = {};

	$scope.load = function() {
		$http.get("http://localhost:8080/category/list").then(function (response) {
			$scope.categories = response.data;
		});
	}

}
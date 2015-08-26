var viewsPath = "../resources/views/";
var baseUrl = "http://localhost:8090/myapp-web/rest/api/";
var token = "";

angular.module('myapp', ['ui.router', 'angularMoment'])

.config(function($httpProvider){
	
	$httpProvider.interceptors.push(function($rootScope) {
		return {
			'request': function(config) {
				config.headers['x-auth-token'] = token;
				return config;
			},
			
			'response': function(response){
				if (response.status === 401) {
					$rootScope.$broadcast('loginRequired');
				} else if (response.status === 200) {
					if(response.headers("x-auth-token"))
						token = response.headers("x-auth-token");
				}
				return response;
			},
			'responseError': function(response){
				if (response.status === 401) {
					$rootScope.$broadcast('loginRequired');
				}
				return response;
			}
	    };
	});
	
})

.constant('angularMomentConfig', {
	preprocess: 'utc',
	timezone: 'Asia/Calcutta'
})

.config(function($stateProvider, $urlRouterProvider) {
    $stateProvider

    .state('app', {
        url: "/app",
        abstract: true,
        templateUrl: viewsPath + "menu.html",
        controller: 'AppCtrl'
    })

    .state('app.login', {
        url: "/login",
        views: {
            'pageContent': {
                templateUrl: viewsPath + "login.html",
                controller: 'LoginCtrl'
            }
        }
    })
        
    .state('app.posts', {
        url: "/posts",
        views: {
            'pageContent': {
                templateUrl: viewsPath + "posts.html",
                controller: 'PostsCtrl'
            }
        }
    })
    
    .state('app.new_post', {
        url: "/posts/new",
        views: {
            'pageContent': {
                templateUrl: viewsPath + "new.html",
                controller: 'PostCtrl'
            }
        }
    })
    
    .state('app.view_post', {
        url: "/posts/view/:id",
        views: {
            'pageContent': {
                templateUrl: viewsPath + "view.html",
                controller: 'PostCtrl'
            }
        }
    })
    
    .state('app.edit_post', {
        url: "/posts/edit/:id",
        views: {
            'pageContent': {
                templateUrl: viewsPath + "new.html",
                controller: 'PostCtrl'
            }
        }
    });
    
    // if none of the above states are matched, use this as the fallback
    $urlRouterProvider.otherwise('/app/posts');
})

.controller('AppCtrl', function($http, $rootScope, $scope, $state){
	
	$scope.modalShown = false;
	
	$scope.toggleModal = function() {
	    $scope.modalShown = !$scope.modalShown;
	};
	
	$scope.$on('loginRequired', function(){
		$scope.modalShown = true;
	});
	
	$scope.user = {};
	
	$scope.doLogin = function(){
		$http.post(baseUrl + 'login', $scope.user).success(function(res){
			if(!res.error){
				$scope.modalShown = false;
				$rootScope.$broadcast('loggedIn');
				//$state.go("app.posts");
			}
		});
	};
})

.controller('LoginCtrl', function($scope, $http, $state){
	
	$scope.user = {};
	
	$scope.doLogin = function(){
		$http.post(baseUrl + 'login', $scope.user).success(function(res){
			if(!res.error){
				$state.go("app.posts");
			}
		});
	};
	
})

.controller('PostsCtrl', function($scope, $http){
	
	$scope.getPosts = function(){
		$http.get(baseUrl + 'posts.json').success(function(res){
			if(!res.error && res.posts && res.posts.length > 0){
				$scope.posts = res.posts;
			}
		});
	};
	
	$scope.getPosts();
	
	$scope.$on('loggedIn', function(){
		$scope.getPosts();
	});
		
	$scope.deletePost = function(postId){
		$http.delete(baseUrl + 'posts/' + postId).success(function(res){
			if(!res.error){
				$scope.getPosts();
			}
		});
	};
	
})

.controller('PostCtrl', function($scope, $http, $state, $stateParams, $anchorScroll, $location){
	
	$scope.post = {};
	
	$scope.showComments = function(){
		$location.hash('comments')
		$anchorScroll();
	};
	
	$scope.savePost = function(){
		
		if($scope.post.id && $scope.post.id != "" && $scope.post.id != 0){
			$http.put(baseUrl + 'posts', $scope.post).success(function(res){
				if(!res.error){
					$state.go('app.posts');
				}
			});
		} else {
			$http.post(baseUrl + 'posts', $scope.post).success(function(res){
				if(!res.error){
					$state.go('app.posts');
				}
			});
		}
		
	};
	
	$scope.getPost = function(){
		if($stateParams.id && $stateParams.id != ''){
			$http.get(baseUrl + 'posts/' + $stateParams.id + '.json').success(function(res){
				if(!res.error){
					$scope.post = res.post;
				}
			});
		}
	}
	
	$scope.getPost();
	
	$scope.$on('loggedIn', function(){
		$scope.getPost();
	});
})

.controller('CommentsController', function($scope, $http){
	
	$scope.post.comments = {};
	$scope.comment = {};
	
	$scope.comment.postId = $scope.post.id;
	
	var getComments = function(){
		if( $scope.post.id && $scope.post.id != ''){
			$http.get(baseUrl + 'posts/' + $scope.post.id + '/comments.json').success(function(res){
				if(!res.error){
					$scope.post.comments = res.comments;
				}
			});
		}
	};
	
	$scope.saveComment = function(){
		if( $scope.post.id && $scope.post.id != ''){
			$http.post(baseUrl + 'posts/' + $scope.post.id + '/comments', $scope.comment).success(function(res){
				if(!res.error){
					$scope.post.comments.push(res.comment);
					$scope.comment.body = "";
				}
			});
		}
	};
	
	$scope.deleteComment = function(commentId){
		if( $scope.post.id && $scope.post.id != ''){
			$http.delete(baseUrl + 'posts/' + $scope.post.id + '/comments/' + commentId).success(function(res){
				if(!res.error){
					getComments();
				}
			});
		}
	};
	
	getComments();
	
	$scope.$on('loggedIn', function(){
		getComments();
	});
		
})

.directive('modalDialog', function() {
	return {
		restrict: 'E',
		scope: {
			show: '='
		},
		replace: true, // Replace with the template below
		transclude: true, // we want to insert custom content inside the directive
		link: function(scope, element, attrs) {
			scope.dialogStyle = {};
			if (attrs.width)
				scope.dialogStyle.width = attrs.width;
			if (attrs.height)
				scope.dialogStyle.height = attrs.height;

			scope.hideModal = function() {
				scope.show = false;
			};
		},
		template: "<div class='ng-modal' ng-show='show'> \
					<div class='ng-modal-overlay'></div> \
					<div class='ng-modal-dialog' ng-style='dialogStyle'> \
					<div class='ng-modal-dialog-content' ng-transclude></div> \
					</div></div>"
	};
})

.directive('starRating', function() {
	  return {
		restrict: 'AE',
		replace: true,
		template: '<div />',
		link: function(scope, elem, attrs) {
			scope.className = "star";
			
			elem.append();
			
			elem.bind('mouseover', function() {
				scope.$apply(function() {
					scope.className = "star_full";
			    });
		  	});
		}
	  };
});
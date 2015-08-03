<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
	<head>
		<title>Home</title>
		<link rel="stylesheet" href="../resources/css/screen.css"/>
	</head>
	
	<body ng-app="myapp">
		<div ui-view></div>
		<script type="text/javascript" src="../resources/js/angular.min.js"></script>
		<script type="text/javascript" src="../resources/js/moment.min.js"></script>
		<script type="text/javascript" src="../resources/js/angular-moment.min.js"></script>
		<script type="text/javascript" src="../resources/js/angular-ui-router.min.js"></script>
		<script type="text/javascript" src="../resources/js/app/app.js"></script>
	</body>
</html>

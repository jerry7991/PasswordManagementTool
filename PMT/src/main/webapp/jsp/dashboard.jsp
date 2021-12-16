<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>DashBoard</title>
</head>
<body>
	<c:set var = "userDetails" scope = "session" value = "${response.getMsg()}"/>
	<form action="viewGroupBy" method="POST">
		<input type="submit" value = "view group by"/>
	</form>
</body>
</html>
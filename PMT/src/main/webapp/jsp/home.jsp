<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet" href="css/home.css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<title>PMT Tool</title>
<div class="wrapper fadeInDown">
	<h1>Password Management Tools</h1>
	<div id="formContent">
		<form action="login" method="POST">
			<input type="text" id="userName" class="fadeIn second form-control"
				name="userName" placeholder="user id"> <input
				type="password" id="password" class="fadeIn third form-control"
				name="password" placeholder="password"> <input type="submit"
				class="fadeIn fourth btn btn-success" value="Log In">
		</form>

		<div id="formFooter">
			<b style="color: red">${message.msg}</b><br> <a
				class="underlineHover" href="addUser">Create New User</a>
		</div>

	</div>
</div>
</html>
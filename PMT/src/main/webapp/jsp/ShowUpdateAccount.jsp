<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Account</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script
	src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-8 table-responsive">

				<form action="updateAccount" method="POST" style="margin: 0">
					<table class="table table-bordered table-hover table-sortable">
						<thead style="background-color: #563d7c;">
							<tr>
								<th colspan="2" class="text-center">Account Details</th>
							</tr>
						</thead>
						<tbody>

								<tr class="form" id='addr0' data-id="0">
									<td data-name="name">Account Name</td>
									<td data-name="name"><input type="text" name="accountName"
										value="${account.accountName}"
										class="accountDetails form-control" /></td>
								</tr>
								<tr>
									<td>Url</td>
									<td data-name="Open"><input type="text" name="url"
										value="${account.url}" class="accountDetails form-control" />
									</td>
								</tr>
								<tr>
									<td>Password</td>
									<td data-name="edit"><input type="password"
										name="password" value="${account.password}"
										class="accountDetails form-control" /> <input type="hidden"
										name="accountId" value="${account.accountId}" /> <input
										type="hidden" name="groupId" value="${account.groupId}" /></td>
								</tr>
								<tr>
									<td data-name="name"colspan="2"><input
										type="submit" value="Submit" style="margin-left:45%"/></td>
								</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
<!-- body>
	<form action="updateAccount" method="POST">
		<input type="text" name="accountName" value="${account.accountName}"
			class="accountDetails" /> <input type="text" name="url"
			value="${account.url}" class="accountDetails" /> <input
			type="password" names" /> <input type="hidden" name="accountId"
			value="${account.accountId}" /> <input type="hidden" name="groupId"
			value="${account.groupId}" /> <input type="submit" value="Submit" />
	</form>
</body-->
</html>
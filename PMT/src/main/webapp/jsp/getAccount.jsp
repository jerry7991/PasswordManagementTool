<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Account</title>
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
				<table class="table table-bordered table-hover table-sortable"
					id="tab_logic">
					<thead style="background-color: #563d7c;">
						<tr>
							<th class="text-center" colspan="7">Account Details</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${accounts}" var="account">
							<tr class="form" id='addr0' data-id="0">
								<form action="deleteAccount" method="POST" style="margin: 0">
									<td><input type="text" name="accountName"
										value="${account.accountName}"
										class="accountDetails form-control" readonly /></td>
									<td><input type="text" name="url" value="${account.url}"
										class="accountDetails form-control" readonly /></td>
									<td><input type="password" name="password"
										value="${account.password}"
										class="accountDetails form-control" readonly /></td>
									<td><c:set var="groupId" value="${account.groupId}" /> <input
										type="hidden" name="accountId" value="${account.accountId}" />
										<input type="hidden" name="groupId"
										value="${account.groupId}" /> <a
										href="showAccountByAccountID?accountId=${account.accountId}&groupId=${account.groupId}">Edit</a></td>
									<td><input type="submit" value="delete"
										class="delete btn btn-danger"
										onclick="return confirm('Are to sure to delete the detail.');" /></td>
								</form>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="col-md-4">
				<form action="addAccount" method="POST" id="addr0" data-id="0">
					<h3>Add Account</h3>
					<div>
						<div class="col-md-10" style="float: left">
							<input type="text" name="accountName" placeholder="Account Name"
								class="form-control" /> <input type="text" name="url"
								placeholder="url" class="form-control" /> <input type="password"
								name="password" class="form-control" placeholder="password"/> <input type="hidden"
								name="groupId" value="${groupId}" />
						</div>
						<div class="col-md-2" style="float: right">
							<input type="Submit" value="add account" class="btn btn-success">
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</body>
</html>
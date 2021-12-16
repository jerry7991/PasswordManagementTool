<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Groups</title>
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
		<h1 align="left">Group DashBoard</h1>
		<div class="row clearfix">
			<div class="col-md-8 table-responsive">
				<table class="table table-bordered table-hover table-sortable"
					id="tab_logic">
					<thead style="background-color: #563d7c;">
						<tr>
							<th class="text-center">Group Name</th>
							<th colspan="3" class="text-center">Operations</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${GroupDetails}" var="group">
							<tr class="form" id='addr0' data-id="0">
								<form action="UpdateGroupName" method="POST" style="margin: 0">
									<td data-name="name"><input type="text" name="groupName"
										value="${group.groupName}" readonly placeholder='Group Name'
										class="form-control data" /></td>
									<td data-name="Open"><a
										href="getAccount?groupName=${group.groupName}&groupId=${group.groupId}">Open</a>
									</td>
									<td data-name="edit"><input type="hidden" name="groupId"
										value="${group.groupId}" class="btn btn-warning" /> <input
										type="button" value="edit" class="edit btn btn-warning">
										<input type="submit" value="update"
										class="update btn btn-success" /></td>
									<td data-name="name" class="btn btn-danger"><a
										href="deleteGroup?groupName=${group.groupName}&groupId=${group.groupId}">Delete</a>
									</td>
								</form>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="col-md-4">
				<form action="addGroup" method="POST" id="addr0" data-id="0">
					<h3>Add Group</h3>
					<div>
						<div class="col-md-10" style="float: left">
							<input type="text" name="groupName" class="form-control" placeholder="group name">
						</div>
						<div class="col-md-2" style="float: right">
							<input type="Submit" value="add" class="btn btn-success">
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script>
		$(".update").hide();
		$(".edit").on("click", function() {
			$(this).hide();
			$(this).closest(".form").find(".update").show();
			$(this).closest(".form").find(".data").attr("readonly", false);
		});
	</script>
</body>
</html>
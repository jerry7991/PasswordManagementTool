/**
 * 
 */
$("#login").on("click", function() {

	var userName = $("#name").val();
	var password = $("#password").val();

	var obj = new Object();
	obj.userName = userName;
	obj.password = password;

	var str = JSON.stringify(obj);

	$.ajax({
		type: "POST",
		url: "login",
		data: str,
		dataType: "json",
		contentType: "application/json; charset=utf-8",
		success: function(msg) {
			alert(msg);
		}
	});
});
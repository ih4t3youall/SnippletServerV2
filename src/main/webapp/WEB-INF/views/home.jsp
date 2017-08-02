<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
<link
	href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/list.css" />" rel="stylesheet">
<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>


</head>
<div class="row">
	<div id="category" class="col-md-6">
		<h2>My Phonebook</h2>

		<input type="text" id="myInput" onkeyup="myFunction()"
			placeholder="Search for names.." title="Type in a name">

		<ul id="myUL">

			<c:forEach var="category" items="${user.category}" varStatus="status">

				<li><a href="#" onClick="">${category.nombreCategoria}</a></li>
				
			</c:forEach>
			<!-- <ul id="myUL"> -->
			<!--   <li><a href="#">Adele</a></li> -->
			<!--   <li><a href="#">Agnes</a></li> -->
			<!--   <li><a href="#">Billy</a></li> -->
			<!--   <li><a href="#">Bob</a></li> -->
			<!--   <li><a href="#">Calvin</a></li> -->
			<!--   <li><a href="#">Christina</a></li> -->
			<!--   <li><a href="#">Cindy</a></li> -->
			<!-- </ul> -->
		</ul>
	</div>
	<div id="snipplet" class="col-md-6">

		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Panel title</h3>
			</div>
			<div class="panel-body">Panel contentPanel</div>
		</div>

	</div>
</div>
<script>
	function myFunction() {
		var input, filter, ul, li, a, i;
		input = document.getElementById("myInput");
		filter = input.value.toUpperCase();
		ul = document.getElementById("myUL");
		li = ul.getElementsByTagName("li");
		for (i = 0; i < li.length; i++) {
			a = li[i].getElementsByTagName("a")[0];
			if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
				li[i].style.display = "";
			} else {
				li[i].style.display = "none";

			}
		}
	}
</script>

</body>
</html>
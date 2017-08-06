<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
<script src="<c:url value="/resources/js/jquery.js" />"></script>
<link
	href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/list.css" />" rel="stylesheet">
<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>



<script type="text/javascript">


function getSnipplets(categoriaId){
	
	$.ajax({
		url : "getSnipplets",
		type : "GET",
		data : "categoryId="+categoriaId,
		success : function(response) {
			$("#snipplet").empty();
			 $("#snipplet").append(response);
			 $("#snipplet").show();
			
		}
	});
	
	
}

</script>

</head>
<div class="row">
	<div id="category" class="col-md-6">
		<h2>Categorys</h2>

		<input type="text" id="myInput" onkeyup="myFunction()"
			placeholder="Search for names.." title="Type in a name">

		<ul id="myUL">

			<c:forEach var="cat" items="${category}" varStatus="status">

				<li><a href="#" onClick="getSnipplets('${cat.categoriaID}')">${cat.nombre}</a></li>
				
			</c:forEach>
		</ul>
	</div>
	<h2>Snipplets</h2>
	<div id="snipplet" class="col-md-6" style="display: none;">
	


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
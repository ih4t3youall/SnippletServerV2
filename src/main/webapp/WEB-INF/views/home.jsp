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

<style>
.list-group-item:hover {
	background-color: grey;
}

.limit {
	height: 100px;
}

nav ul {
	height: 500px;
	width: 99%;
}

nav ul {
	overflow: hidden;
	overflow-y: scroll;
}
</style>

<script type="text/javascript">
	function getSnipplets(categoriaId) {

		$("#category-snipplet-id").html(categoriaId);
		
		$.ajax({
			url : "getSnipplets",
			type : "GET",
			data : "categoryId=" + categoriaId,
			success : function(response) {
				$("#snipplet").empty();
				$("#snipplet").append(response);
				$("#snipplet").show();

			}
		});

	}
	
	function refreshSnipplet(snipplet){
		
		$("#"+snipplet.id).find(".panel-body").html(snipplet.contenido);
		$("#"+snipplet.id).find(".panel-title").html(snipplet.titulo);
		
		
	}
	
	function editSnipplet(id){
		
		$.ajax({
			url : "getModal",
			type : "GET",
			data : "snippletId=" + id,
			success : function(response) {
				$("#modal").empty();
				$("#modal").append(response);
				$('#modal-editar-snipplet').modal('show');

			}
		});
		
	}
	
	
	//snipplet things
	
	function addSnipplet(){
		
		var categoriaId = $("#category-snipplet-id").html();
		
		if(categoriaId != ""){
		
		$.ajax({
			url : "getModalAddSnipplet",
			type : "GET",
			success : function(response) {
				$("#modal").empty();
				$("#modal").append(response);
				$('#modal-crear-snipplet').modal('show');

			}
		});
		}else{
			
			alert("debe selccionar una categoria");
			
		}
		
		
	}

</script>

</head>
<div class="row">
	<div id="category" class="col-md-6">
		<button type="button" class="btn btn-outline-danger">Agregar Categoria</button>
		<h2>Categorias</h2>

		<input type="text" id="myInput" onkeyup="myFunction()"
			placeholder="Search for names.." title="Type in a name">
		<nav>
		<ul id="myUL">

			<c:forEach var="cat" items="${category}" varStatus="status">

				<li><a href="#" onClick="getSnipplets('${cat.categoriaID}')">${cat.nombre}</a></li>

			</c:forEach>
		</ul>
		</nav>
	</div>
	<button type="button" class="btn btn-outline-danger" onClick="addSnipplet()">Agregar Snipplet</button>
	<h2>Snipplets</h2>
	<p style="visibility: hidden;" id="category-snipplet-id"></p>
	<div id="snipplet" class="col-md-6" style="display: none;"></div>
</div>

<div id="modal"></div>

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
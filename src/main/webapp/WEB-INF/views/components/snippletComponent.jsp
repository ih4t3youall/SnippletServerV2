<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:forEach var="snip" items="${snipplets}" varStatus="status">

	<div id="${snip.id}" class="panel panel-primary" onclick="editSnipplet('${snip.id}')">
	<p style="display: none;">${snip.id} </p>
		<div class="panel-heading">
			<h3 class="panel-title">${snip.titulo}</h3>
		</div>
		<div class="panel-body">${snip.contenido}</div>
	</div>

</c:forEach>
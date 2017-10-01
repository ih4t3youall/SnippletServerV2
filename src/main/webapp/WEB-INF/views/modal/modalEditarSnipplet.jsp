<script type="text/javascript">

function eliminarSnipplet(){
	
	var snipplet = new Object();
	
	snipplet.id = $("#snipplet-id").html();
	var toSend =JSON.stringify(snipplet);
	var categoriaId = $("#category-snipplet-id").html();
	$.ajax({
		url : "eliminarSnipplet",
		type : "POST",
		data : "jsonSnipplet=" + toSend+"&categoryId="+categoriaId,
		success : function(response) {
			$('#modal-editar-snipplet').modal('hide');
			getSnipplets(categoriaId);
		}
	});
	
}

function cerrarModalEditarSnipplet(){
	
	var snipplet = new Object();
	snipplet.titulo = $("#snipplet-titulo").val();
	snipplet.contenido = $("#snipplet-contenido").val();
	snipplet.id = $("#snipplet-id").html();
	
	var toSend =JSON.stringify(snipplet);
	
	
	$.ajax({
		url : "saveEditedSnipplet",
		type : "POST",
		data : "jsonSnipplet=" + toSend,
		success : function(response) {
			$('#modal-editar-snipplet').modal('hide');
			refreshSnipplet(snipplet);

		}
	});
	
	
}

</script>


<div class="container">
		<!-- Modal -->
		<div class="modal fade" id="modal-editar-snipplet" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 style="visibility: hidden;" class="modal-snipplet-id" id="snipplet-id">${snipplet.id }</h4>
						<h4 class="modal-agegar-categoria-title">
							<input type="text" class="modal-title-input" id="snipplet-titulo" value="${snipplet.titulo}">
						</h4>
					</div>
					<div class="modal-agregar-categoria-body">
						<textarea rows="4" cols="50" class="modal-agregar-categoria-contenido" id="snipplet-contenido" >${snipplet.contenido }</textarea>
					</div>
					<div class="modal-agregar-categoria-footer">
						<button type="button" class="btn btn-default"
							onclick="cerrarModalEditarSnipplet()">Close</button>
							<button type="button" class="btn btn-default"
							onclick="eliminarSnipplet()">Eliminar Snipplet</button>
					</div>
				</div>
			</div>
		</div>
	</div>

<script type="text/javascript">

function cerrarModalCrearSnipplet(){
	
	var snipplet = new Object();
	snipplet.titulo = $("#snipplet-titulo").val();
	snipplet.contenido = $("#snipplet-contenido").val();
	
	var toSend =JSON.stringify(snipplet);
	var categoriaId = $("#category-snipplet-id").html();
	
	$.ajax({
		url : "createNewSnipplet",
		type : "POST",
		data : "jsonSnipplet=" + toSend+"&categoryId="+categoriaId,
		success : function(response) {
			$('#modal-crear-snipplet').modal('hide');
			getSnipplets(categoriaId);

		}
	});
	
	
}

</script>


<div class="container">
		<!-- Modal -->
		<div class="modal fade" id="modal-crear-snipplet" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 style="visibility: hidden;" class="modal-snipplet-id" id="snipplet-id"></h4>
						<h4 class="modal-agegar-categoria-title">
							<input type="text" class="modal-title-input" id="snipplet-titulo" value="">
						</h4>
					</div>
					<div class="modal-agregar-categoria-body">
						<textarea rows="4" cols="50" class="modal-agregar-categoria-contenido" id="snipplet-contenido" ></textarea>
					</div>
					<div class="modal-agregar-categoria-footer">
						<button type="button" class="btn btn-default"
							onclick="cerrarModalCrearSnipplet()">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>

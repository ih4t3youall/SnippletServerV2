<script type="text/javascript">

function cerrarModalCrearCategory(){
	
	
	
	$.ajax({
		url : "createNewCategory",
		type : "GET",
		data : "categoryTitle=" +$("#category-titulo").val(),
		success : function(response) {
			$('#modal-crear-categoria').modal('hide');
			location.reload();

		}
	});
	
	
}

</script>


<div class="container">
		<!-- Modal -->
		<div class="modal fade" id="modal-crear-categoria" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 style="visibility: hidden;" class="modal-snipplet-id" id="snipplet-id"></h4>
						<h4 class="modal-agegar-categoria-title">
							<input type="text" class="modal-title-input" id="category-titulo" value="">
						</h4>
					</div>
					
					<div class="modal-agregar-categoria-footer">
						<button type="button" class="btn btn-default"
							onclick="cerrarModalCrearCategory()">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>

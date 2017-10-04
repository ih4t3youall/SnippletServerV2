<script type="text/javascript">

function cerrarModalCrearSnipplet(){
	
	var passwd1 = $("#passwd1").val();	
	var passwd2 = $("#passwd2").val();	

	$.ajax({
		url : "cambiarPasswd",
		type : "POST",
		data : "passwd1=" + passwd1+"&passwd2="+passwd2,
		success : function(response) {
			$('#modal-cambiar-passwd').modal('hide');
			alert(response);
		}
	});
	
	
}

</script>


<div class="container">
		<!-- Modal -->
		<div class="modal fade" id="modal-cambiar-passwd" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-cambiar-passwd-title">
						<p>Ingrese y confirme su password</p>
						</h4>
					</div>
					<div class="modal-cambiar-passwd-body">
						<p>ingrese nuevo passwd</p>
						<input type="password" id="passwd1">

						<p>repita el nuevo passwd</p>
						<input type="password" id="passwd2">
					</div>
					<div class="modal-agregar-categoria-footer">
						<button type="button" class="btn btn-default"
							onclick="cerrarModalCrearSnipplet()">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>

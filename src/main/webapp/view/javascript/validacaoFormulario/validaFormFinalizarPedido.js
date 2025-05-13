	const msgCampoObrigatorio = "Campo obrigatório";

		$(document).ready(function() {
			$("#formFinalizarPedido").validate({
				rules : {
					enderecoEntrega : {
						required : true
					},
					enderecoCobranca : {
						required : true
					}
				},
				messages : {
					enderecoEntrega : {
						required : msgCampoObrigatorio
					},
					enderecoCobranca : {
						required : msgCampoObrigatorio
					}			
				}
			});
		});
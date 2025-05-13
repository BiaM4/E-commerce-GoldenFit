	const msgCampoObrigatorio = "Campo obrigatório";

		$(document).ready(function() {
			$("#formCadastroCartao").validate({
				rules : {
					bandeira : {
						required : true
					},
					numeroCartao : {
						required : true
					},
					nomeImpresso : {
						required : true
					},
					codigoSeguranca : {
						required : true
					}
				},
				messages : {
					bandeira : {
						required : msgCampoObrigatorio
					},
					numeroCartao : {
						required : msgCampoObrigatorio
					},
					nomeImpresso : {
						required : msgCampoObrigatorio
					},
					codigoSeguranca : {
						required : msgCampoObrigatorio
					}					
				}
			});
		});
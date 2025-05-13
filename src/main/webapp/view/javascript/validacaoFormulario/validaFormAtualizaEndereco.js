	const msgCampoObrigatorio = "Campo obrigatório"

		$(document).ready(function() {
			$("#formAtualizaEndereco").validate({
				rules : {	
					tipoLogradouro : {
						required : true
					},
					logradouro : {
						required : true
					},
					tipoResidencia : {
						required : true
					},
					numeroEndereco : {
						required : true
					},
					bairro : {
						required : true
					},
					cep : {
						required : true
					},
					estado : {
						required : true
					},
					cidade : {
						required : true
					},
					descricaoEndereco : {
						required : true
					}
				},
				messages : {
					tipoLogradouro : {
						required : msgCampoObrigatorio
					},
					logradouro : {
						required : msgCampoObrigatorio
					},
					tipoResidencia : {
						required : msgCampoObrigatorio
					},
					numeroEndereco : {
						required : msgCampoObrigatorio
					},
					bairro : {
						required : msgCampoObrigatorio
					},
					cep : {
						required : msgCampoObrigatorio
					},
					estado : {
						required : msgCampoObrigatorio
					},
					cidade : {
						required : msgCampoObrigatorio
					},
					descricaoEndereco : {
						required : msgCampoObrigatorio
					}
					
				}
			});
		});
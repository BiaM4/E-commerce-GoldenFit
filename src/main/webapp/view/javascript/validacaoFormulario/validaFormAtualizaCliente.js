	const msgCampoObrigatorio = "Campo obrigatório"

		$(document).ready(function() {
			$("#formAtualizaCliente").validate({
				rules : {
					email : {
						required : true,
						email : true
					},
					nome : {
						required : true
					},
					sobrenome : {
						required : true
					},
					genero : {
						required : true
					},
					dddResidencial : {
						required : true
					},
					numeroTelResidencial : {
						required : true
					},
					dataNascimento : {
						required : true
					},
					cpf : {
						required : true
					}
					
				},
				messages : {
					email : {
						required : msgCampoObrigatorio,
						email : "Por favor, informe um e-mail válido"
					},
					nome : {
						required : msgCampoObrigatorio
					},
					sobrenome : {
						required : msgCampoObrigatorio
					},
					genero : {
						required : msgCampoObrigatorio
					},
					dddResidencial : {
						required : msgCampoObrigatorio
					},
					numeroTelResidencial : {
						required : msgCampoObrigatorio
					},
					dataNascimento : {
						required : msgCampoObrigatorio
					},
					cpf : {
						required : msgCampoObrigatorio
					}
					
				}
			});
		});
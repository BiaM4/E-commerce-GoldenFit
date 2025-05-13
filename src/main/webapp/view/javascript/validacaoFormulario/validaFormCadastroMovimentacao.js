	const msgCampoObrigatorio = "Campo obrigatório";

		$(document).ready(function() {
			$("#formCadastroMovimentacao").validate({
				rules : {
					data : {
						required : true
					},
					tipo : {
						required : true
					},
					livro : {
						required : true
					},
					quantidade : {
						required : true
					},
					fornecedor : {
						required : true
					},
					precoCusto : {
						required : true
					}
				},
				messages : {
					data : {
						required : msgCampoObrigatorio
					},
					tipo : {
						required : msgCampoObrigatorio
					},
					livro : {
						required : msgCampoObrigatorio
					},
					quantidade : {
						required : msgCampoObrigatorio
					},
					fornecedor : {
						required : msgCampoObrigatorio
					},
					precoCusto : {
						required : msgCampoObrigatorio
					}				
				}
			});
		});
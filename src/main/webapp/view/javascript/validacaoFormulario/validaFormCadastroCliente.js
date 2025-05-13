// 	const msgCampoObrigatorio = "Campo obrigatório"
//
// 	$(document).ready(function() {
// 		$.validator.addMethod("senhaForte", function(value) {
// 			let regex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#!?$%&])[0-9a-zA-Z$*&@#!?$%&]{8,}$/;
// 			return (regex.test(value));
// 		}, "A senha deve conter ao menos: 1 letra maíuscula, 1 letra minúscula, 1 número e 1 caractere especial ($*&@#!?$%&)");
//
//
// 		$("#formCadastroCliente").validate({
// 				rules : {
// 					nome : {
// 						required : true
// 					},
// 					sobrenome : {
// 						required : true
// 					},
// 					genero : {
// 						required : true
// 					},
// 					dddResidencial : {
// 						required : true
// 					},
// 					numeroTelResidencial : {
// 						required : true
// 					},
// 					dddCelular : {
// 						required : true
// 					},
// 					numeroTelCelular : {
// 						required : true
// 					},
// 					dataNascimento : {
// 						required : true
// 					},
// 					cpf : {
// 						required : true
// 					},
// 					tipoLogradouro : {
// 						required : true
// 					},
// 					logradouro : {
// 						required : true
// 					},
// 					tipoResidencia : {
// 						required : true
// 					},
// 					numeroEndereco : {
// 						required : true
// 					},
// 					bairro : {
// 						required : true
// 					},
// 					cep : {
// 						required : true
// 					},
// 					estado : {
// 						required : true
// 					},
// 					cidade : {
// 						required : true
// 					},
// 					descricaoEndereco : {
// 						required : true
// 					},
// <<<<<<< Updated upstream
// 					observacaoEndereco : {
// 						required : true
// =======
// <<<<<<< HEAD
// 					senha : {
// 						required: true,
// 						minlength : 8,
// 						senhaForte: true
// 					},
// 					confirmacaoSenha: {
// 						required: true,
// 						equalTo : "#senha"
// =======
// 					observacaoEndereco : {
// 						required : true
// >>>>>>> b898719bb61a74b6d92ad9934aeacd68af57747b
// >>>>>>> Stashed changes
// 					}
//
// 				},
// 				messages : {
// 					nome : {
// 						required : msgCampoObrigatorio
// 					},
// 					sobrenome : {
// 						required : msgCampoObrigatorio
// 					},
// 					genero : {
// 						required : msgCampoObrigatorio
// 					},
// 					dddResidencial : {
// 						required : msgCampoObrigatorio
// 					},
// 					numeroTelResidencial : {
// 						required : msgCampoObrigatorio
// 					},
// 					dddCelular : {
// 						required : msgCampoObrigatorio
// 					},
// 					numeroTelCelular : {
// 						required : msgCampoObrigatorio
// 					},
// 					dataNascimento : {
// 						required : msgCampoObrigatorio
// 					},
// 					cpf : {
// 						required : msgCampoObrigatorio
// 					},
// 					tipoLogradouro : {
// 						required : msgCampoObrigatorio
// 					},
// 					logradouro : {
// 						required : msgCampoObrigatorio
// 					},
// 					tipoResidencia : {
// 						required : msgCampoObrigatorio
// 					},
// 					numeroEndereco : {
// 						required : msgCampoObrigatorio
// 					},
// 					bairro : {
// 						required : msgCampoObrigatorio
// 					},
// 					cep : {
// 						required : msgCampoObrigatorio
// 					},
// 					estado : {
// 						required : msgCampoObrigatorio
// 					},
// 					cidade : {
// 						required : msgCampoObrigatorio
// 					},
// 					descricaoEndereco : {
// 						required : msgCampoObrigatorio
// 					},
// <<<<<<< Updated upstream
// 					observacaoEndereco : {
// 						required : msgCampoObrigatorio
// =======
// <<<<<<< HEAD
// 					senha: {
// 						required: "Por favor, informe sua senha",
// 						minlength : "A senha precisa ter no mínimo 8 dígitos"
// 					},
// 					confirmacaoSenha: {
// 						required: "Por favor, confirme sua senha",
// 						equalTo : "A senha e a confirmação não conferem"
// =======
// 					observacaoEndereco : {
// 						required : msgCampoObrigatorio
// >>>>>>> b898719bb61a74b6d92ad9934aeacd68af57747b
// >>>>>>> Stashed changes
// 					}
// 				}
// 			});
// 		});
/**
 * Mascaras do formulário de cadastro do cliente
 */
 $(document).ready(function () {		 
	$('input[name=dddResidencial]').mask('00');
	$('input[name=numeroTelResidencial]').mask('0000-0000');
	$('input[name=dddCelular]').mask('00');
	$('input[name=numeroTelCelular]').mask('00000-0000');
	$('input[name=dataNascimento]').mask('00/00/0000');
	$('input[name=cpf]').mask('000.000.000-00');
	$('input[name=numeroEndereco]').mask('000000');
	$('input[name=cep]').mask('00000-000');
})


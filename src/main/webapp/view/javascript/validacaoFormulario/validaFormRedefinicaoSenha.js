 $(document).ready(function() {
 		 $.validator.addMethod("senhaForte", function(value) {
		        let regex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#!?$%&])[0-9a-zA-Z$*&@#!?$%&]{8,}$/;
		        return (regex.test(value));
   			 }, "A senha deve conter ao menos: 1 letra maíuscula, 1 letra minúscula, 1 número e 1 caractere especial ($*&@#!?$%&)");
		
	  $("#formRedefinicaoSenha").validate({
	    rules: {
	      senha : {
	        required: true,
	        minlength : 8,
	        senhaForte: true
	      },
	      confirmacaoSenha: {
	        required: true,
	        equalTo : "#senha"
	      }
	    },
	    messages : {
	      senha: {
	        required: "Por favor, informe sua senha",
	        minlength : "A senha precisa ter no mínimo 8 dígitos"
	      },
	      confirmacaoSenha: {
	        required: "Por favor, confirme sua senha",
	        equalTo : "A senha e a confirmação não conferem"
	      }
	    }
	  });
	});
 $(document).ready(function(){	
	  $("#formConfirmacaoSenha").validate({
	    rules: {
	      senha : {
	        required: true,
	        minlength : 8
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
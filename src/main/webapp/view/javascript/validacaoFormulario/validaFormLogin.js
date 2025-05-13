 $(document).ready(function() {
	  $("#formLogin").validate({
	    rules: {
	      email : {
	        required: true,
	        email:true
	      },
	      senha: {
	        required: true
	      }
	    },
	    messages : {
	      email: {
	        required: "Por favor, informe seu e-mail",
	        email:"Por favor, informe um e-mail válido"
	      },
	      senha: {
	        required: "Por favor, informe sua senha"
	      }
	    }
	  });
	});
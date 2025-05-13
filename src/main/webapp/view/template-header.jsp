		<link href="fontawesome/css/all.css" rel="stylesheet">
		
		<div class="cabecalho-ecomm mb-3 p-3 d-flex justify-content-between shadow">
		<a href="/ecommerce/view/index" style="color:white;text-decoration:none;"><strong>GoldenFit</strong></a>

	<div class="dropdown d-flex align-items-center">
		<button class="btn btn-primary btn-lg btn-block btn-custom dropdown-toggle" type="button"
			id="dropdownMenuButton" data-bs-toggle="dropdown" alt="Minha conta" title="Minha conta"
			aria-expanded="false">
			<i class="fas fa-user-circle"></i>
		</button>
		<ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
			<%if (request.getSession().getAttribute("clienteLogado")==null){%>
			<li><a class="dropdown-item" href="login.jsp">Entrar</a></li>
			<li><a class="dropdown-item" href="cadastraCliente.jsp">Cadastro</a></li>
			<%} %>
			<%if (request.getSession().getAttribute("clienteLogado")!=null){%>
				<li><a class="dropdown-item" href="carrinho.jsp">Carrinho</a></li>
				<li><a class="dropdown-item" href="listaClientes.jsp">Minha conta</a></li>
			<li><a class="dropdown-item" href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarPedidosVH">Meus pedidos</a></li>
			<li><a class="dropdown-item" href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarCuponsVH">Meus cupons</a></li>
			<li><a class="dropdown-item" href="/GoldenFit_war/controlador?acao=Logout">Sair</a></li>
			<%}%>		
		</ul>
	</div>
</div>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>

<header>
	<nav class="navbar" style="background-color: #173B21; height: 60px;">
		<div class="container-fluid">
			<div class="d-flex justify-content-between align-items-center">
				<div class="d-flex flex-row mb-3 align-items-center">
					<img src="images/LogoGold.png" style="height: 35px; width: 35px; margin-left: 3%; margin-top: 1%;">
					<a class="navbar-brand" href="#" style="color:#ECBD69; margin-right: 70px; margin-left: 3%; margin-top: 1%;">GOLDENFIT</a>
				</div>
				<nav class="navbar navbar-dark bg-dark shadow" style="margin-left: 500%; margin-top: -5%">
					<div>
						<button class="navbar-toggler btn btn-outline-light" type="button" data-bs-toggle="modal" data-bs-target="#exampleModal">
							<span class="navbar-toggler-icon"></span>
						</button>
					</div>
				</nav>
			</div>
		</div>
	</nav>
	<div style="background-color: #ECBD69; height: 5px;"></div>

	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-fullscreen">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="list-group list-group-flush">
						<a href="#" class="list-group-item list-group-item-action active" id="topico1" style="font-weight: bold">
							GERENCIAMENTO
						</a>
						<a href="painelAdmin.jsp" class="list-group-item list-group-item-action">Painel</a>
						<a href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarPedidosAdminVH" class="list-group-item list-group-item-action">Pedidos</a>
						<a href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarClientesVH" class="list-group-item list-group-item-action">Clientes</a>
						<a href="#" class="list-group-item list-group-item-action active" id="topico2" style="font-weight: bold">
							ESTOQUE
						</a>
						<a href="/GoldenFit_war/view/precificacao" class="list-group-item list-group-item-action">Precificação</a>
						<a href="/GoldenFit_war/view/categoria" class="list-group-item list-group-item-action">Categoria</a>
						<a href="/GoldenFit_war/view/carregarDadosProduto" class="list-group-item list-group-item-action">Cadastrar Produto</a>
						<a href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarProdutosVH" class="list-group-item list-group-item-action">Consultar Produtos</a>
						<a href="/GoldenFit_war/view/carregarTodosProdutos" class="list-group-item list-group-item-action">Movimentações</a>
						<a href="consultarMovimentacoes.jsp" class="list-group-item list-group-item-action">Consultar movimentações</a>
						<a href="#" class="list-group-item list-group-item-action active" id="topico3" style="font-weight: bold">
							CUPONS
						</a>
						<a href="/GoldenFit_war/view/consultarClientes" class="list-group-item list-group-item-action">Cadastrar cupons</a>
						<a href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarCuponsAdminVH" class="list-group-item list-group-item-action">Consultar cupons</a>

						<a href="#" class="list-group-item list-group-item-action active" id="topico4" style="font-weight: bold">
							DASHBOARDS
						</a>

						<a href="dashboard.jsp" class="list-group-item list-group-item-action">Vendas por Produto</a>
						<a href="dashboard2.jsp" class="list-group-item list-group-item-action">Vendas por Mês</a>

						<a href="/GoldenFit_war/controlador?acao=Logout" class="list-group-item list-group-item-action enable"><strong>Sair</strong></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</header>

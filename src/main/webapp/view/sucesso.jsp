<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:url value="/controlador" var="stub" />
<!DOCTYPE html>
<html>

<head>
	<c:import url="template-head.jsp" />
	<title>Pedido Recebido</title>

	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
		  rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
		  crossorigin="anonymous">
	<link rel="stylesheet" href="view/css/stylesView.css">

	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
			integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+OJrXkxBxSkn0m9fUkV+zGMNb9I1c"
			crossorigin="anonymous"></script>
</head>

<body>
<nav class="navbar" style="background-color: #173B21; height: 60px">
	<div class="d-flex flex-row mb-3" style="justify-content: space-between; align-items: center; margin-top: 5px; margin-left: 30px">
		<div class="d-flex flex-row mb-3" style="justify-content: center; align-items: center;">
			<img src="images/LogoGold.png" style="height: 35px; width: 35px">
			<a class="navbar-brand" href="/GoldenFit_war/view/index" style="color:#ECBD69; margin-right: 70px">GOLDENFIT</a>
		</div>

		<div class="d-flex flex-row mb-3" style="justify-content: flex-end; align-items: center;margin-left: 210%">
			<div class="btn-group">
				<img src="images/user.png" style="height: 30px; width: 30px;">
				<button style="color:#ffffff" type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
					Olá, ${sessionScope.clienteLogado.nome}
				</button>
				<ul class="dropdown-menu">
					<li><a class="dropdown-item" href="listaClientes.jsp">Meu perfil</a></li>
					<li><hr class="dropdown-divider"></li>
					<li><a class="dropdown-item" href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarCartaoVH">Meus cartões</a></li>
					<li><a class="dropdown-item" href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarEnderecoVH">Endereços</a></li>
					<li><a class="dropdown-item" href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarPedidosVH">Meus pedidos</a></li>
					<li><a class="dropdown-item" href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarCuponsVH">Meus cupons</a></li>
					<li><hr class="dropdown-divider"></li>
					<li><a class="dropdown-item" href="/GoldenFit_war/controlador?acao=Logout">Logout</a></li>
				</ul>
			</div>
			<a href="carrinho.jsp" style="text-decoration: none;">
				<img src="images/shopping-bag.png" alt="Shopping Bag" style="height: 30px; width: 30px; margin-left: 10px; cursor: pointer;">
			</a>
		</div>
	</div>
</nav>

<div>
	<div style="background-color: #ECBD69; height: 5px;"></div>

	<form id="formSucesso" action="${stub}">
		<div class="container" style="margin-top: 5%; width: 70%">
			<h1 class="card-header" style="color:#173B21;text-align: center; font-size: 25px; margin-bottom: 2%"> PEDIDO RECEBIDO </h1>
			<div class="card shadow mb-5 pb-4">
				<div class="card-body">
					<div class="row">
						<div class="row pt-2">
							<div class="col-12">
								<p>Seu pedido foi recebido com sucesso pela GoldenFit.
									Estamos processando as informações de seu pedido.</p>
							</div>
							<div class="row align-items-center w-100" style="margin-left: 11px">
								<div class="col-auto mx-auto mt-7 form-control pt-4 pb-3 text-center font-weight-bold">PAGAMENTO EM PROCESSAMENTO</div>
							</div>
							<div class="row "></div>
							<div class="col-10 mt-4">Os produtos serão enviados em até
								3 dias utéis após a aprovação de pagamento.</div>
						</div>
					</div>
				</div>
			</div>
			<input type="hidden" name="acao" value="consultar" /> <input
				type="hidden" name="viewHelper" value="ConsultarPedidoVH" /> <input
				type="hidden" name="tipoPesquisa" value="ultimoCadastrado" />

			<div style="text-align: center;">
				<div class="d-flex justify-content-center">
					<a href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarPedidosVH" class="btn btn-secondary w-15" type="submit" title="Ver pedidos" alt="Ver pedidos" style="background-color: #173B21; border-color: #173B21; margin-right: 10px">Ver pedidos</a>
					<a href="/GoldenFit_war/view/index" class="btn btn-secondary w-15"
					   style="background-color: #ECBD69; border-color: #ECBD69">Voltar</a>
				</div>
			</div>
		</div>
	</form>
</div>
</body>
</html>

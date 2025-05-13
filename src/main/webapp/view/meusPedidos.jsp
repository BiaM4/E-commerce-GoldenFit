<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.List,br.com.fatec.goldenfit.model.Pedido" %>
<!DOCTYPE html>
<html>
<head>
	<title>Meus pedidos</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
		  rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
	<link rel="stylesheet" href="view/css/stylesView.css">

	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<%
	List<Pedido> pedidos = (List<Pedido>) request.getSession().getAttribute("pedidos");
%>
<body>
	<nav class="navbar" style="background-color: #173B21; height: 60px ">
		<div class="d-flex flex-row mb-3" style="justify-content: space-between; align-items: center; margin-top: 5px; margin-left: 30px">
			<div class="d-flex flex-row mb-3" style="justify-content: center; align-items: center;">
				<img src="images/LogoGold.png" style="height: 35px; width: 35px">
				<a class="navbar-brand" href="/GoldenFit_war/view/index" style="color:#ECBD69; margin-right: 70px ">GOLDENFIT</a>
			</div>
			<div class="d-flex flex-row mb-3" style="justify-content: flex-end; align-items: center;margin-left: 210%">
				<div class="btn-group">
					<img src="images/user.png" style="height: 30px; width: 30px;">
					<button style="color:#ffffff" type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown"
							aria-expanded="false">
						Olá, ${sessionScope.clienteLogado.nome}
					</button>
					<ul class="dropdown-menu">
						<li><a class="dropdown-item" href="listaClientes.jsp">Meu perfil</a></li>
						<li>
							<hr class="dropdown-divider">
						</li>
						<li><a class="dropdown-item" href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarCartaoVH">Meus cartões</a></li>
						<li><a class="dropdown-item"
							   href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarEnderecoVH">Endereços</a>
						</li>
						<li><a class="dropdown-item" href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarCuponsVH">Meus cupons</a></li>
						<hr class="dropdown-divider">
						</li>
						<li><a class="dropdown-item" href="/GoldenFit_war/controlador?acao=Logout">Logout</a></li>
					</ul>
				</div>
				<a href="carrinho.jsp" style="text-decoration: none;">
					<img src="images/shopping-bag.png" alt="Shopping Bag"
						 style="height: 30px; width: 30px; margin-left: 10px; cursor: pointer;">
				</a>
			</div>
		</div>
	</nav>

	<div style="background-color: #ECBD69; height: 5px;"></div>

	<div class="row" style="margin-top: 2%; margin-right: 12%">
		<div class="col">
			<p style="color: #ECBD69; text-align: right;"><strong>* Cancelamento da compra só pode ser realizado até 7 dias após a data da compra.</strong></p>
		</div>
	</div>

	<div class="container" style="margin-top: 2%; width: 60%">
		<h1 style="color:#173B21;text-align: center; font-size: 30px;">HISTÓRICO DE PEDIDOS</h1>
		<div style="background-color: #173B21; height: 5px; margin-bottom: 3%"></div>
				<div class="row" style="margin-left: 8%">
					<div class="col-3">
						<p class="h5">Número do pedido</p>
					</div>
					<div class="col-2">
						<p class="h5">Valor</p>
					</div>
					<div class="col-2">
						<p class="h5">Status</p>
					</div>
					<div class="col-2">
						<p class="h5">Data</p>
					</div>
					<div class="col-2">
						<p class="h5" style="color:#FFF;"></p>
					</div>				
				</div>
				<%if(pedidos != null){
					for(Pedido pedido : pedidos){%>
						<div class="row" style="margin-left: 8%; margin-bottom: 2%">
							<div class="col-3">
								<p><%=pedido.getId()%></p>
							</div>
							<div class="col-2">
								<fmt:setLocale value = "pt_BR"/>								
								<p><fmt:formatNumber value = "<%=pedido.getValorTotal()%>" type = "currency"/></p>
							</div>
							<div class="col-2">
								<%=pedido.getStatus() != null ? pedido.getStatus().getDescricao() : ""%>
							</div>
							<div class="col-2">
								<p><fmt:formatDate value="<%=pedido.getDtCadastro()%>" pattern="dd/MM/yyyy"/></p>
							</div>
							<div class="col-2">
								<a id="detalhePedido<%=pedido.getId() %>" href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarPedidoVH&id=<%=pedido.getId()%>" class="btn btn-secondary w-26" type="submit" title="Detalhes" alt="Detalhes" style="background-color: #173B21; border-color: #173B21; margin-top: -3%">Detalhes</a>
							</div>
						</div>
					<%}
					}%>
		</div>
	</div>
</body>
</html>
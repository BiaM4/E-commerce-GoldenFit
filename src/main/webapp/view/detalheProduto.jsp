<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="br.com.fatec.goldenfit.model.Produto, br.com.fatec.goldenfit.model.Categoria"%>
<%@ page import="br.com.fatec.goldenfit.model.Cliente" %>
<c:url value="/controlador" var="stub"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Detalhes do Produto</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
		  rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
	<link rel="stylesheet" href="view/css/stylesView.css">

	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<%
	Produto produto = (Produto) request.getAttribute("produto");
%>
<fmt:setLocale value = "pt_BR"/>

<body>
<nav class="navbar" style="background-color: #173B21; height: 60px ">
	<div class="d-flex flex-row mb-3" style="justify-content: space-between; align-items: center; margin-top: 5px; margin-left: 30px">
		<div class="d-flex flex-row mb-3" style="justify-content: center; align-items: center;">
			<img src="view/images/LogoGold.png" style="height: 35px; width: 35px">
			<a class="navbar-brand" href="/GoldenFit_war/view/index" style="color:#ECBD69; margin-right: 15px ">GOLDENFIT</a>
		</div>
		<div class="d-flex flex-row mb-3" style="justify-content: center; align-items: center; margin-left: 15%; margin-right: 5%" >
			<form id="formConsultaProdutos" class="d-flex" action="${stub}" method="GET" novalidate>
				<input class="form-control me-2" type="search" id="nome" name="nome" placeholder="O que está buscando?" aria-label="Search" style="height: 25px; width: 550px">
				<button class="btn btn-success" type="submit" style="height: 25px; width: 50px; font-size: 12px; position: relative;">
					<img style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); margin-top: -2%;" src="view/images/search.svg">
				</button>
				<input type="hidden" name="acao" value="consultar"/>
				<input type="hidden" name="viewHelper" value="BuscarProdutosVH"/>
				<input type="hidden" name="tipoPesquisa" value="filtros"/>
			</form>
		</div>
		<div class="d-flex flex-row mb-3" style="justify-content: center; align-items: center;">
			<img src="view/images/user.png" style="height: 30px; width: 30px; margin-left: 30px">
			<%
				Cliente cliente = (Cliente) session.getAttribute("clienteLogado");
				if (cliente != null) {
					String nome = cliente.getNome();
			%>
			<%--                <a class="navbar-brand" href="view/listaClientes.jsp" style="color:#ffffff ; font-size: 12px; margin-left: 20px">Olá, <%=nome%></a>--%>
			<div class="btn-group">
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
			<%
			} else {
			%>
			<a class="navbar-brand" href="view/login.jsp" style="color:#ffffff ; font-size: 12px; margin-left: 20px">Entre ou cadastre-se</a>
			<%
				}
			%>
			<a href="view/carrinho.jsp">
				<img src="view/images/shopping-bag.png" style="height: 30px; width: 30px; margin-left: 50px">
			</a>
		</div>
	</div>
</nav>
<div style="background-color: #ECBD69; height: 5px;"></div>

<div class="d-flex flex-row mb-3" style="margin-top: 5%;margin-left:10%">
	<div>
		<img style="margin-left: 35%; margin-top: 30%; width:400px; height: 400px;" src="view/images/<%=produto.getLinkImagem()%>"/>
	</div>

	<div style="margin-top: 8%; margin-left: 20%">
		<h2 style="color: #173B21;"><%=produto.getNome()%></h2>
		<h4 style="color: #173B21;margin-bottom: 10% "><STRONG><fmt:formatNumber value = "<%=produto.getPreco()%>" type = "currency"/></STRONG></h4>

		<h5><strong style="color: #173B21;">Descrição: </strong><%=produto.getDescricao()%></h5>
		<h5><strong style="color: #173B21;">Cor: </strong><%=produto.getCor()%></h5>
		<%
			double qtdEstoqueDouble = produto.getQtdEstoque();

			int qtdEstoqueInt = (int) qtdEstoqueDouble;

			String numeroAntesVirgula = String.valueOf(qtdEstoqueInt);
		%>
		<h5><strong style="color: #173B21;">Quantidades em estoque: </strong><%=numeroAntesVirgula%></h5>
		<h5><strong style="color: #173B21;">Fabricante: </strong><%=produto.getMarca()%></h5>
		<h5><strong style="color: #173B21;">Referência: </strong><%=produto.getReferencia()%></h5>
		<h5><strong style="color: #173B21;">Status: </strong><%=produto.getQtdEstoque() != null && produto.getQtdEstoque() > 0 ? "Em Estoque" : "Sem Estoque"%></h5>
		<br>
		<% if (produto.getQtdEstoque() == 0 ) { %>
		<div style="display: flex; justify-content: center; align-items: center; height: 100px; border: 2px solid red; padding: 10px; background-color: rgba(255, 0, 0, 0.1);margin-top: 10px;">
			<p style="color: red; margin-top: 1%"><strong>⚠️ Desculpe, este item está temporariamente fora de estoque.</strong></p>
		</div>
		<% } %>

		<% if (produto.getStatus().equals("Inativo") ) { %>
		<div style="display: flex; justify-content: center; align-items: center; height: 100px; border: 2px solid red; padding: 10px; background-color: rgba(255, 0, 0, 0.1);margin-top: 10px;">
			<p style="color: red; margin-top: 1%"><strong>⚠️ Desculpe, este item está temporariamente indisponível.</strong></p>
		</div>
		<% } %>

		<form id="formAdicionarCarrinho" action="${stub }" method="get" novalidate onsubmit="return validarForm()">
			<div class="row d-flex justify-content-space-between">
				<%if(produto.getQtdDisponivelCompra() != null && produto.getQtdDisponivelCompra() > 0 && produto.getStatus().equals("Ativo")){%>
				<div class="row" style="margin-top: 8%">
				<div class="col-md-6">
						<h5><strong style="color: #173B21; margin-top: -1%">Quantidade: </strong></h5>
					</div>
					<div class="col-md-3">
						<input style="margin-left: -110%; margin-top: -8%" class="form-control" type="number" name="quantidade" id="quantidade" min="1" max="<%=produto.getQtdDisponivelCompra()%>" required="true"/>
						<span id="mensagemQuantidade" style="color: red; display: none; margin-left: -110%">Campo obrigatório</span>
					</div>
				</div>
				<%}%>

				<script>
					function validarForm() {
						var quantidade = document.getElementById('quantidade').value;
						if (quantidade === "" || quantidade <= 0) {
							document.getElementById('quantidade').style.borderColor = "red";
							document.getElementById('mensagemQuantidade').style.display = "inline";
							return false;
						}
						return true;
					}
				</script>

				<input type="hidden" name="acao" value="consultar" />
				<input type="hidden" name="acaoCarrinho" value="adicionarItem" />
				<input type="hidden" name="viewHelper" value="AdicionarCarrinhoVH" />
				<input type="hidden" name="id" value="<%=produto.getId()%>"/>

				<%if(produto.getQtdDisponivelCompra() != null && produto.getQtdDisponivelCompra() > 0 && produto.getStatus().equals("Ativo")){%>
				<div class="col-auto d-flex align-items-end" style="margin-top: 10%; margin-left: 20%">
				<button style="background-color: #173B21" class="btn btn-secondary w-20" type="submit" title="Adicionar ao carrinho"
							alt="Adicionar ao carrinho">Adicionar á sacola</button>
				</div>
				<%} %>
			</div>
		</form>
	</div>

</div>
</body>
</html>
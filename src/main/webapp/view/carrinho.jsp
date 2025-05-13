<%@page import="br.com.fatec.goldenfit.model.CarrinhoItem" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List, br.com.fatec.goldenfit.model.Cliente, br.com.fatec.goldenfit.model.Carrinho" %>
<c:url value="/carrinho" var="stub"/>
<c:url value="/controlador" var="pesquisa"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="template-head.jsp"/>
    <title>Carrinho de Compras</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
</head>
<fmt:setLocale value="pt_BR"/>

<%
    Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
%>
<body>
<nav class="navbar" style="background-color: #173B21; height: 60px ">
    <div class="d-flex flex-row mb-3"
         style="justify-content: space-between; align-items: center; margin-top: 5px; margin-left: 30px">
        <div class="d-flex flex-row mb-3" style="justify-content: center; align-items: center;">
            <img src="images/LogoGold.png" style="height: 35px; width: 35px">
            <a class="navbar-brand" href="/GoldenFit_war/view/index" style="color:#ECBD69; margin-right: 15px ">GOLDENFIT</a>
        </div>
        <div class="d-flex flex-row mb-3"
             style="justify-content: center; align-items: center; margin-left: 15%; margin-right: 5%">
            <form id="formConsultaProdutos" class="d-flex" action="${pesquisa}" method="GET" novalidate>
                <input class="form-control me-2" type="search" id="nome" name="nome" placeholder="O que está buscando?" aria-label="Search" style="height: 25px; width: 550px">
                <button class="btn btn-success" type="submit" style="height: 25px; width: 50px; font-size: 12px; position: relative;">
                    <img style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); margin-top: -2%;" src="images/search.svg">
                </button>
                <input type="hidden" name="acao" value="consultar"/>
                <input type="hidden" name="viewHelper" value="BuscarProdutosVH"/>
                <input type="hidden" name="tipoPesquisa" value="filtros"/>
            </form>
        </div>
        <div class="d-flex flex-row mb-3" style="justify-content: center; align-items: center;">
            <img src="images/user.png" style="height: 30px; width: 30px; margin-left: 30px">
            <%
                Cliente cliente = (Cliente) session.getAttribute("clienteLogado");
                if (cliente != null) {
                    String nome = cliente.getNome();
            %>

            <div class="btn-group">
                <button style="color:#ffffff" type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown"
                        aria-expanded="false">
                    Olá, ${sessionScope.clienteLogado.nome}
                </button>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="listaClientes.jsp">Meu perfil</a></li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>
                    <li><a class="dropdown-item"
                           href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarCartaoVH">Meus
                        cartões</a></li>
                    <li><a class="dropdown-item"
                           href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarEnderecoVH">Endereços</a>
                    </li>
                    <li><a class="dropdown-item"
                           href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarPedidosVH">Meus
                        pedidos</a></li>
                    <li><a class="dropdown-item"
                           href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarCuponsVH">Meus cupons</a>
                    </li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>
                    <li><a class="dropdown-item" href="/GoldenFit_war/controlador?acao=Logout">Logout</a></li>
                </ul>
            </div>
            <%
            } else {
            %>
            <a class="navbar-brand" href="login.jsp" style="color:#ffffff ; font-size: 12px; margin-left: 20px">Entre ou
                cadastre-se</a>
            <%
                }
            %>
            <a href="carrinho.jsp">
                <img src="images/shopping-bag.png" style="height: 30px; width: 30px; margin-left: 50px">
            </a>
        </div>
    </div>
</nav>
<div style="background-color: #ECBD69; height: 5px;"></div>

<div class="container" style="margin-top: 5%">
    <h1 style="color:#173B21;text-align: center; font-size: 27px; margin-bottom: 3%"> S A C O L A </h1>
    <div class="card shadow mb-5 pb-5">
        <div class="card-body">
            <h4>Itens selecionados (<fmt:formatNumber value="<%=carrinho != null ? carrinho.getQuantidadeTotal() : 0%>"
                                                      type="number" maxFractionDigits="0"/>)</h4>
            <hr class="my-3">
            <div class="row" style="margin-left: 2%">
                <div class="col">
                    <p class="h5">Produto</p>
                </div>
                <div class="col">
                    <p class="h5">Valor</p>
                </div>
                <div class="col">
                    <p class="h5">Quantidade</p>
                </div>
                <div class="col">
                    <p class="h5">Subtotal</p>
                </div>
                <div class="col">
                    <p class="h5" style="color:#FFF;"></p>
                </div>
            </div>
            <hr class="my-2">
            <%
                if (carrinho != null && carrinho.getItens() != null && !carrinho.getItens().isEmpty()) {
                    for (CarrinhoItem item : carrinho.getItens()) {
            %>
            <div class="row pt-2" style="margin-left: 2%">
                <div class="d-flex align-items-end col ">
                    <p><%=item.getProduto().getNome()%>
                    </p>
                </div>
                <div class="col">
                    <p><fmt:formatNumber value="<%=item.getValorUnitario()%>" type="currency"/></p>
                </div>
                <div class="col">
                    <div class="row">
                        <form class="w-25" id="formDiminuirQuantidade" action="${stub}" novalidate>
                            <input type="hidden" name="acaoCarrinho" value="diminuirQuantidade"/>
                            <input type="hidden" name="produtoId" value="<%=item.getProduto().getId()%>"/>
                            <button class="btn white border w-10" type="submit" title="Diminuir quantidade"
                                    alt="Diminuir quantidade">
                                <i class="fas fa-minus"></i>
                            </button>
                        </form>
                        <input class="form-control w-25" type="text" name="quantidadeItem" id="quantidadeItem"
                               style="background-color: #FFF; text-align: center;"
                               value="<fmt:formatNumber value = "<%=item.getQuantidade()%>" type = "number" minFractionDigits="0"/>"
                               readonly/>
                        <form class="w-25" id="formAumentarQuantidade" action="${stub}" novalidate>
                            <input type="hidden" name="acaoCarrinho" value="aumentarQuantidade"/>
                            <input type="hidden" name="produtoId" value="<%=item.getProduto().getId()%>"/>
                            <button class="btn white border w-10" type="submit" title="Aumentar quantidade"
                                    alt="Aumentar quantidade">
                                <i class="fas fa-plus"></i>
                            </button>
                        </form>
                    </div>
                </div>
                <div class="col">
                    <p><fmt:formatNumber value="<%=item.getValorTotal()%>" type="currency"/></p>
                </div>
                <div class="col">
                    <form id="formRemoverCarinho" action="${stub}" method="get" novalidate>
                        <input type="hidden" name="acaoCarrinho" value="removerItem"/>
                        <input type="hidden" name="produtoId" value="<%=item.getProduto().getId()%>"/>
                        <button class="btn btn-red w-50" type="submit" title="Excluir"
                                alt="Excluir">
                            Excluir
                        </button>
                    </form>
                </div>
            </div>
            <% }
            }%>
        </div>
    </div>
    <div class="row">
        <div class="d-flex flex-row mb-3" style="justify-content: space-between;">
            <h4>Total:</h4>
            <h4><fmt:formatNumber value="<%=carrinho != null ? carrinho.getValorTotal() : 0%>" type="currency"/></h4>
        </div>

        <div style="text-align: center;">
            <div class="d-flex justify-content-center">
                <%if (carrinho != null && carrinho.getItens() != null && !carrinho.getItens().isEmpty()) {%>
                <a href="finalizarPedido" class="btn btn-secondary w-15" type="submit" title="finalizar pedido"
                   alt="finalizar pedido" style="background-color: #173B21; border-color: #173B21; margin-right: 10px">Comprar</a>
                <%}%>

                <a href="<c:url value="/view/index"/>" class="btn btn-secondary w-15"
                   style="background-color: #ECBD69; border-color: #ECBD69;">Continuar comprando</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page
        import="java.util.List, br.com.fatec.goldenfit.model.PedidoItemTroca, br.com.fatec.goldenfit.model.PedidoItem, br.com.fatec.goldenfit.model.Produto" %>
<%@ page import="br.com.fatec.goldenfit.model.Produto" %>
<!DOCTYPE html>
<html>
<head>
    <c:import url="template-head.jsp"/>
    <title>Solicitação de troca</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="view/css/stylesView.css">

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</head>
<%
    List<PedidoItemTroca> itensTroca = (List<PedidoItemTroca>) request.getSession().getAttribute("itensTroca");
%>
<c:url value="/view/solicitarTroca" var="stub"/>
<fmt:setLocale value="pt_BR"/>
<body>
<nav class="navbar" style="background-color: #173B21; height: 60px ">
    <div class="d-flex flex-row mb-3"
         style="justify-content: space-between; align-items: center; margin-top: 5px; margin-left: 30px">
        <div class="d-flex flex-row mb-3" style="justify-content: center; align-items: center;">
            <img src="view/images/LogoGold.png" style="height: 35px; width: 35px">
            <a class="navbar-brand" href="/GoldenFit_war/view/index" style="color:#ECBD69; margin-right: 70px ">GOLDENFIT</a>
        </div>
    </div>
    </div>
</nav>

<div style="background-color: #ECBD69; height: 5px;"></div>

<form id="formSolicitarTroca" action="${stub }" method="post" novalidate>
    <div class="container" style="margin-top: 5%">
        <h1 style="color:#173B21;text-align: center; font-size: 30px; margin-bottom:3%">ITENS DO PEDIDO</h1>
        <div style="background-color: #173B21; height: 5px; margin-bottom: 2%"></div>
        <div class="card-body">
            <h5>Selecione os itens que deseja trocar:</h5>
            <hr class="my-4">
            <div class="row">
                <div class="col">
                    <p class="h5">Nome</p>
                </div>
                <div class="col me-4">
                    <p class="h5">Quantidade no pedido</p>
                </div>
                <div class="col me-4">
                    <p class="h5">Valor unitário</p>
                </div>
                <div class="col me-4">
                    <p class="h5">Subtotal</p>
                </div>
                <div class="col">
                    <p class="h5">Quantidade para troca
                    <p>
                </div>
            </div>
            <%
                if (itensTroca != null && !itensTroca.isEmpty()) {
                    for (PedidoItemTroca itemTroca : itensTroca) {
                        PedidoItem itemPedido = itemTroca.getItem();
                        Produto produto = itemPedido != null ? itemPedido.getProduto() : null;
            %>
            <div class="row pt-2">
                <div class="col">
                    <p><%=produto != null ? produto.getNome() : ""%>
                    </p>
                </div>
                <div class="col me-4">
                    <p><fmt:formatNumber value="<%=itemPedido.getQuantidade()%>"
                                         type="number" maxFractionDigits="0"/></p>
                </div>
                <div class="col me-4">
                    <fmt:formatNumber value="<%=itemPedido.getValorUnitario()%>" type="currency"/>
                </div>
                <div class="col me-4">
                    <fmt:formatNumber value="<%=itemPedido.getValorTotal()%>" type="currency"/>
                </div>
                <div class="col">
                    <input class="form-control" type="number" name="quantidadeTrocaItem<%=itemPedido.getId()%>"
                           id="quantidadeTrocaItem<%=itemPedido.getId()%>" min="0"
                           max="<%=itemPedido.getQuantidadeDisponivelTroca()%>"
                           value="<fmt:formatNumber value ="<%=itemTroca.getQuantidade()%>"
									type = "number" maxFractionDigits="0"/>" required="true"
                           placeholder="Informe a quantidade..."
                           onkeyup="this.value = this.value <= <%=itemTroca.getQuantidade()%> ? parseInt(this.value, 10) : <%=itemTroca.getQuantidade()%> "/>
                </div>
            </div>
            <%
                    }
                }
            %>
            <div class="row mt-4">
                <div class="d-flex justify-content-center" style="margin-top: 5%">
                    <button type="submit" alt="Trocar" title="Trocar" class="btn btn-secondary w-20"
                            style="width:10%; background-color: #173B21; border-color: #173B21; margin-right: 2%">Trocar</button>

                    <a href="/GoldenFit_war/view/detalhePedido.jsp" class="btn btn-secondary w-20"
                       style="width:10%; background-color: #ECBD69; border-color: #ECBD69;">Voltar</a>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>
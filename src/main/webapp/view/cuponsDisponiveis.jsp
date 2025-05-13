<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List, br.com.fatec.goldenfit.model.Cupom" %>
<!DOCTYPE html>
<html>

<head>
    <c:import url="template-head.jsp"/>
    <title>Meus Cupons</title>
</head>
<%
    List<Cupom> cupons = (List<Cupom>) request.getSession().getAttribute("cupons");
%>
<fmt:setLocale value="pt_BR"/>
<body>
<nav class="navbar" style="background-color: #173B21; height: 60px ">
    <div class="d-flex flex-row mb-3"
         style="justify-content: space-between; align-items: center; margin-top: 5px; margin-left: 30px">
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
                    <li><a class="dropdown-item"
                           href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarPedidosVH">Meus
                        pedidos</a></li>
                    <li>
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

<div>
    <div style="background-color: #ECBD69; height: 5px;"></div>
</div>
<div class="container" style="margin-top: 5%">
    <div class="card-body">
        <h1 style="color:#173B21;text-align: center; font-size: 30px; margin-bottom:1%">MEUS CUPONS</h1>
        <div style="background-color: #173B21; height: 5px; margin-bottom: 2%"></div>
        <div class="row" style="margin-left: 7%; margin-bottom: 1%">
            <div class="col">
                <p class="h5">Tipo</p>
            </div>
            <div class="col">
                <p class="h5">Descrição</p>
            </div>
            <div class="col">
                <p class="h5">Código</p>
            </div>
            <div class="col">
                <p class="h5">Valor</p>
            </div>
            <div class="col">
                <p class="h5">Status</p>
            </div>
            <div class="col">
                <p class="h5">Validade</p>
            </div>
        </div>
        <%
            if (cupons != null) {
                for (Cupom cupom : cupons) {
        %>
        <div class="row pt-2" style="margin-left: 7%">
            <div class="d-flex align-items-end col ">
                <p><%=cupom.getTipo() != null ? cupom.getTipo().getDescricao() : "" %>
                </p>
            </div>
            <div class="col">
                <p><%=cupom.getNome()%>
                </p>
            </div>
            <div class="col">
                <p><%=cupom.getCodigo() %>
                </p>
            </div>
            <div class="col">
                <p><fmt:formatNumber value="<%=cupom.getValor() %>" type="currency"/></p>
            </div>
            <div class="col">
                <p><%= cupom.isStatus() ? "Disponível" : "Utilizado" %></p>
            </div>
            <div class="col">
                <p><fmt:formatDate value="<%=cupom.getValidade()%>" pattern="dd/MM/yyyy"/></p>
            </div>
        </div>
        <%
                }
            }
        %>
    </div>
</div>
</body>
</html>
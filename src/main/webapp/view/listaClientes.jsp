<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@ page import="java.util.List,
                 br.com.fatec.goldenfit.model.Cliente,
                 br.com.fatec.goldenfit.model.Cartao,
                 br.com.fatec.goldenfit.model.Endereco,
                 br.com.fatec.goldenfit.model.Cidade,
                 br.com.fatec.goldenfit.model.PedidoItemTroca" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Perfil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
    <link rel="stylesheet" href="view/css/stylesView.css">

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<fmt:setLocale value="pt_BR"/>
<%
    Cliente cliente = (Cliente) request.getSession().getAttribute("clienteLogado");
    List<PedidoItemTroca> itensTrocaNotificar = (List<PedidoItemTroca>) request.getSession().getAttribute("itensTrocaNotificacao");
%>

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
            <a href="carrinho.jsp" style="text-decoration: none;">
                <img src="images/shopping-bag.png" alt="Shopping Bag"
                     style="height: 30px; width: 30px; margin-left: 10px; cursor: pointer;">
            </a>
        </div>
    </div>
</nav>

<div>
    <div style="background-color: #ECBD69; height: 5px;"></div>
    <h3 style="margin-botom: 5%; font-size: 20px" class="ps-3 pt-2">
        Pontuação: <%=cliente != null ? cliente.getScore() : "" %>
    </h3>
    <!--<div class="card-header hidden">
		    <p>Usuário logado: ${sessionScope.clienteLogado.nome} </p>
	    </div>-->
    <div class="container">
        <% if (itensTrocaNotificar != null && !itensTrocaNotificar.isEmpty()) { %>
        <div class="alert alert-success d-flex align-items-center" role="alert">
            <div class="col">
                <h5 class="alert-heading">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                         class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img"
                         aria-label="Warning:">
                        <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                    </svg>
                    Trocas aprovadas!
                </h5>
                <hr>
                <% for (PedidoItemTroca itemTroca : itensTrocaNotificar) { %>
                <div>
                    Pedido <%= itemTroca.getItem().getPedido().getId() %> -
                    Item <%= itemTroca.getItem().getProduto().getNome() %> - <fmt:formatNumber
                        value="<%= itemTroca.getQuantidade() %>" type="number" maxFractionDigits="0"/> unidade(s)
                </div>
                <% } %>
                <a class="btn mt-3 btn-outline-success" href="/GoldenFit_war/view/marcarNotificacaoTrocaComoLida"
                   style="color: #173B21">Marcar como lido</a>
            </div>
        </div>
        <% } %>

        <div style="margin-left: 10%; margin-right: 10%">
            <h1 style="color:#173B21;text-align: center; font-size: 30px; margin-top: 10%; margin-bottom:3%">
                CONFIGURAÇÕES DE CONTA</h1>
            <div style="background-color: #173B21; height: 5px;"></div>
            <div>
                <div class="card-body">
                    <div class="row"></div>
                    <div>
                        <div>
                            <div style="justify-content: space-between" class="d-flex flex-row mb-3">
                                <h2 style="color:#173B21;font-size: 25px;margin-top: 5%;">Dados pessoais</h2>
                                <a style="background-color: #173B21;width:15%;margin-top:5%;margin-right:4%"
                                   class="btn btn-secondary w-20" alt="Alterar dados da conta"
                                   title="Alterar dados da conta"
                                   href="/GoldenFit_war/controlador?acao=consultar&viewHelper=PreparaAlteracaoClienteVH&id=<%=cliente.getId()%>">
                                    Editar
                                </a>
                            </div>
                        </div>

                        <div class="card-body">
                            <div class="row">
                                <div>
                                    <div class="col-12 mb-3">
                                        <div class="row d-flex justify-content-between">
                                            <p style="margin-left: 4%;margin-botom: 4%; font-size: 20px"
                                               class="ps-3 pt-2"><strong>Nome:</strong> <%=cliente.getNomeCompleto()%>
                                            </p>
                                            <p style="margin-left: 4%;margin-botom: 4%; font-size: 20px"
                                               class="ps-3 pt-2"><strong>Número Residencial:</strong>
                                                (<%=cliente.getTelefoneResidencial().getDdd()%>
                                                )<%=cliente.getTelefoneResidencial().getNumero() %>
                                            </p>
                                            <p style="margin-left: 4%;margin-botom: 4%; font-size: 20px"
                                               class="ps-3 pt-2"><strong>Número Celular:</strong>
                                                (<%=cliente.getTelefoneCelular().getDdd()%>
                                                )<%=cliente.getTelefoneCelular().getNumero() %>
                                            </p>
                                            <p style="margin-left: 4%;margin-botom: 4%; font-size: 20px"
                                               class="ps-3 pt-2"><strong>Data de nascimento:</strong> <fmt:formatDate
                                                    value="<%=cliente.getDataNascimento()%>" pattern="dd/MM/yyyy"/></p>
                                            <p style="margin-left: 4%;margin-botom: 4%; font-size: 20px"
                                               class="ps-3 pt-2"><strong>CPF:</strong> <%=cliente.getCpf()%>
                                            </p>
                                            <p style="margin-left: 4%;margin-botom: 4%; font-size: 20px"
                                               class="ps-3 pt-2">
                                                <strong>E-mail:</strong> <%=cliente.getUsuario().getEmail() %>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div>
                        <div>
                            <div style="background-color: #173B21; height: 5px;margin-top: 5%"></div>
                            <div style="justify-content: space-between" class="d-flex flex-row mb-3">
                                <h2 style="color:#173B21;font-size: 25px;margin-top: 5%;">Dados de conta</h2>
                                <a style="background-color: #173B21;width:15%;margin-top:5%;margin-right:4%"
                                   class="btn btn-secondary w-20" alt="Alterar senha" title="Alterar senha"
                                   href="confirmaSenhaAtual.jsp">Editar</a>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div>
                                <div class="col-12 mb-3">
                                    <div class="row d-flex justify-content-between">
                                        <p style="margin-left: 4%;margin-botom: 4%; font-size: 20px" class="ps-3 pt-2">
                                            <strong>E-mail:</strong> <%=cliente.getUsuario().getEmail() %>
                                        </p>
                                        <p style="margin-left: 4%;margin-botom: 4%; font-size: 20px" class="ps-3 pt-2">
                                            <strong>Senha:</strong> ********</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="d-flex justify-content-center align-items-center" style="height: 20vh;">
                    <a href="inativarConta.jsp" class="btn btn-secondary w-26" type="submit" title="Desativar conta"
                       alt="Desativar conta"
                       style="background-color: #173B21; border-color: #173B21; margin-top: 15px; margin-bottom: 20px;">Desativar Conta</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
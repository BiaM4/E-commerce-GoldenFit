<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="br.com.fatec.goldenfit.model.Produto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="br.com.fatec.goldenfit.model.Cliente" %>
<%@ page import="br.com.fatec.goldenfit.model.Categoria" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    List<Produto> produtos = (List<Produto>) request.getAttribute("produtos");
    Map<String, List<Produto>> produtosPorCategoria = new HashMap<>();

// Organizando os produtos por categoria
    for (Produto produto : produtos) {
        // Iterar sobre a lista de categorias do produto
        for (Categoria categoria : produto.getCategoria()) {
            String nomeCategoria = categoria.getNome();
            if (!produtosPorCategoria.containsKey(nomeCategoria)) {
                produtosPorCategoria.put(nomeCategoria, new ArrayList<>());
            }
            produtosPorCategoria.get(nomeCategoria).add(produto);
        }
    }

%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoldenFit</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="css/stylesView.css">

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <style>
        .chatbot-icon-container {
            position: fixed;
            bottom: 50px; /* Ajusta a distância do ícone até a parte inferior da janela */
            right: 50px; /* Ajusta a distância do ícone até a borda direita da janela */
            z-index: 1030;
        }

        .chatbot-icon-circle {
            background-color: #173B21;
            width: 90px; /* Diâmetro do círculo */
            height: 90px; /* Diâmetro do círculo */
            border: 5px #ECBD69; /* Adiciona o contorno dourado */
            border-radius: 50%; /* Torna o elemento um círculo */
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .chatbot-icon {
            height: 70px;
            width: 70px;
        }

        .categoria-container {
            text-align: center;
            margin-top: 30px;
            margin-bottom: 10px;
        }

        .linha-dourada {
            background-color: #ECBD69;
            height: 5px;
            width: 95%;
            margin-bottom: 1%;
            margin-left: auto;
            margin-right: auto;
        }
    </style>
</head>
<fmt:setLocale value="pt_BR"/>
<c:url value="/controlador" var="stub"/>

<body>
<nav class="navbar" style="background-color: #173B21; height: 60px ">
    <div class="d-flex flex-row mb-3" style="justify-content: space-between; align-items: center; margin-top: 5px; margin-left: 30px">
        <div class="d-flex flex-row mb-3" style="justify-content: center; align-items: center;">
            <img src="images/LogoGold.png" style="height: 35px; width: 35px">
            <a class="navbar-brand" href="#" style="color:#ECBD69; margin-right: 15px ">GOLDENFIT</a>
        </div>
        <div class="d-flex flex-row mb-3" style="justify-content: center; align-items: center; margin-left: 15%; margin-right: 5%">
            <form id="formConsultaProdutos" class="d-flex" action="${stub}" method="GET" novalidate>
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
            <a class="navbar-brand" href="login.jsp" style="color:#ffffff ; font-size: 12px; margin-left: 20px">Entre ou cadastre-se</a>
            <%
                }
            %>
            <a href="carrinho.jsp">
                <img src="images/shopping-bag.png" style="height: 30px; width: 30px; margin-left: 50px">
            </a>
        </div>
    </div>
</nav>

<img src="images/banner.png" style="height: 60%; width: 100%; flex: 1px " >

<div style="margin-top: 15px">
    <div class="categoria-container">
        <%
            String[] orderedCategories = {"Ofertas", "Produtos Novos", "Mais Vendidos", "Feminino", "Masculino"};
            for (String category : orderedCategories) {
                List<Produto> produtosDaCategoria = produtosPorCategoria.get(category);
                if (produtosDaCategoria != null && !produtosDaCategoria.isEmpty()) {
        %>
        <h3 style="color:#000000; font-size: 20px; text-align: center;">
            <strong><%= category %></strong>
        </h3>
        <div class="linha-dourada"></div>
        <div class="container" style="margin-left: auto; margin-right: auto;">
            <div class="row">
                <% for (Produto produto : produtosDaCategoria) { %>
                <div class="col-md-4">
                    <div style=" margin-top: 30px; margin-bottom: 50px ">
                        <img src="images/<%=produto.getLinkImagem() %>" style="height: 200px; width: 200px; margin-bottom: 1%">
                        <a href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarProdutoVH&id=<%=produto.getId()%>"
                           style="text-decoration: none;">
                            <div style="background-color: #173B21; height: 90px; width: 200px; padding: 10px; margin-left: 26%">
                                <h3 style="color:#ECBD69; font-size: 14px; text-align: justify;">
                                    <div style="margin-bottom: 5%">
                                        <%=produto.getNome() %>
                                    </div>

                                    <div style="margin-bottom: 5%">
                                        <% if(produto.getQtdEstoque() == 0 || produto.getStatus().equals("Inativo")) { %>
                                        Indisponível
                                        <% } else { %>
                                        <fmt:formatNumber value="<%=produto.getPreco()%>" type="currency"/>
                                        <% } %>
                                    </div>

                                    <div style="background-color: #ECBD69; color: #ffffff; text-align: center; padding: 2px; border-radius: 5px;">
                                        <%=produto.getCor()%>
                                    </div>
                                </h3>
                            </div>
                        </a>
                    </div>
                </div>
                <% } %>
            </div>
        </div>
        <% } } %>
    </div>

    <!-- Ícone do Chatbot -->
    <div class="chatbot-icon-container">
        <div class="chatbot-icon-circle">
            <a href="chatbot.jsp">
                <img class="chatbot-icon" src="images/bate-papo.png" alt="Ícone do Chatbot">
            </a>
        </div>
    </div>
</div>
</body>
</html>

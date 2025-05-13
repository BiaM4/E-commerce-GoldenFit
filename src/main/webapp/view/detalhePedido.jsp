<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List,
                 br.com.fatec.goldenfit.model.Cliente,
                 br.com.fatec.goldenfit.model.Carrinho,
                 br.com.fatec.goldenfit.model.Cupom,
                 br.com.fatec.goldenfit.model.Endereco,
                 br.com.fatec.goldenfit.model.Cartao,
                 br.com.fatec.goldenfit.model.Endereco,
                 br.com.fatec.goldenfit.model.Pedido,
                 br.com.fatec.goldenfit.model.enums.StatusPedidoItem,
                 br.com.fatec.goldenfit.model.enums.StatusPedido,
                 br.com.fatec.goldenfit.model.PedidoItem,
                 br.com.fatec.goldenfit.model.FormaPagamento,
                 br.com.fatec.goldenfit.model.Produto" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.ZoneId" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhe do Pedido</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
    <link rel="stylesheet" href="view/css/stylesView.css">

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<c:url value="/controlador" var="stub"/>

<%
    Pedido pedido = (Pedido) request.getSession().getAttribute("pedido");
    String caminhoRedirecionarCliente = "/view/detalhePedido.jsp";
%>

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
                    <li><a class="dropdown-item"
                           href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarCartaoVH">Meus
                        cartões</a></li>
                    <li><a class="dropdown-item"
                           href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarEnderecoVH">Endereços</a>
                    </li>
                    <li><a class="dropdown-item"
                           href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarPedidosVH">Meus
                        pedidos</a></li>
                    <li>
                    <li><a class="dropdown-item"
                           href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarCuponsVH">Meus cupons</a>
                    </li>
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

<div style="margin-top: 3%; margin-left:10%; margin-right: 10%">
    <h4 style="color: #173B21;"> STATUS: <%=pedido.getStatus().getDescricao()%>
    </h4>
    <div style="margin-top: 3%;">
        <h2 style="color: #173B21; text-align: center">Detalhe do Pedido - #<%=pedido.getId() %>
        </h2>
        <div style="background-color: #ECBD69; height: 5px;"></div>
        <h4 style="color: #173B21;margin-bottom: 3%; margin-top: 3%"><STRONG>Resumo do pedido</STRONG></h4>

        <h5><strong style="color: #173B21;">Valor Total: </strong><fmt:formatNumber
                value="<%=pedido.getValorTotalItens()%>" type="currency"/></h5>
        <h5><strong style="color: #173B21;">Frete: </strong><fmt:formatNumber value="<%=pedido.getValorFrete() %>"
                                                                              type="currency"/></h5>
        <h5><strong style="color: #173B21;">Desconto: </strong><fmt:formatNumber
                value="<%=pedido.getValorTotalDescontos() %>" type="currency"/></h5>
        <h5><strong style="color: #173B21;">Valor Final: </strong><fmt:formatNumber value="<%=pedido.getValorTotal()%>"
                                                                                    type="currency"/></h5>

    </div>

    <div style="margin-top: 3%;">
        <div style="background-color: #ECBD69; height: 5px;"></div>
        <h4 style="color: #173B21;margin-bottom: 3%; margin-top: 3%"><STRONG>Endereço de Entrega</STRONG></h4>

        <h5><strong style="color: #173B21;">Logradouro: </strong> <%=pedido.getEnderecoEntrega().getLogradouro() %>
        </h5>
        <h5><strong style="color: #173B21;">Número: </strong> <%=pedido.getEnderecoEntrega().getNumero() %>
        </h5>
        <h5><strong style="color: #173B21;">Cidade: </strong> <%=pedido.getEnderecoEntrega().getCidade().getNome() %>
        </h5>
        <h5><strong style="color: #173B21;">CEP: </strong><%=pedido.getEnderecoEntrega().getCep() %>
        </h5>
    </div>

    <div style="margin-top: 3%;">
        <div style="background-color: #ECBD69; height: 5px;"></div>
        <h4 style="color: #173B21;margin-bottom: 3%; margin-top: 3%"><STRONG>Forma de Pagamento</STRONG></h4>

        <%
            if (pedido.getFormasPagamento() != null) {
                for (FormaPagamento formaPagamentoCupom : pedido.getFormasPagamento()) {
                    if (formaPagamentoCupom.getCupom() != null && formaPagamentoCupom.getCupom().getId() > 0) {

        %>

        <h5><strong style="color: #173B21;">Tipo: </strong><%=formaPagamentoCupom.getCupom().getTipo() %>
        </h5>
        <h5><strong style="color: #173B21;">Código: </strong><%=formaPagamentoCupom.getCupom().getCodigo() %>
        </h5>
        <h5><strong style="color: #173B21;">Valor: </strong><fmt:formatNumber
                value="<%= formaPagamentoCupom.getCupom().getValor() %>" type="currency"/>
        </h5>
        <br>
        <%
                    }
                }
            }
        %>

        <%
            if (pedido.getFormasPagamento() != null) {
                for (FormaPagamento formaPagamento : pedido.getFormasPagamento()) {
                    if (formaPagamento.getCartao() != null && formaPagamento.getCartao().getId() > 0) {
        %>

        <h5><strong style="color: #173B21;">Bandeira: </strong><%=formaPagamento.getCartao().getBandeira() %>
        </h5>
        <h5><strong style="color: #173B21;">Número: </strong><%=formaPagamento.getCartao().getNumero()%>
        </h5>
        <h5><strong style="color: #173B21;">Nome Impresso: </strong><%=formaPagamento.getCartao().getNome() %>
        </h5>
        <br>
        <%
                    }
                }
            }
        %>
    </div>

    <div style="margin-top: 3%;">
        <div style="background-color: #ECBD69; height: 5px;"></div>
        <h4 style="color: #173B21;margin-bottom: 3%; margin-top: 3%"><STRONG>Itens Comprados</STRONG></h4>

        <%
            boolean trocaAutorizada = false;
            if (pedido.getItens() != null) {
                for (PedidoItem item : pedido.getItens()) {
        %>

        <h5><strong style="color: #173B21;">Produto: </strong><%=item.getProduto() != null ? item.getProduto().getNome() : "" %>
        </h5>
        <h5><strong style="color: #173B21;">Valor: </strong><fmt:formatNumber value="<%=item.getValorUnitario()%>"
                                                                              type="currency"/></h5>
        <h5><strong style="color: #173B21;">Quantidade: </strong><fmt:formatNumber value="<%=item.getQuantidade()%>"
                                                                                   type="number" maxFractionDigits="0"/>
        </h5>

        <%-- Verificando se há quantidade de troca solicitada --%>
        <% Double quantidadeTroca = item.getQuantidadeTroca(); %>
        <% if (quantidadeTroca > 0) { %>
        <h5><strong style="color: #173B21;">Quantidade Solicitada de Troca: </strong><fmt:formatNumber value="<%= quantidadeTroca %>" type="number" maxFractionDigits="0"/></h5>
        <% } %>

        <div class="col-2">
            <p><%=item.getStatus() != null &&
                    (item.getStatus() == StatusPedidoItem.TROCA_SOLICITADA ||
                            item.getStatus() == StatusPedidoItem.TROCA_AUTORIZADA ||
                            item.getStatus() == StatusPedidoItem.TROCA_REALIZADA ||
                            item.getStatus() == StatusPedidoItem.TROCA_REPROVADA) ? item.getStatus().getDescricao() : ""%>
            </p>
        </div>
        <br>
        <%
                    if (item.getStatus() == StatusPedidoItem.TROCA_AUTORIZADA)
                        trocaAutorizada = true;
                }
            } %>

        <%
            if (trocaAutorizada && pedido.getStatus().equals(StatusPedido.TROCA_AUTORIZADA)) {
        %>
        <div style="margin-left: 38%" class="col-3">
            <a class="btn btn-secondary w-100" style="background-color: #173B21; border-color: #173B21"
               alt="Enviar itens p/ troca" title="Enviar itens p/ troca"
               href="/GoldenFit_war/controlador?acao=alterar&viewHelper=AlterarStatusPedidoVH&id=<%=pedido.getId()%>&status=<%=StatusPedido.ITENS_ENVIADOS_PARA_TROCA.name()%>">
                Enviar iten(s) para Troca
            </a>
        </div>
        <%
            }
        %>

        <%if (pedido.getStatus().equals(StatusPedido.EM_TRANSITO)) { %>
        <div style="margin-left: 38%" class="col-3">
            <a class="btn btn-secondary w-100" style="background-color: #173B21; border-color: #173B21"
               alt="Pedido Entregue" title="Pedido Entregue"
               href="/GoldenFit_war/controlador?acao=alterar&viewHelper=AlterarStatusPedidoVH&id=<%=pedido.getId()%>&status=<%=StatusPedido.ENTREGUE.name()%>">
                Pedido Entregue
            </a>
        </div>
    </div>
    <%}%>

    <div class="row mt-4">
        <%
            if (pedido != null && pedido.getStatus().equals(StatusPedido.ENTREGUE) &&
                    !pedido.isPossuiTrocaParcialSolicitada() &&
                    !pedido.isPossuiTrocaParcialRealizada() && !pedido.isPossuiTrocaParcialAutorizada()) {
        %>
        <div style="margin-left: 38%" class="col-3">
            <form id="formSolicitarTroca" action="${stub }" method="post" novalidate>
                <input type="hidden" name="id" value="<%=pedido.getId()%>"/>
                <input type="hidden" name="acao" value="consultar"/>
                <input type="hidden" name="viewHelper" value="PreparaSolicitacaoTrocaVH"/>

                <button style="background-color: #173B21" class="btn btn-secondary w-100" type="submit"
                        title="Solicitar Troca" alt="Solicitar Troca">Solicitar Troca
                </button>
            </form>
        </div>

        <%}%>
    </div>

    <% if (pedido.getItens() != null) {
        for (PedidoItem item : pedido.getItens()) {
            if (pedido.getStatus().equals(StatusPedido.CANCELAMENTO_AUTORIZADO) && item.getStatus().getDescricao().equals("Cancelamento autorizado")) { %>
    <div style="margin-left: 38%" class="col-3">
        <a class="btn btn-secondary w-100" style="background-color: #173B21; border-color: #173B21"
           alt="Enviar itens p/ troca" title="Enviar itens p/ troca"
           href="/GoldenFit_war/controlador?acao=alterar&viewHelper=AlterarStatusPedidoVH&id=<%=pedido.getId()%>&status=<%=StatusPedido.ITENS_DEVOLVIDOS.name()%>">
            Devolver iten(s) para loja
        </a>
    </div>
</div>
<%}%>
<%}%>
<%}%>

<%
    LocalDate dataAtual = LocalDate.now();
    LocalDate dataCadastro = pedido.getDtCadastro().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate dataLimiteCancelamento = dataCadastro.plusDays(7);

    if (pedido.getStatus().equals(StatusPedido.APROVADO)
            || pedido.getStatus().equals(StatusPedido.EM_TRANSITO)
            || (pedido.getStatus().equals(StatusPedido.ENTREGUE) && dataAtual.isBefore(dataLimiteCancelamento))) { %>
<div style="margin-left: 38%; margin-top: 2%" class="col-3">
    <a class="btn btn-secondary w-100" style="background-color: darkred; border-color: darkred"
       alt="Cancelar Pedido" title="Cancelar Pedido"
       href="/GoldenFit_war/controlador?acao=alterar&viewHelper=AlterarStatusPedidoVH&id=<%=pedido.getId()%>&status=<%=StatusPedido.CANCELAMENTO_SOLICITADO.name()%>">
        Cancelar Pedido
    </a>
</div>
</div>
<%}%>

<div class="row mt-4">

    <div style="margin-left: 45%">
        <a style="background-color: #ECBD69; border-color: #ECBD69; width:10%; margin-top:2%; margin-bottom: 5%"
           class="btn btn-secondary w-20" alt="Voltar" title="Voltar"
           href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarPedidosVH">
            Voltar
        </a>
    </div>
</div>
</div>

</div>
</div>
</body>
</html>
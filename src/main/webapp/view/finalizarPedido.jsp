<%@ page import="java.util.List,
                 br.com.fatec.goldenfit.model.Cliente,
                 br.com.fatec.goldenfit.model.Carrinho,
                 br.com.fatec.goldenfit.model.Cupom,
                 br.com.fatec.goldenfit.model.Endereco,
                 br.com.fatec.goldenfit.model.Cartao,
                 br.com.fatec.goldenfit.model.Endereco,
                 br.com.fatec.goldenfit.model.Pedido,
                 br.com.fatec.goldenfit.model.CarrinhoItem" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:url value="/controlador" var="stub"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Finalizar Compra</title>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
    <link rel="stylesheet" href="view/css/stylesView.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
    <link rel="stylesheet" href="view/css/stylesView.css">

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<fmt:setLocale value="pt_BR"/>
<%
    Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
    Pedido pedido = (Pedido) request.getSession().getAttribute("novoPedido");
    List<Cupom> cupons = (List<Cupom>) request.getAttribute("cupons");
    List<Endereco> enderecos = (List<Endereco>) request.getAttribute("enderecos");
    List<Cartao> cartoes = (List<Cartao>) request.getAttribute("cartoes");
    String erroCartao = (String) request.getAttribute("erroCartao");
    String erroCupom = (String) request.getAttribute("erroCupom");
    String erroPedido = (String) request.getAttribute("erroPedido");
    String caminhoRedirecionar = "/view/finalizarPedido.jsp";

    boolean mostrarListagemCartao = true;
    if (pedido.getValorTotal() <= 0) {
        mostrarListagemCartao = false;
    }
%>
<body>
<nav class="navbar" style="background-color: #173B21; height: 60px ">
    <div class="d-flex flex-row mb-3"
         style="justify-content: space-between; align-items: center; margin-top: 5px; margin-left: 30px">
        <div class="d-flex flex-row mb-3" style="justify-content: center; align-items: center;">
            <img src="images/LogoGold.png" style="height: 35px; width: 35px">
            <a class="navbar-brand" href="/GoldenFit_war/view/index" style="color:#ECBD69; margin-right: 15px ">GOLDENFIT</a>
        </div>
        <div class="d-flex flex-row mb-3" style="justify-content: center; align-items: center;margin-left: 190%">
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
            <a href="carrinho.jsp" style="text-decoration: none;">
                <img src="images/shopping-bag.png" alt="Shopping Bag"
                     style="height: 30px; width: 30px; margin-left: 10px; cursor: pointer;">
            </a></div>
    </div>
</nav>
<div style="background-color: #ECBD69; height: 5px;"></div>

<div style="margin-top: 3%; margin-left:10%; margin-right: 10%">
    <div style="margin-top: 3%;">
        <form id="formFinalizarPedido" action="${stub}" novalidate onsubmit="return validarFormulario()">
            <h2 style="color: #173B21; text-align: center">Finalizar Compra</h2>
            <div style="background-color: #ECBD69; height: 5px;"></div>
            <h4 style="color: #173B21;margin-bottom: 5%; margin-top: 3%"><STRONG>Resumo do Pedido</STRONG></h4>

            <h5><strong style="color: #173B21;">Valor total: </strong>
                <fmt:formatNumber value="<%=pedido.getValorTotalItens()%>" type="currency"/>
            </h5>
            <h5><strong style="color: #173B21;">Frete: </strong>
                <fmt:formatNumber value="<%=pedido.getValorFrete() %>" type="currency"/>
            </h5>
            <h5><strong style="color: #173B21;">Desconto: </strong>
                <fmt:formatNumber value="<%=pedido.getValorTotalDescontos() %>" type="currency"/>
            </h5>
            <h5><strong style="color: #173B21;">Valor Final: </strong>
                <fmt:formatNumber
                        value="<%=pedido.getValorTotal() >= 0 ? pedido.getValorTotal() : pedido.getValorTotal() * -1 %>"
                        type="currency"/>
            </h5>


            <div style="margin-top: 3%; margin-bottom: 10%">
                <div style="background-color: #ECBD69; height: 5px;"></div>
                <h4 style="color: #173B21;margin-bottom: 5%; margin-top: 3%"><STRONG>Cupons disponíveis</STRONG></h4>
                <span class="error"><%=erroCupom != null ? erroCupom : ""%></span>
                <div class="row mb-3">
                    <div class="col-2">
                        <h5><strong style="color: #173B21; margin-bottom: 1%;">Tipo</strong></h5>
                    </div>
                    <div class="col-3">
                        <h5><strong style="color: #173B21; margin-bottom: 1%">Código</strong></h5>
                    </div>
                    <div class="col-3">
                        <h5><strong style="color: #173B21; margin-bottom: 1%">Valor Desconto</strong></h5>
                    </div>
                    <div class="col-3"></div>
                </div>

                <% if (cupons != null) { %>
                <% for (Cupom cupom : cupons) { %>
                <% if (cupom.isStatus() && cupom.isValido()) { %>
                <div class="row mb-3">
                    <div class="col-2">
                        <h5 style="color: #173B21;"><%= cupom.getTipo().getDescricao() %></h5>
                    </div>

                    <div class="col-3">
                        <h5><strong style="color: #173B21;"><%= cupom.getCodigo() %></strong></h5>
                    </div>

                    <div class="col-3">
                        <h5 style="color: #173B21;"><fmt:formatNumber value="<%= cupom.getValor() %>"
                                                                      type="currency"/></h5>
                    </div>

                    <div class="col-3">
                        <% if (!cupom.isAplicado()) { %>
                        <a style="background-color: #173B21; width: 25%; margin-top: -4%; margin-right: 5%;"
                           href="/GoldenFit_war/controlador?acao=consultar&viewHelper=AplicarCupomVH&id=<%= cupom.getId() %>"
                           class="btn btn-secondary w-20" alt="Usar Cupom" title="Usar Cupom">Usar</a>
                        <% } else { %>
                        <a style="width: 25%; margin-top: -4%; margin-right: 5%;"
                           class="btn btn-secondary w-20" href="#">Aplicado</a>
                        <% } %>
                    </div>
                </div>
<%--                <% } else { %>--%>
<%--                <div class="row mb-3">--%>
<%--                    <div class="col">--%>
<%--                        <p style="color: red;">O cupom <%= cupom.getCodigo() %> está fora da validade e não pode ser usado.</p>--%>
<%--                    </div>--%>
<%--                </div>--%>
                <% } %>
                <% } %>
                <% } %>

                <div style="margin-top: 3%;">
                    <div style="background-color: #ECBD69; height: 5px;"></div>
                    <h4 style="color: #173B21; margin-bottom: 5%; margin-top: 3%"><strong>Pagamento</strong></h4>
                    <a style="background-color: #173B21;color:#ffffff;width:10%;margin-top: -14%; margin-left: 89%; margin-right:5%"
                       href="cadastraCartaoPedido.jsp" class="btn white" alt="Novo cartão" title="Novo cartão">Adicionar</a>
                    <span class="error"><%=erroCartao != null ? erroCartao : ""%></span>

                    <div class="row mb-3">
                        <div class="col-2">
                            <h5 style="color: #173B21; margin-bottom: 1%;">Bandeira</h5>
                        </div>
                        <div class="col-3">
                            <h5 style="color: #173B21; margin-bottom: 1%;">Número</h5>
                        </div>
                        <div class="col-3">
                            <h5 style="color: #173B21; margin-bottom: 1%;">Nome Impresso</h5>
                        </div>
                    </div>
                    <%if (cartoes != null) { %>
                    <%for (Cartao cartao : cartoes) { %>
                    <div class="row mb-3">
                        <div class="col-2">
                            <h5 style="color: #173B21;"><%= cartao.getBandeira().name() %>
                            </h5>
                        </div>
                        <div class="col-3">
                            <h5 style="color: #173B21;"><%= cartao.getNumero() %>
                            </h5>
                        </div>
                        <div class="col-3">
                            <h5 style="color: #173B21;"><%= cartao.getNome() %>
                            </h5>
                        </div>
                        <div class="col">
                            <% if (!mostrarListagemCartao && pedido.getValorTotal() <= 0) { %>
                            <button style="background-color: lightgray; width: 20%; margin-top: -4%; margin-right: 5%;"
                                    class="btn btn-secondary w-20" disabled>Usar</button>
                            <% } else if (!cartao.isUsado()) { %>
                            <a style="background-color: #173B21; width: 20%; margin-top: -4%; margin-right: 5%;"
                               class="btn btn-secondary w-20"
                               href="/GoldenFit_war/controlador?acao=consultar&viewHelper=UsarCartaoVH&id=<%= cartao.getId() %>"
                               alt="Usar Cartão" title="Usar Cartão">Usar</a>
                            <% } else {
                                if (pedido.getValorTotal() <= 0 || pedido.getQuantidadeCartoesUsados() > 1) { %>
                            <a style="background-color: darkred; width: 20%; margin-top: -4%; margin-right: 5%;"
                               class="btn btn-secondary w-20"
                               href="/GoldenFit_war/controlador?acao=consultar&viewHelper=RemoverCartaoPedidoVH&id=<%= cartao.getId() %>"
                               alt="Remover Cartão" title="Remover Cartão">Remover</a>
                            <% } else { %>
                            <a style="background-color: #173B21; width: 20%; margin-top: -4%; margin-right: 5%;"
                               class="btn btn-secondary w-20" alt="Alterar dados da conta"
                               title="Alterar dados da conta" href="#">Usado</a>
                            <% }
                            } %>
                        </div>
                    </div>
                    <% } %>
                    <% } %>
                </div>
            </div>

            <div>
                <div style="background-color: #ECBD69; height: 5px;"></div>
                <h4 style="color: #173B21;margin-bottom: 5%; margin-top: 3%"><STRONG>Revisar Itens</STRONG></h4>
            </div>

            <div class="d-flex flex-row mb-3" style="justify-content: space-between">
                <div class="col-3">
                    <h5><strong style="color: #173B21;margin-bottom: 1%;">Nome</strong></h5>
                </div>
                <div class="col-2">
                    <h5><strong style="color: #173B21;margin-bottom: 1%;">Valor</strong></h5>
                </div>
                <div class="col-2">
                    <h5><strong style="color: #173B21;margin-bottom: 1%;">Quantidade</strong></h5>
                </div>
                <div class="col-2">
                    <h5><strong style="color: #173B21;margin-bottom: 1%;">Subtotal</strong></h5>
                </div>
                <div class="col-3">
                    <h5><strong style="color: #173B21;margin-bottom: 1%;">Ação</strong></h5>
                </div>
            </div>

            <div style="margin-bottom: 3%"></div>

                <% if(carrinho != null && carrinho.getItens() != null){
        for(CarrinhoItem item : carrinho.getItens()){ %>
            <div class="row mt-2" style="margin-bottom: 3%">
                <div class="col-3">
                    <%= item.getProduto().getNome() %>
                </div>
                <div class="col-2">
                    <fmt:formatNumber value="<%= item.getValorUnitario() %>" type="currency"/>
                </div>
                <div class="col-2">
                    <div class="row">
                        <a class="btn white border w-25" title="Diminuir quantidade"
                           href="/GoldenFit_war/carrinho?acaoCarrinho=diminuirQuantidade&caminhoRedirecionar=/view/finalizarPedido&produtoId=<%= item.getProduto().getId() %>"
                           alt="Diminuir quantidade">
                            <i class="fas fa-minus"></i>
                        </a>
                        <input class="form-control w-25" type="text" name="quantidadeItem" id="quantidadeItem"
                               style="background-color: #FFF; text-align: center;"
                               value="<fmt:formatNumber value="<%= item.getQuantidade() %>" type="number" minFractionDigits="0"/>"
                               readonly/>
                        <a class="btn white border w-25" type="submit" title="Aumentar quantidade"
                           href="/GoldenFit_war/carrinho?acaoCarrinho=aumentarQuantidade&caminhoRedirecionar=/view/finalizarPedido&produtoId=<%= item.getProduto().getId() %>"
                           alt="Aumentar quantidade">
                            <i class="fas fa-plus"></i>
                        </a>
                    </div>
                </div>
                <div class="col-2">
                    <fmt:formatNumber value="<%= item.getValorTotal() %>" type="currency"/>
                </div>
                <div class="col-3">
                    <input type="hidden" name="caminhoRedirecionar" value="/view/finalizarPedido"/>
                    <a style="background-color: darkred; margin-left: -8%" class="btn btn-secondary w-20" type="submit"
                       title="Excluir"
                       href="/GoldenFit_war/carrinho?acaoCarrinho=removerItem&caminhoRedirecionar=/view/finalizarPedido&produtoId=<%= item.getProduto().getId() %>"
                       alt="Excluir">Excluir
                    </a>
                </div>
            </div>
                <%}%>
                <%}%>

            <div>
                <div style="background-color: #ECBD69; height: 5px;"></div>
                <h4 style="color: #173B21;margin-bottom: 5%; margin-top: 3%"><STRONG>Endereço de Entrega</STRONG></h4>
                <a style="background-color: #173B21;color:#ffffff;width:10%;margin-top: -14%; margin-left: 89%; margin-right:5%"
                   href="cadastraEnderecoPedido.jsp" class="btn white" alt="Novo endereço" title="Novo endereço">Adicionar</a>
            </div>
            <div class="row mt-2">
                <div class="col">
                    <select class="form-control" name="enderecoEntrega" id="enderecoEntrega" required="true"
                            style="margin-bottom: 3%">
                        <option value="">Escolha o endereco...</option>
                        <% for (Endereco endereco : enderecos) { %>
                        <option value="<%=endereco.getId()%>"><%=endereco.getLabel()%>
                        </option>
                        <% } %>
                    </select>
                </div>
            </div>

            <div>
                <div style="background-color: #ECBD69; height: 5px;"></div>
                <h4 style="color: #173B21;margin-bottom: 5%; margin-top: 3%"><STRONG>Endereço de Cobrança</STRONG></h4>
                <a style="background-color: #173B21;color:#ffffff;width:10%;margin-top: -14%; margin-left: 89%; margin-right:5%"
                   href="cadastraEnderecoPedido.jsp" class="btn white" alt="Novo endereço" title="Novo endereço">Adicionar</a>
            </div>
            <div class="row mt-2">
                <div class="col">
                    <%if (enderecos != null) {%>
                    <select class="form-control" name="enderecoCobranca" id="enderecoCobranca" required="true"
                            style="margin-bottom: 3%">
                        <option value="">Escolha o endereco...</option>
                        <% for (Endereco endereco : enderecos) { %>
                        <option value="<%=endereco.getId()%>"><%=endereco.getLabel()%>
                        </option>
                        <% } %>
                    </select>
                    <% } %>
                </div>
            </div>

            <input type="hidden" name="acao" value="salvar"/>
            <input type="hidden" name="viewHelper" value="CadastroPedidoVH"/>

            <button style="background-color: #173B21;width:20%;margin-top:3%;margin-bottom: 5%;margin-left: 40%"
                    class="btn btn-secondary w-20" type="submit" Alt="Finalizar compra" title="Finalizar compra">
                Finalizar compra
            </button>
    </div>
    </form>
</div>
</body>
</html>

<script>
    function validarFormulario() {
        var enderecoEntrega = document.getElementById("enderecoEntrega").value;
        var enderecoCobranca = document.getElementById("enderecoCobranca").value;

        if (enderecoEntrega === "" && enderecoCobranca === "") {
            alert("Por favor, selecione os endereços de entrega e cobrança.");
            return false;
        }

        if (enderecoEntrega === "") {
            alert("Por favor, selecione o endereço de entrega.");
            return false;
        }

        if (enderecoCobranca === "") {
            alert("Por favor, selecione o endereço de cobrança.");
            return false;
        }

        return true;
    }
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@page import="br.com.fatec.goldenfit.model.enums.StatusPedidoItem" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List,
                 br.com.fatec.goldenfit.model.Cliente,
                 br.com.fatec.goldenfit.model.Carrinho,
                 br.com.fatec.goldenfit.model.Cupom,
                 br.com.fatec.goldenfit.model.Endereco,
                 br.com.fatec.goldenfit.model.Cartao,
                 br.com.fatec.goldenfit.model.FormaPagamento,
                 br.com.fatec.goldenfit.model.PedidoItem,
                 br.com.fatec.goldenfit.model.enums.StatusPedido,
                 br.com.fatec.goldenfit.model.Endereco,
                 br.com.fatec.goldenfit.model.Pedido,
                 br.com.fatec.goldenfit.model.PedidoItemTroca" %>

<!DOCTYPE html>
<html lang="pt-br">
<c:import url="template-head-admin.jsp"/>
<c:url value="/view/gerenciamentoStatusPedido" var="stub"/>
<fmt:setLocale value="pt_BR"/>
<%
    Pedido pedido = (Pedido) request.getSession().getAttribute("pedidoAdmin");
    List<StatusPedido> listaStatus = StatusPedido.getTiposStatus();
%>

<body>
<c:import url="template-header-admin.jsp"/>
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
        <h4 style="color: #173B21;margin-bottom: 3%; margin-top: 3%"><STRONG>Dados do Cliente</STRONG></h4>

        <h5><strong style="color: #173B21;">Nome: </strong><%=pedido.getCliente().getNomeCompleto()%>
        </h5>
        <h5><strong style="color: #173B21;">E-mail: </strong><%=pedido.getCliente().getUsuario().getEmail() %>
        </h5>
        <h5><strong
                style="color: #173B21;">Score: </strong><%=pedido.getCliente().getScore() != null ? pedido.getCliente().getScore() : "" %>
        </h5>
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
        <h4 style="color: #173B21;margin-bottom: 3%; margin-top: 3%"><STRONG>Endereço de Cobrança</STRONG></h4>

        <h5><strong style="color: #173B21;">Logradouro: </strong><%=pedido.getEnderecoCobranca().getLogradouro() %>
        </h5>
        <h5><strong style="color: #173B21;">Número: </strong><%=pedido.getEnderecoCobranca().getNumero() %>
        </h5>
        <h5><strong style="color: #173B21;">Cidade: </strong><%=pedido.getEnderecoCobranca().getCidade().getNome() %>
        </h5>
        <h5><strong style="color: #173B21;">CEP: </strong><%=pedido.getEnderecoCobranca().getCep() %>
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
            if (pedido.getItens() != null) {
                for (PedidoItem item : pedido.getItens()) {
        %>

        <h5><strong
                style="color: #173B21;">Produto: </strong><%=item.getProduto() != null ? item.getProduto().getNome() : "" %>
        </h5>
        <h5><strong style="color: #173B21;">Valor: </strong><fmt:formatNumber value="<%=item.getValorUnitario()%>"
                                                                              type="currency"/></h5>
        <h5><strong style="color: #173B21;">Quantidade: </strong><fmt:formatNumber value="<%=item.getQuantidade()%>"
                                                                                   type="number" maxFractionDigits="0"/>
        </h5>

        <%-- Verificando se há quantidade de troca solicitada --%>
        <% Double quantidadeTroca = item.getQuantidadeTroca(); %>
        <% if (quantidadeTroca > 0) { %>
        <h5><strong style="color: #173B21;">Quantidade Solicitada de Troca: </strong><fmt:formatNumber
                value="<%= quantidadeTroca %>" type="number" maxFractionDigits="0"/></h5>
        <% } %>
        <div class="col-2">
            <p><%
                if (item.getStatus() != null &&
                        (pedido.getStatus() == StatusPedido.ENTREGUE || pedido.getStatus() == StatusPedido.TROCA_AUTORIZADA) &&
                        (item.getStatus() == StatusPedidoItem.TROCA_SOLICITADA ||
                                item.getStatus() == StatusPedidoItem.TROCA_AUTORIZADA ||
                                item.getStatus() == StatusPedidoItem.TROCA_REALIZADA ||
                                item.getStatus() == StatusPedidoItem.TROCA_REPROVADA)) {
            %>
                <%=item.getStatus().getDescricao()%>
                <%}%></p>
        </div>
        <br>

        <% }
        }%>

        <%
            if ((pedido.getStatus() != StatusPedido.REPROVADO
                    && pedido.getStatus() != StatusPedido.ENTREGUE
                    && pedido.getStatus() != StatusPedido.TROCA_REPROVADA
                    && pedido.getStatus() != StatusPedido.TROCA_REALIZADA
                    && pedido.getStatus() != StatusPedido.PEDIDO_CANCELADO
                    && pedido.getStatus() != StatusPedido.CANCELAMENTO_AUTORIZADO
                    && pedido.getStatus() != StatusPedido.TROCA_AUTORIZADA
//                        && pedido.getStatus() != StatusPedido.CANCELAMENTO_REPROVADO
                    && !pedido.isPossuiTrocaParcialRealizada())
                    || pedido.isPossuiTrocaParcialSolicitada()
                    || pedido.isPossuiTrocaParcialAutorizada()
            ) {
        %>
    </div>

    <div style="margin-top: 3%;">
        <div style="background-color: #ECBD69; height: 5px;"></div>
        <h2 style="color: #173B21; text-align: center; margin-top: 3%">Gerenciamento do Pedido</h2>

        <form id="statusForm" method="post" action="${stub}">
            <div class="card-body">
                <div class="col mt-4">
                    <%
                        for (StatusPedido status : listaStatus) {
                            if (status != StatusPedido.EM_PROCESSAMENTO) {
                                if ((pedido.getStatus() == StatusPedido.EM_PROCESSAMENTO && (status == StatusPedido.APROVADO || status == StatusPedido.REPROVADO))
                                        || (pedido.getStatus() == StatusPedido.APROVADO && status == StatusPedido.EM_TRANSITO)
                                        || (pedido.getStatus() == StatusPedido.EM_TRANSITO && status == StatusPedido.ENTREGUE)
                                        || ((pedido.getStatus() == StatusPedido.TROCA_SOLICITADA || pedido.isPossuiTrocaParcialSolicitada()) && (status == StatusPedido.TROCA_AUTORIZADA || status == StatusPedido.TROCA_REPROVADA))
                                        || ((pedido.getStatus() == StatusPedido.ITENS_ENVIADOS_PARA_TROCA || pedido.isPossuiTrocaParcialAutorizada()) && status == StatusPedido.TROCA_REALIZADA)
                                        || ((pedido.getStatus() == StatusPedido.ITENS_ENVIADOS_PARA_TROCA && pedido.isPossuiTrocaParcialAutorizada()) && status == StatusPedido.TROCA_REALIZADA) //era pedido.getStatus() == StatusPedido.ITENS_ENVIADOS_PARA_TROCA || pedido.isPossuiTrocaParcialAutorizada()
                                        || ((pedido.getStatus() == StatusPedido.CANCELAMENTO_SOLICITADO)) && (status == StatusPedido.CANCELAMENTO_AUTORIZADO || status == StatusPedido.CANCELAMENTO_REPROVADO)
                                        || ((pedido.getStatus() == StatusPedido.ITENS_DEVOLVIDOS) && (status == StatusPedido.PEDIDO_CANCELADO))
                                        || ((pedido.getStatus() == StatusPedido.CANCELAMENTO_REPROVADO) && (status == StatusPedido.EM_TRANSITO))) {
                    %>
                    <div class="form-check">
                        <%--@declare id="flexradiodefault1"--%><input class="form-check-input" type="radio"
                                                                      value="<%=status.name()%>" name="status"
                                                                      id="<%=status.name()%>"
                        <%=status == pedido.getStatus() ? "checked" : "" %>>
                        <label class="form-check-label" for="flexRadioDefault1" style="font-size: 20px">
                            <%=status.getDescricao()%>
                        </label>
                    </div>
                    <%
                                }
                            }
                        }
                    %>
                </div>
                <input type="hidden" name="id" value="<%=pedido.getId()%>"/>
            </div>
            <div style="margin-left: 47.5%">
                <button class="btn btn-blue w-20" type="submit" id="salvar" name="salvar" title="Salvar"
                        style="background-color: #173B21; border-color: #173B21" alt="Salvar">Salvar
                </button>
            </div>
        </form>
    </div>
    <%}%>

    <a style="background-color: #ECBD69; border-color: #ECBD69; width:10%; margin-top:3%; margin-bottom: 5%; margin-left: 45%"
       class="btn btn-secondary w-20" alt="Voltar" title="Voltar"
       href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarPedidosAdminVH">
        Voltar
    </a>
</div>
</body>
</html>


<script>
    document.getElementById('statusForm').onsubmit = function (event) {
        var radios = document.getElementsByName('status');
        var formValid = false;

        for (var i = 0; i < radios.length; i++) {
            if (radios[i].checked) {
                formValid = true;
                break;
            }
        }

        if (!formValid) {
            alert('Por favor, selecione um status.');
            event.preventDefault();
        }
    };
</script>



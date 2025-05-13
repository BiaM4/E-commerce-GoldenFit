<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List, br.com.fatec.goldenfit.model.Pedido,br.com.fatec.goldenfit.model.Cliente" %>

<!DOCTYPE html>
<html lang="pt-br">
<c:import url="template-head-admin.jsp"/>
<%
    List<Pedido> pedidos = (List<Pedido>) request.getSession().getAttribute("pedidos");
    String nomeCliente = (String) request.getSession().getAttribute("nomeCliente");
    String idCliente = (String) request.getSession().getAttribute("idCliente");
    String caminhoRedirecionar = "/view/detalharPedido.jsp";
%>
<body>
<c:import url="template-header-admin.jsp"/>
<fmt:setLocale value="pt_BR"/>
<div class="container" style="margin-top: 5%">
    <h1 style="color:#173B21;text-align: center; font-size: 27px; margin-bottom: 3%"> HISTÓRICO DE PEDIDOS </h1>
    <div class="card shadow mb-5 pb-4">
        <div class="card-body">
            <div class="row">
                <div class="col-8">
                    <p class="h3"><%=nomeCliente%>
                    </p>
                </div>
                <div class="col-2" STYLE="margin-left: 15%">
                    <p class="h3">Código: <%=idCliente%>
                    </p>
                </div>
            </div>
            <hr class="my-4">
            <div class="row mt-4">
                <div class="col">
                    <p class="h5">Número do pedido</p>
                </div>
                <div class="col">
                    <p class="h5">Valor</p>
                </div>
                <div class="col">
                    <p class="h5">Status</p>
                </div>
                <div class="col">
                    <p class="h5">Data</p>
                </div>
                <div class="col-2">
                    <p class="h5" style="color: #FFF;"></p>
                </div>
            </div>


            <hr class="my-4">
            <%
                if (pedidos != null && !pedidos.isEmpty()) {
                    for (Pedido pedido : pedidos) {
            %>
            <div class="row mb-4">
                <div class="col">
                    <p><%=pedido.getId()%>
                    </p>
                </div>
                <div class="col">
                    <p><fmt:formatNumber value="<%=pedido.getValorTotal()%>" type="currency"/></p>
                    </select>
                </div>
                <div class="col">
                    <p><%=pedido.getStatus().getDescricao()%>
                    </p>
                </div>
                <div class="col">
                    <p><fmt:formatDate value="<%=pedido.getDtCadastro()%>" pattern="dd/MM/yyyy"/></p>
                </div>

                <div class="col-2">
                    <a href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarPedidoAdminVH&id=<%=pedido.getId()%>"
                       class="btn btn-secondary w-26" type="submit" title="Visualizar pedidos" alt="Visualizar pedidos"
                       style="background-color: #173B21; border-color: #173B21">Visualizar</a>
                </div>
            </div>
            <%
                    }
                }
            %>
        </div>
    </div>
    <div class="col-2" style="margin-left: 47%">
        <a class="btn btn-secondary w-40" href="/GoldenFit_war/view/consultarClientes.jsp" style="background-color: #ECBD69; border-color: #ECBD69" title="Voltar">Voltar</a>
    </div>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List,br.com.fatec.goldenfit.model.Pedido, br.com.fatec.goldenfit.model.enums.StatusPedido" %>
<!DOCTYPE html>
<html lang="pt-br">
<c:import url="template-head-admin.jsp" />
<fmt:setLocale value="pt_BR" />
<c:url value="/controlador" var="stub" />
<%
    List<Pedido> pedidos = (List<Pedido>) request.getSession().getAttribute("pedidosAdmin");
    List<StatusPedido> statusPedido = StatusPedido.getTiposStatus();
    String caminhoRedirecionar = "/view/detalharPedido.jsp";

%>
<body>
<c:import url="template-header-admin.jsp" />
<div class="container" style="margin-top: 5%">
    <h1 style="color:#173B21;text-align: center; font-size: 27px; margin-bottom: 3%">CONSULTAR PEDIDOS</h1>
    <div class="card shadow mb-5 pb-4">
        <div class="card-body">
            <div class="row mt-4">
                <div class="col-1">
                    <p class="h5">Código</p>
                </div>
                <div class="col-2">
                    <p class="h5">Data</p>
                </div>
                <div class="col">
                    <p class="h5">Email</p>
                </div>
                <div class="col-2">
                    <p class="h5">CPF</p>
                </div>
                <div class="col-1">
                    <p class="h5">Total</p>
                </div>
                <div class="col">
                    <p class="h5">Status</p>
                </div>
                <div class="col-2">
                    <p class="h5" style="color: #FFF;"></p>
                </div>
            </div>

            <form action="${stub}" method="get" id="formFiltrosPedidos" novalidate>
                <div class="row mt-2 mb-4">
                    <div class="col-1">
                        <input class="form-control" type="number" name="id"	id="id" min="0"
                               onkeyup="this.value = this.value != null && this.value != undefined ? parseInt(this.value, 10) : null "/>
                    </div>
                    <div class="col-2">
                        <input class="form-control" type="date" id="dtCadastro" name="dtCadastro" />
                    </div>
                    <div class="col">
                        <input class="form-control" type="email" id="email" name="email" />
                    </div>
                    <div class="col-2">
                        <input class="form-control" type="text" id="cpf" name="cpf" />
                    </div>
                    <div class="col-1">
                        <input class="form-control" type="number" id="valorTotal" name="valorTotal" min="0" />
                    </div>
                    <div class="col">
                        <select class="form-control" name="status" id="status" required="true">
                            <option value="">Escolha...</option>
                            <%if(statusPedido != null){
                                for(StatusPedido status : statusPedido){%>
                            <option value="<%=status.name()%>"> <%=status.getDescricao() %> </option>
                            <%}
                            } %>
                        </select>
                    </div>
                    <input type="hidden" name="acao" value="consultar" />
                    <input type="hidden" name="viewHelper" value="ConsultarPedidosAdminVH" />
                    <input type="hidden" name="tipoPesquisa" value="filtros"/>
                    <div class="col-2">
                        <button class="btn btn-blue w-100 pb-2" id="consultar" name="consultar" type="submit"
                                style="background-color: #173B21; border-color: #173B21" title="Consultar" alt="Consultar">Consultar</button>
                    </div>
                </div>
            </form>

            <hr class="my-4">
            <%if(pedidos != null){
                for(Pedido pedido : pedidos){%>
            <form action="${stub}" method="post" novalidate>
                <div class="row mb-4">
                    <div class="col-1">
                        <p><%=pedido.getId()%></p>
                    </div>
                    <div class="col-2">
                        <p>
                            <fmt:formatDate value="<%=pedido.getDtCadastro()%>" pattern="dd/MM/yyyy" />
                        </p>
                    </div>

                    <div class="col">
                        <p><%=pedido.getCliente().getUsuario().getEmail() %></p>
                    </div>


                    <div class="col-2">
                        <p><%=pedido.getCliente().getCpf() %></p>
                    </div>

                    <div class="col-1">
                        <p>
                            <fmt:formatNumber value="<%=pedido.getValorTotal() %>" type="currency" />
                        </p>
                    </div>
                    <div class="col">
                        <p><%=pedido.getStatus() != null ? pedido.getStatus().getDescricao() : ""%></p>
                    </div>
                    <div class="col-2">
                        <a class="btn btn-blue w-100" id="Visualizar Pedido" style="background-color: #ECBD69; border-color: #ECBD69"
                           href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarPedidoAdminVH&id=<%=pedido.getId()%>"
                           title="Visualizar Pedido" alt="Visualizar Pedido">Visualizar</a>
                    </div>
                </div>
            </form>
            <%}
            } %>
        </div>
    </div>
    <div class="col-2" style="margin-left: 47%">
        <a class="btn btn-secondary w-40" href="painelAdmin.jsp" style="background-color: #ECBD69; border-color: #ECBD69" title="Voltar">Voltar</a>
    </div>
</div>
</body>
</html>
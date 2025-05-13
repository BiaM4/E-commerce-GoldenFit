<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page
        import="java.util.List, br.com.fatec.goldenfit.model.MovimentacaoEstoque, br.com.fatec.goldenfit.model.enums.TipoMovimentacao, br.com.fatec.goldenfit.model.Produto" %>
<%@ page import="br.com.fatec.goldenfit.model.Produto" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <style>
        .required-label::after {
            content: "*";
            color: red;
        }

        .error-message {
            color: red;
        }

        .invalid-input {
            border-color: red !important;
        }
    </style>
</head>
<c:import url="template-head-admin.jsp"/>
<fmt:setLocale value="pt_BR"/>
<c:url value="/controlador" var="stub"/>
<%
    List<MovimentacaoEstoque> movimentacoes = (List<MovimentacaoEstoque>) request.getSession().getAttribute("movimentacoes");
    List<TipoMovimentacao> tipos = TipoMovimentacao.getTiposMovimentacao();
    List<Produto> produtos = (List<Produto>) request.getSession().getAttribute("produtos");
%>

<body>
<c:import url="template-header-admin.jsp"/>
<div class="container" style="margin-top: 5%">
    <h1 style="color:#173B21;text-align: center; font-size: 30px; margin-bottom:3%">CONSULTAR MOVIMENTAÇÕES</h1>
    <div class="card-body">
        <div class="row mt-4">
            <div class="col">
                <p class="h5 ">Data</p>
            </div>
            <div class="col">
                <p class="h5">Tipo</p>
            </div>
            <div class="col-2">
                <p class="h5">Produto</p>
            </div>
            <div class="col">
                <p class="h5">Quantidade</p>
            </div>
            <div class="col">
                <p class="h5">Fornecedor</p>
            </div>
            <div class="col">
                <p class="h5">Preço de custo</p>
            </div>
            <div class="col-2">
                <p class="h5" style="color: #FFF;"></p>
            </div>
        </div>

        <form id="formConsultarFiltros" action="${stub}" method="post" novalidate>
            <div class="row mt-2 mb-4">
                <div class="col">
                    <input class="form-control ps-1 pe-1" type="date" id="data" name="data" style="width: 150px;"/>
                </div>
                <div class="col">
                    <select class="form-control" name="tipo" id="tipo" required="true">
                        <option value="">Escolha...</option>
                        <%
                            if (tipos != null) {
                                for (TipoMovimentacao tipo : tipos) {
                        %>
                        <option value="<%=tipo.name()%>"><%=tipo.getDescricao() %>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                    <span class="error-message" id="tipoError"></span>
                </div>
                <div class="col-2">
                    <input class="form-control" type="text" id="produto" name="produto"/>
                </div>
                <div class="col">
                    <input class="form-control" type="number" id="quantidade" name="quantidade"/>
                </div>
                <div class="col">
                    <input class="form-control" type="text" id="fornecedor" name="fornecedor"/>
                </div>
                <div class="col">
                    <input class="form-control" type="text" id="precoCusto" name="precoCusto"/>
                </div>
                <input type="hidden" name="acao" value="consultar"/>
                <input type="hidden" name="viewHelper" value="ConsultarMovimentacoesVH"/>
                <input type="hidden" name="tipoPesquisa" value="filtros"/>
                <div class="col-2">
                    <button type="submit" class="btn btn-secondary w-100 text-white" alt="Consultar"
                            title="Consultar"
                            style="background-color: #173B21; border-color: #173B21;">
                        Consultar
                    </button>
                </div>
            </div>
        </form>

        <hr class="my-4">
        <% if (movimentacoes != null && !movimentacoes.isEmpty()) { %>
        <% for (MovimentacaoEstoque mov : movimentacoes) { %>
        <form action="${stub}" method="post" novalidate>
            <div class="row mb-4">
                <div class="col">
                    <p>
                        <fmt:formatDate value="<%=mov.getData()%>" pattern="dd/MM/yyyy"/>
                    </p>
                </div>
                <div class="col">
                    <p><%=mov.getTipo().getDescricao()%>
                    </p>
                </div>
                <div class="col-2">
                    <p><%=mov.getProduto().getNome()%>
                    </p>
                </div>
                <div class="col">
                    <fmt:formatNumber value="<%=mov.getQuantidade()%>" type="number" maxFractionDigits="0"/>
                </div>
                <div class="col">
                    <p><%=mov.getFornecedor()%>
                    </p>
                </div>
                <div class="col-2">
                    <p>
                        <fmt:formatNumber value="<%=mov.getPrecoCusto()%>" type="currency"/>
                    </p>
                </div>
                <div class="col">
                </div>
            </div>
        </form>
        <% } %>
        <% } else { %>
        <div class="row">
            <div class="col">
                <p class="h5">Nenhum resultado encontrado</p>
            </div>
        </div>
        <% } %>
    </div>
</div>
</body>
</html>

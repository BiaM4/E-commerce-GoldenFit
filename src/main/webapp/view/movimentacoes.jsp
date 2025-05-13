<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List, br.com.fatec.goldenfit.model.Produto, br.com.fatec.goldenfit.model.enums.TipoMovimentacao " %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <style>
        .error-message {
            color: red;
            font-size: 12px;
            margin-top: 4px;
        }

        .required-label::after {
            content: " *";
            color: red;
        }

        .error-input {
            border: 1px solid red;
        }

        .campo-invalido {
            border: 1px solid red;
        }
    </style>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="view/css/stylesView.css">

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>

<c:import url="template-head-admin.jsp"/>
<c:url value="/controlador" var="stub"/>
<%
    List<Produto> produtos = (List<Produto>) request.getSession().getAttribute("produtos");

    System.out.println(produtos);
    List<TipoMovimentacao> tipos = TipoMovimentacao.getTiposMovimentacao();
%>

<body>
<c:import url="template-header-admin.jsp"/>
<script src="javascript/validacaoFormulario/validaFormCadastroMovimentacao.js" charset="UTF-8"></script>
<div class="container" style="margin-top: 5%">
    <h1 style="color:#173B21;text-align: center; font-size: 30px; margin-bottom:3%">MOVIMENTAÇÕES NO ESTOQUE</h1>
    <div class="card shadow mb-5 pb-4">
        <div class="card-body">
            <form id="formCadastroMovimentacao" action="${stub}" method="post" novalidate onsubmit="return validarFormulario()">
                <div class="row pt-2 mb-2 d-flex align-items-center" style="margin-left: 2%">
                    <div class="row mt-4">
                        <div class="row">
                            <div class="col-4">
                                <p class="h5 required-label">Data</p>
                            </div>
                            <div class="col-4">
                                <p class="h5 required-label">Tipo de movimentação</p>
                            </div>
                            <div class="col-4">
                                <p class="h5 required-label">Produto</p>
                            </div>
                        </div>


                        <div class="row d-flex align-items-center">
                            <div class="col-4">
                                <input class="form-control mt-2" type="date" id="data" name="data"
                                       required="true"/>
                            </div>
                            <div class="col-4 mt-2">
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
                            </div>


                            <div class="col-4 mt-2	">
                                <select class="form-control" name="produto" id="produto" required="true">
                                    <option value="">Escolha...</option>
                                    <%
                                        if (produtos != null) {
                                            for (Produto produto : produtos) {
                                    %>
                                    <option value="<%=produto.getId()%>">
                                        <%=produto.getId() + " - " + produto.getNome() %>
                                    </option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </div>


                        </div>
                    </div>


                    <div class="row mt-4">
                        <div class="row">
                            <div class="col-4">
                                <p class="h5 required-label">Quantidade</p>
                            </div>
                            <div class="col-4">
                                <p class="h5 required-label">Fornecedor</p>
                            </div>
                            <div class="col-4">
                                <p class="h5 required-label">Preço de custo</p>
                            </div>
                        </div>


                        <div class="row">
                            <div class="col-4">
                                <input class="form-control mt-2" type="number" id="quantidade" min="1" name="quantidade"
                                       required="true"/>
                            </div>
                            <div class="col-4">
                                <input class="form-control mt-2" type="text" id="fornecedor" name="fornecedor"
                                       required="true"/>
                            </div>
                            <div class="col-4">
                                <input class="form-control mt-2" type="number" id="precoCusto" name="precoCusto"
                                       required="true"/>
                            </div>
                        </div>
                    </div>

                    <input type="hidden" name="acao" value="salvar"/>
                    <input type="hidden" name="viewHelper" value="CadastroMovimentacaoVH"/>

                    <div class="row mt-4 d-flex justify-content-center">
                        <div class="col-2">
                            <button type="submit" class="btn btn-secondary w-100 text-white" alt="Salvar"
                                    title="Salvar"
                                    style="background-color: #173B21; border-color: #173B21; margin-top: 4%">
                                Salvar
                            </button>
                        </div>
                    </div>

                </div>
            </form>
        </div>
    </div>

    <div class="row mt-4">
        <div class="d-flex justify-content-center" style="margin-top: 5%">
            <a href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarMovimentacoesVH&id="
               class="btn btn-secondary w-20" id="consultar" alt="Consultar" title="Consultar"
               style="width:10%;background-color: #173B21; border-color: #173B21; margin-left: 1%">Consultar</a>
        </div>
    </div>
</div>
</body>
</html>

<script>
    function validarFormulario() {
        var data = document.getElementById("data").value.trim();
        var tipo = document.getElementById("tipo").value.trim();
        var produto = document.getElementById("produto").value.trim();
        var quantidade = document.getElementById("quantidade").value.trim();
        var fornecedor = document.getElementById("fornecedor").value.trim();
        var precoCusto = document.getElementById("precoCusto").value.trim();
        var formularioValido = true;

        // Validar se a quantidade é maior que zero
        if (quantidade <= 0) {
            var campoQtdEstoque = document.getElementById("quantidade");
            campoQtdEstoque.classList.add("campo-invalido");
            formularioValido = false;

            // Verifica se já existe uma mensagem de erro para este campo
            var mensagemErroQtdEstoque = campoQtdEstoque.parentNode.querySelector(".mensagem-erro-estoque");
            if (!mensagemErroQtdEstoque) {
                mensagemErroQtdEstoque = document.createElement("div");
                mensagemErroQtdEstoque.textContent = "A quantidade deve ser maior que zero";
                mensagemErroQtdEstoque.classList.add("mensagem-erro-estoque");
                mensagemErroQtdEstoque.style.color = "red";
                campoQtdEstoque.parentNode.appendChild(mensagemErroQtdEstoque);
            }
        } else {
            var campoQtdEstoque = document.getElementById("quantidade");
            campoQtdEstoque.classList.remove("campo-invalido");
            var mensagemErroQtdEstoque = campoQtdEstoque.parentNode.querySelector(".mensagem-erro-estoque");
            if (mensagemErroQtdEstoque) {
                // Remove a mensagem de erro se a quantidade em estoque for válida
                campoQtdEstoque.parentNode.removeChild(mensagemErroQtdEstoque);
            }
        }

        return formularioValido;
    }
</script>
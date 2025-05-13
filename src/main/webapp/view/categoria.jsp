<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List, br.com.fatec.goldenfit.model.Categoria" %>
<c:url value="/controlador" var="stub"/>
<%
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
%>

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
<body>

<c:import url="template-header-admin.jsp"/>

<div class="container" style="margin-top: 5%">
    <h1 style="color:#173B21;text-align: center; font-size: 30px; margin-top: 10%; margin-bottom:3%">CADASTRO DE
        CATEGORIA</h1>
    <div style="background-color: #173B21; height: 5px;"></div>
    <div class="card-body">
        <div class="row mt-4">
            <div class="col-5">
                <p class="h5 required-label">Descrição</p>
            </div>
            <div class="col-2">
                <p class="h5" style="color: #FFF;"></p>
            </div>
        </div>
        <form id="formCadastroCategoria" action="${stub }" method="post" onsubmit="return validateForm()" novalidate>
            <div class="row pt-2 mb-2 d-flex align-items-center">
                <div class="col-5">
                    <input class="form-control mt-2" type="text" id="nome"
                           name="nome" required="true"/>
                    <span class="error-message" id="nomeError"></span>
                </div>
                <input type="hidden" name="acao" value="salvar"/>
                <input type="hidden" name="viewHelper" value="CadastroCategoriaVH"/>
                <div class="col-2">
                    <button type="submit" class="btn btn-secondary w-100 text-white" alt="Salvar"
                            title="Salvar"
                            style="background-color: #173B21; border-color: #173B21; margin-top: 4%">
                        Cadastrar
                    </button>
                </div>
            </div>
        </form>
    </div>

    <div class="card-body">
        <h1 style="color:#173B21;text-align: center; font-size: 30px; margin-top: 5%; margin-bottom:3%">LISTAGEM DE CATEGORIAS</h1>
        <div style="background-color: #173B21; height: 5px;"></div>
        <div class="row mt-4">
            <div class="col-5">
                <p class="h5">Descrição</p>
            </div>
            <div class="col-2">
                <p class="h5">Data de cadastro</p>
            </div>
            <div class="col-2">
                <p class="h5" style="color: #FFF;"></p>
            </div>
            <div class="col-2">
                <p class="h5" style="color: #FFF;"></p>
            </div>
        </div>
        <%
            if (categorias != null) {
                for (Categoria categoria : categorias) {
        %>
        <form action="${stub}" method="post" novalidate>
            <div class="row pt-2 mb-2 d-flex align-items-center">
                <div class="col-5">
                    <input class="form-control mt-2" type="text" id="descricaoAtualizar"
                           name="descricaoAtualizar" value="<%=categoria.getNome()%>" required="true"/>
                </div>
                <div class="col-2">
                    <p style="margin-top: 13%"><fmt:formatDate value="<%=categoria.getDtCadastro()%>" pattern="dd/MM/yyyy"/></p>
                </div>
                <input type="hidden" name="id" value="<%=categoria.getId()%>"/>
                <input type="hidden" name="acao" value="alterar"/>
                <input type="hidden" name="viewHelper" value="AlterarCategoriaVH"/>
                <div class="col-2">
                    <button type="submit" class="btn btn-secondary w-100 text-white" alt="Atualizar"
                            title="Atualizar"
                            style="background-color: #173B21; border-color: #173B21; margin-top: 4%">
                        Atualizar
                    </button>
                </div>
                <div class="col-2">
                    <a class="btn btn-secondary w-100" style="background-color: darkred; border-color: darkred; margin-top: 4%" alt="Excluir endereço" title="Excluir endereço"
                       href="/GoldenFit_war/controlador?acao=excluir&viewHelper=ExcluirCategoriaVH&id=<%=categoria.getId()%>">
                        Excluir
                    </a>
                </div>
            </div>
        </form>
        <%
                }
            }
        %>
    </div>
</div>
</body>
</html>

<script>
    function validateForm() {
        var nome = document.getElementById("nome");
        var nomeError = document.getElementById("nomeError");

        if (!nome.value.trim()) {
            nome.classList.add("invalid-input");
            nomeError.innerText = "Campo obrigatório";
            return false;
        } else {
            nome.classList.remove("invalid-input");
            nomeError.innerText = "";
            return true;
        }
    }
</script>

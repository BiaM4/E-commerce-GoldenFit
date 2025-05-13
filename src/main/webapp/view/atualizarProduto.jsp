<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List, br.com.fatec.goldenfit.model.Categoria" %>
<%@ page import="br.com.fatec.goldenfit.model.GrupoPrecificacao" %>
<%@ page import="br.com.fatec.goldenfit.model.Produto" %>
<%@ page import="br.com.fatec.goldenfit.model.EstoqueItem" %>
<c:url value="/controlador" var="stub"/>

<%
    Produto produto = (Produto) request.getSession().getAttribute("produto");
    EstoqueItem estoqueItem = new EstoqueItem();
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
    List<String> grupos = (List<String>) request.getAttribute("gruposPrecificacao");
%>

<!DOCTYPE html>
<html lang="pt-br">
<c:import url="template-head-admin.jsp"/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dropdown com Checkboxes</title>

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

        .multiselect {
            width: 100%;
            position: relative;
        }

        .selectBox select {
            width: 100%;
            padding: 5px;
            border: 1px solid #ccc;
            background-color: #fff;
            cursor: pointer;
        }

        .overSelect {
            position: absolute;
            left: 0;
            right: 0;
            top: 0;
            bottom: 0;
        }

        #checkboxes {
            display: none;
            position: absolute;
            width: 100%;
            border: 1px solid #ccc;
            overflow-y: auto;
            max-height: 150px;
            z-index: 1000;
        }

        #checkboxes label {
            display: block;
            padding: 5px;
            cursor: pointer;
        }

        #checkboxes label:hover {
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>
<c:import url="template-header-admin.jsp"/>
<div class="container mb-5">
    <p style="color:#173B21;text-align: center; font-size: 30px;justify-content: center" class="h4 mb-3 mt-5">ATUALIZAR
        PRODUTO</p>
    <div style="background-color: #173B21; height: 5px;margin-top: 3%; margin-bottom: 3%"></div>

    <form id="formCadastroProduto" action="${stub }" method="post" novalidate onsubmit="return validarFormulario();">
        <div>
            <div class="card-body">
                <div class="row mt-1" style="margin-bottom: 1%">
                    <div class="col-8">
                        <p class="h5 required-label">Nome</p>
                        <input class="form-control" type="text" name="nome" id="nome" value="<%=produto.getNome()%>"
                               required="true"/>
                        <div class="error-message"></div>
                    </div>
                    <div class="col-4">
                        <p class="h5">Código</p>
                        <input class="form-control" type="number" name="codigo" id="codigo"
                               value="<%=produto.getCode()%>" readonly/>
                    </div>
                </div>

                <div class="row mt-1">
                    <div class="col">
                        <p class="h5 required-label">Descrição</p>
                        <textarea class="form-control" name="descricao" id="descricao"
                                  required="true"><%=produto.getDescricao()%></textarea>
                        <div class="error-message"></div>
                    </div>
                </div>

                <div class="row mt-1" style="margin-bottom: 1%">
                    <div class="col-4">
                        <p class="h5 required-label">Cor</p>
                        <input class="form-control" type="text" name="cor" id="cor" value="<%=produto.getCor()%>"
                               required="true"/>
                        <div class="error-message"></div>
                    </div>
                    <div class="col-4">
                        <p class="h5 required-label">Gênero</p>
                        <select class="form-control" name="genero" id="genero" required="true">
                            <option value="">Escolha...</option>
                            <option value="FEMININO" <%= produto.getGenero().equals("FEMININO") ? "selected" : "" %>>
                                Feminino
                            </option>
                            <option value="MASCULINO" <%= produto.getGenero().equals("MASCULINO") ? "selected" : "" %>>
                                Masculino
                            </option>
                        </select>
                        <div class="error-message"></div>
                    </div>
                    <div class="col-4">
                        <p class="h5 required-label">Tamanho</p>
                        <select class="form-control" name="tamanho" id="tamanho" required="true">
                            <option value="">Escolha...</option>
                            <option value="PP" <%= produto.getTamanho().equals("PP") ? "selected" : "" %>>PP</option>
                            <option value="P" <%= produto.getTamanho().equals("P") ? "selected" : "" %>>P</option>
                            <option value="M" <%= produto.getTamanho().equals("M") ? "selected" : "" %>>M</option>
                            <option value="G" <%= produto.getTamanho().equals("G") ? "selected" : "" %>>G</option>
                            <option value="GG" <%= produto.getTamanho().equals("GG") ? "selected" : "" %>>GG</option>
                            <option value="EG" <%= produto.getTamanho().equals("EG") ? "selected" : "" %>>EG</option>
                            <option value="EGG" <%= produto.getTamanho().equals("EGG") ? "selected" : "" %>>EGG</option>
                        </select>
                        <!-- Container para exibir a mensagem de erro -->
                        <div class="error-message"></div>
                    </div>
                </div>

                <div class="row mt-1" style="margin-bottom: 1%">
                    <div class="col-4">
                        <p class="h5 required-label">Referencia</p>
                        <input class="form-control" type="text" name="referencia" id="referencia"
                               value="<%=produto.getReferencia()%>" required="true"/>
                        <div class="error-message"></div>
                    </div>
                    <div class="col-4">
                        <p class="h5 required-label">Marca</p>
                        <input class="form-control" type="text" name="marca" id="marca" value="<%=produto.getMarca()%>"
                               required="true"/>
                        <!-- Adicione a div para a mensagem de erro -->
                        <div class="error-message"></div>
                    </div>
                    <div class="col-4">
                        <p class="h5 required-label">Status</p>
                        <select class="form-control" name="status" id="status" required="true">
                            <option value="">Escolha...</option>
                            <option value="Ativo" <%= produto.getStatus().equals("Ativo") ? "selected" : "" %>>Ativo
                            </option>
                            <option value="Inativo" <%= produto.getStatus().equals("Inativo") ? "selected" : "" %>>
                                Inativo
                            </option>
                        </select>
                        <div class="error-message"></div>
                    </div>
                </div>

                <div class="row mt-1">
                    <div class="col">
                        <p class="h5">Upload da Nova Imagem</p>
                        <input class="form-control" type="file" accept="image/*"
                               id="imagem" name="imagem"/>
                        <p class="h5" style="margin-top: 1%">Imagem Atual</p>
                        <input class="form-control mt-2" type="text" readonly
                               value="${produto.getLinkImagem()}" placeholder="Nome do arquivo"/>
                    </div>
                </div>

            </div>
            <div>
                <div class="card-body">
                    <div class="row mt-1 d-flex align-items-center">
                        <div class="col-4">
                            <p class="h5 required-label">Categoria(s)</p>
                            <div class="multiselect">
                                <div class="selectBox" onclick="showCheckboxes()">
                                    <select>
                                        <option>Escolha...</option>
                                    </select>
                                    <div class="overSelect"></div>
                                </div>
                                <div id="checkboxes">
                                    <c:forEach var="categoria" items="${categorias}">
                                        <label for="categoria-${categoria.id}">
                                            <c:set var="isChecked" value="false"/>
                                            <c:forEach var="catProduto" items="${produto.getCategoria()}">
                                                <c:if test="${catProduto.id eq categoria.id}">
                                                    <c:set var="isChecked" value="true"/>
                                                </c:if>
                                            </c:forEach>
                                            <input type="checkbox" id="categoria-${categoria.id}"
                                                   value="${categoria.id}"
                                                   name="categorias" ${isChecked == "true" ? 'checked="checked"' : ''}/>
                                                ${categoria.getNome()}
                                        </label>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>

                        <div class="col-4">
                            <p class="h5 required-label">Grupo de precificação</p>
                            <select class="form-control" id="grupoPrecificacao" name="grupoPrecificacao"
                                    required="true">
                                <option value="">Escolha...</option>
                                <c:forEach var="grupo" items="${gruposPrecificacao}">
                                    <c:choose>
                                        <c:when test="${grupo.id eq produto.grupoPrecificacao.id}">
                                            <option value="${grupo.id}" selected>${grupo.descricao}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${grupo.id}">${grupo.descricao}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            <div class="error-message"></div>
                        </div>
                    </div>
                    <div style="margin-bottom: 2%"></div>
                    <div class="row mt-1">
                        <div class="col">
                            <p class="h5 required-label">Motivo</p>
                            <textarea class="form-control" name="motivo" id="motivo"
                                      placeholder="Informe aqui o motivo da alteração do status"><%= produto.getMotivo() != null ? produto.getMotivo() : "" %></textarea>
                        </div>
                    </div>
                </div>
            </div>

            <div>
                <input type="hidden" name="acao" value="alterar"/>
                <input type="hidden" name="viewHelper" value="AlterarProdutoVH"/>
                <input type="hidden" name="id" value="<%=produto.getId()%>"/>
                <br/>
                <div>
                    <button style="background-color: #173B21;margin-left:40%; margin-top: 5%; margin-bottom: 10%"
                            class="btn btn-secondary w-25" type="submit" title="Salvar" alt="Salvar">Salvar
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>

<script>
    function validarFormulario() {
        var camposObrigatorios = ["nome", "descricao", "cor", "genero", "tamanho", "referencia", "status", "categoria", "marca", "grupoPrecificacao"];
        var formularioValido = true;

        for (var i = 0; i < camposObrigatorios.length; i++) {
            var campo = document.getElementById(camposObrigatorios[i]);
            var valorCampo = campo.value.trim();

            if (valorCampo === "") {
                campo.classList.add("campo-invalido");
                formularioValido = false;

                // Verifica se já existe uma mensagem de erro para este campo
                var mensagemErro = campo.parentNode.querySelector(".error-message");
                if (!mensagemErro) {
                    mensagemErro = document.createElement("div");
                    mensagemErro.textContent = "Campo obrigatório";
                    mensagemErro.classList.add("error-message");
                    campo.parentNode.appendChild(mensagemErro);
                }
            } else {
                campo.classList.remove("campo-invalido");
                var mensagemErro = campo.parentNode.querySelector(".error-message");
                if (mensagemErro) {
                    mensagemErro.parentNode.removeChild(mensagemErro);
                }
            }
        }

        return formularioValido;
    }
</script>

<script>
    function verificarAlteracaoStatus() {
        var statusAtual = "<%=produto.getStatus()%>";
        var novoStatus = document.getElementById("status").value;
        var motivoField = document.getElementById("motivo");

        if (novoStatus !== statusAtual) {
            motivoField.parentNode.style.display = "block";
            motivoField.setAttribute("required", "true");
        } else {
            motivoField.parentNode.style.display = "none";
            motivoField.removeAttribute("required");
        }
    }

    function showCheckboxes() {
        var checkboxes = document.getElementById("checkboxes");
        checkboxes.style.display = checkboxes.style.display === "block" ? "none" : "block";
    }

    function showNovaCategoria() {
        var novaCategoriaInput = document.getElementById("novaCategoriaInput");
        novaCategoriaInput.style.display = document.getElementById("novaCategoria").checked ? "block" : "none";
    }

    document.getElementById("status").addEventListener("change", verificarAlteracaoStatus);

    verificarAlteracaoStatus();
</script>


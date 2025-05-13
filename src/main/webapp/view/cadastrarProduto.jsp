<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List, br.com.fatec.goldenfit.model.Categoria" %>
<%@ page import="br.com.fatec.goldenfit.model.GrupoPrecificacao" %>
<c:url value="/controlador" var="stub"/>

<%
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
    List<String> gruposPrecificacao = (List<String>) request.getAttribute("gruposPrecificacao");
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
    <p style="color:#173B21;text-align: center; font-size: 30px;justify-content: center" class="h4 mb-3 mt-5">CADASTRO DE PRODUTO</p>
    <div style="background-color: #173B21; height: 5px;margin-top: 3%; margin-bottom: 3%"></div>

    <form id="formCadastroProduto" action="${stub}" method="post" novalidate onsubmit="return validarFormulario();">
        <div>
            <div class="card-body">
                <div class="row mt-1" style="margin-bottom: 1%">
                    <div class="col-8">
                        <p class="h5 required-label">Nome</p>
                        <input class="form-control" type="text" name="nome" id="nome" required="true"/>
                    </div>
                    <div class="col-4">
                        <p class="h5 required-label">Código</p>
                        <input class="form-control" type="number" name="codigo" id="codigo" required="true"/>
                    </div>
                </div>

                <div class="row mt-1">
                    <div class="col">
                        <p class="h5 required-label">Descrição</p>
                        <textarea class="form-control" name="descricao" id="descricao" required="true"></textarea>
                    </div>
                </div>

                <div class="row mt-1" style="margin-bottom: 1%">
                    <div class="col-4">
                        <p class="h5 required-label">Cor</p>
                        <input class="form-control" type="text" name="cor" id="cor" required="true"/>
                    </div>
                    <div class="col-4">
                        <p class="h5 required-label">Gênero</p>
                        <select class="form-control" name="genero" id="genero" required="true">
                            <option value="">Escolha...</option>
                            <option value="FEMININO">Feminino</option>
                            <option value="MASCULINO">Masculino</option>
                        </select>
                    </div>
                    <div class="col-4">
                        <p class="h5 required-label">Tamanho</p>
                        <select class="form-control" name="tamanho" id="tamanho" required="true">
                            <option value="">Escolha...</option>
                            <option value="PP">PP</option>
                            <option value="P">P</option>
                            <option value="M">M</option>
                            <option value="G">G</option>
                            <option value="GG">GG</option>
                            <option value="EG">EG</option>
                            <option value="EGG">EGG</option>
                        </select>
                    </div>
                </div>

                <div class="row mt-1" style="margin-bottom: 1%">
                    <div class="col-4">
                        <p class="h5 required-label">Referência</p>
                        <input class="form-control" type="text" name="referencia" id="referencia" required="true"/>
                    </div>
                    <div class="col-4">
                        <p class="h5 required-label">Marca</p>
                        <input class="form-control" type="text" name="marca" id="marca" required="true"/>
                    </div>
                    <div class="col-4">
                        <p class="h5">Status</p>
                        <input class="form-control" type="text" id="status" name="status" value="Ativo" readonly/>
                    </div>
                </div>

                <div class="row mt-1">
                    <div class="col">
                        <p class="h5 required-label">Upload de imagem</p>
                        <input class="form-control" type="file" accept="image/*" id="imagem" name="imagem" required/>
                    </div>
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
                                        <input type="checkbox" id="categoria-${categoria.id}" value="${categoria.id}" name="categorias"/>
                                            ${categoria.nome}
                                    </label>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="col-4">
                        <p class="h5 required-label">Grupo de precificação</p>
                        <select class="form-control" id="grupoPrecificacao" name="grupoPrecificacao" required>
                            <option value="">Escolha...</option>
                            <c:forEach var="grupo" items="${gruposPrecificacao}">
                                <option value="${grupo.id}">${grupo.descricao}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <input type="hidden" name="acao" value="salvar" />
            <input type="hidden" name="viewHelper" value="CadastroProdutoVH" />
            <br/>
            <div>
                <button style="background-color: #173B21;margin-left:40%; margin-top: 5%; margin-bottom: 10%" class="btn btn-secondary w-25" type="submit" title="Salvar" alt="Salvar">Cadastrar</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>

<script>
    function validarFormulario() {
        var camposObrigatorios = ["nome", "codigo", "descricao", "cor", "genero", "tamanho", "referencia", "marca", "grupoPrecificacao", "imagem"];
        var formularioValido = true;

        camposObrigatorios.forEach(function(campoId) {
            var campo = document.getElementById(campoId);
            var valorCampo = campo.value.trim();

            if (valorCampo === "") {
                campo.classList.add("campo-invalido");
                formularioValido = false

                var mensagemErro = campo.parentNode.querySelector(".mensagem-erro");
                if (!mensagemErro) {
                    mensagemErro = document.createElement("div");
                    mensagemErro.textContent = "Campo obrigatório";
                    mensagemErro.classList.add("mensagem-erro");
                    mensagemErro.style.color = "red";
                    campo.parentNode.appendChild(mensagemErro);
                }
            } else {
                campo.classList.remove("campo-invalido");
                var mensagemErro = campo.parentNode.querySelector(".mensagem-erro");
                if (mensagemErro) {
                    campo.parentNode.removeChild(mensagemErro);
                }
            }
        });

        var categorias = document.querySelectorAll('input[name="categorias"]:checked');
        var categoriaField = document.querySelector('.multiselect .selectBox select');
        if (categorias.length === 0) {
            formularioValido = false;
            categoriaField.classList.add("campo-invalido");

            var mensagemErro = categoriaField.parentNode.querySelector(".mensagem-erro");
            if (!mensagemErro) {
                mensagemErro = document.createElement("div");
                mensagemErro.textContent = "Selecione pelo menos uma categoria";
                mensagemErro.classList.add("mensagem-erro");
                mensagemErro.style.color = "red";
                categoriaField.parentNode.appendChild(mensagemErro);
            }
        } else {
            categoriaField.classList.remove("campo-invalido");
            var mensagemErro = categoriaField.parentNode.querySelector(".mensagem-erro");
            if (mensagemErro) {
                categoriaField.parentNode.removeChild(mensagemErro);
            }
        }

        return formularioValido;
    }

    function showCheckboxes() {
        var checkboxes = document.getElementById("checkboxes");
        checkboxes.style.display = checkboxes.style.display === "block" ? "none" : "block";
    }

    function showNovaCategoria() {
        var novaCategoriaInput = document.getElementById("novaCategoriaInput");
        novaCategoriaInput.style.display = document.getElementById("novaCategoria").checked ? "block" : "none";
    }
</script>
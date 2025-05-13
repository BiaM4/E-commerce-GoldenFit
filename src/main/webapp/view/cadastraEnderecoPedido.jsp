<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="java.util.List, br.com.fatec.goldenfit.model.enums.Estado" %>

<c:url value="/controlador" var="stub"/>

<!DOCTYPE html>
<html>
<head>
    <c:import url="template-head.jsp"/>
    <title>Cadastro de endereço</title>
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

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
    <link rel="stylesheet" href="view/css/stylesView.css">

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<header>
    <nav class="navbar" style="background-color: #173B21; height: 60px ">
        <div class="d-flex flex-row mb-3"
             style="justify-content: space-between; align-items: center; margin-top: 5px; margin-left: 30px">
            <div class="d-flex flex-row mb-3" style="justify-content: center; align-items: center;">
                <img src="images/LogoGold.png" style="height: 35px; width: 35px">
                <a class="navbar-brand" href="/GoldenFit_war/view/index" style="color:#ECBD69; margin-right: 70px ">GOLDENFIT</a>
            </div>
        </div>
        </div>
    </nav>

    <div style="background-color: #ECBD69; height: 5px;"></div>

    <script src="javascript/validacaoFormulario/validaFormCadastroCartao.js" charset="UTF-8"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <script src="javascript/validacaoFormulario/validaFormCadastroEndereco.js" charset="UTF-8"></script>
    <script type="text/javascript" src="javascript/carregaCidades.js" charset="UTF-8"></script>
</header>
<%
    List<Estado> estados = Estado.getEstadosOrdenados();
%>
<div class="container" style="margin-top: 5%; width: 80%">
    <div>
        <h1 style="color:#173B21;text-align: center; font-size: 35px;">CADASTRO DE ENDEREÇO</h1>
    </div>
    <div style="background-color: #173B21; height: 5px; margin-bottom: 3%"></div>

    <div class="card shadow">
        <div class="card-body">
            <div id="formErrorMessage" class="error-message"></div>
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <form id="formCadastroEndereco" action="${stub }" method="post" onsubmit="return validateForm()"
                          novalidate>
                        <div class="row d-flex align-items-center" style="margin-bottom: 1%">
                            <div class="col-md-9">
                                Descrição: <strong style="color:#ff253a">*</strong><input class="form-control"
                                                                                          type="text"
                                                                                          name="descricaoEndereco"
                                                                                          id="descricaoEndereco"
                                                                                          placeholder="Exemplo: Minha Casa"
                                                                                          required="true"
                                                                                          maxlength="120"/>
                            </div>
                            <div class="col-3 mb-2">
                                <label for="tipoLogradouro">Tipo de logradouro: <strong style="color:#ff253a">*</strong></label>
                                <select required="true"
                                        class="form-control"
                                        name="tipoLogradouro"
                                        id="tipoLogradouro">
                                    <option value="">Escolha...</option>
                                    <option value="Rua">Rua</option>
                                    <option value="Travessa">Travessa</option>
                                    <option value="Avenida">Avenida</option>
                                    <option value="Alameda">Alameda</option>
                                    <option value="Estrada">Estrada</option>
                                    <option value="Rodovia">Rodovia</option>
                                    <option value="Jardim">Jardim</option>
                                </select>
                            </div>
                        </div>
                        <div class="row d-flex align-items-center" style="margin-bottom: 2%">
                            <div class="col-md-12">
                                Logradouro: <strong style="color:#ff253a">*</strong><input class="form-control"
                                                                                           type="text" name="logradouro"
                                                                                           id="logradouro"
                                                                                           required="true"
                                                                                           placeholder="Digite o logradouro..."/>
                            </div>
                        </div>

                        <div class="row d-flex align-items-center" style="margin-bottom: 1%">
                            <div class="col-md-3">
                                Nº: <strong style="color:#ff253a">*</strong><input class="form-control" type="text"
                                                                                   name="numeroEndereco"
                                                                                   id="numeroEndereco"
                                                                                   required="true" placeholder="0000"/>
                            </div>
                            <div class="col-md-6">
                                Complemento: <input class="form-control" type="text" name="complemento" id="complemento"
                                                    placeholder="Digite o complemento..."/>
                            </div>
                            <div class="col-3 mb-2">
                                <label for="tipoResidencia">Tipo de residência: <strong style="color:#ff253a">*</strong></label>
                                <select class="form-control" name="tipoResidencia" id="tipoResidencia" required="true">
                                    <option value="">Escolha...</option>
                                    <option value="Casa">Casa</option>
                                    <option value="Apartamento">Apartamento</option>
                                    <option value="Flat">Flat</option>
                                    <option value="Kitnet">Kitnet</option>
                                    <option value="Loft">Loft</option>
                                </select>
                            </div>
                        </div>

                        <div class="row d-flex align-items-center" style="margin-bottom: 1%">
                            <div class="col-md-6">
                                Bairro: <strong style="color:#ff253a">*</strong><input class="form-control" type="text"
                                                                                       name="bairro" id="bairro"
                                                                                       required="true"
                                                                                       placeholder="Digite o bairro..."/>
                            </div>
                            <div class="col-md-3">
                                CEP: <strong style="color:#ff253a">*</strong><input class="form-control" type="text"
                                                                                    name="cep" id="cep"
                                                                                    placeholder="00000-000"
                                                                                    required="true"/>
                            </div>
                            <div class="col-3 mb-2">
                                <label for="estado">Estado: <strong style="color:#ff253a">*</strong></label>
                                <select class="form-control" name="estado" id="estado" required="true"
                                        onchange="carregarCidades(this);">
                                    <option value="">Escolha o estado...</option>
                                    <% for (Estado estado : estados) { %>
                                    <option value=<%=estado.getSigla()%>><%=estado.getNome()%>
                                    </option>
                                    <% } %>
                                </select>
                            </div>
                        </div>
                        <div class="row justify-content-center">
                            <div class="col-md-6 mb-2">
                                <label for="cidade">Cidade: <strong style="color:#ff253a">*</strong></label>
                                <select class="form-control" name="cidade" id="cidade" required="true">
                                    <option value="">Escolha...</option>
                                </select>
                            </div>
                            <div class="col-md-6 mb-2">
                                <label for="tipoEndereco">Tipo de endereço: <strong
                                        style="color:#ff253a">*</strong></label>
                                <select class="form-control" name="tipoEndereco" id="tipoEndereco" required="true">
                                    <option value="">Escolha...</option>
                                    <option value="Cobranca">Cobrança</option>
                                    <option value="Entrega">Entrega</option>
                                </select>
                            </div>
                        </div>
                        <div class="row justify-content-center">
                            <div class="col-md-12">
                                Observação: <strong style="color:#ff253a">*</strong><textarea class="form-control"
                                                                                              type="text"
                                                                                              maxlength="200"
                                                                                              name="observacaoEndereco"
                                                                                              id="observacaoEndereco"
                                                                                              required="true"></textarea>
                            </div>
                        </div>
                        <br/>
                        <input type="hidden" name="acao" value="salvar"/>
                        <input type="hidden" name="viewHelper" value="CadastroEnderecoPedidoVH"/>

                        <div style="text-align: center;">
                            <div class="d-flex justify-content-center">
                                <button type="submit" class="btn btn-secondary w-26 text-white" alt="Salvar"
                                        title="Salvar"
                                        style="background-color: #173B21; border-color: #173B21; margin-top: 15px; margin-bottom: 20px; margin-right: 10px;">
                                    <i class="fas fa-save"></i> Salvar
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<script>
    function validateForm() {
        var form = document.getElementById("formCadastroEndereco");
        var inputs = form.querySelectorAll("input, select, textarea");
        var isValid = true;

        inputs.forEach(function (input) {
            var errorSpan = document.getElementById(input.id + "Error");
            if (input.hasAttribute("required") && !input.value.trim()) {
                isValid = false;
                input.classList.add("invalid-input");
                if (!errorSpan) {
                    errorSpan = document.createElement("span");
                    errorSpan.id = input.id + "Error";
                    errorSpan.className = "error-message";
                    input.parentNode.appendChild(errorSpan);
                }
                errorSpan.textContent = "Campo obrigatório";
                errorSpan.style.display = "block";
            } else {
                input.classList.remove("invalid-input");
                if (errorSpan) {
                    errorSpan.textContent = "";
                    errorSpan.style.display = "none";
                }
            }
        });

        return isValid;
    }
</script>



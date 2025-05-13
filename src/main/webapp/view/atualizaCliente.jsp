<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="br.com.fatec.goldenfit.model.Cliente" %>

<c:url value="/controlador" var="stub"/>

<!DOCTYPE html>
<html>
<head>
    <title>Atualizar Dados</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        .invalid-input {
            border-color: red !important;
        }
    </style>
</head>

<body>
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
<%
    Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
%>
<div class="container" style="margin-top: 5%">

    <div>
        <h1 style="color:#173B21;text-align: center; font-size: 30px;"> ATUALIZAÇÃO DE
            CADASTRO </h1>
    </div>
    <div style="background-color: #173B21; height: 5px; margin-bottom: 3%"></div>


    <div>
        <h1 style="color:#173B21;text-align: center; font-size: 27px; margin-bottom: 3%"> Dados pessoais </h1>
    </div>

    <div class="card shadow">
        <div class="card-body">
            <div id="formErrorMessage" class="error-message"></div>
            <div class="row">
                <div class="col-12 mb-3">
                    <form id="formAtualizaCliente" action="${stub}" method="post" onsubmit="return validateForm()"
                          novalidate>

                        <div class="row d-flex justify-content-between">
                            <div class="col-md-5">Nome: <strong style="color:#ff253a">*</strong><input
                                    class="form-control" type="text" name="nome"
                                    value="${cliente.nome}" required="true"/></div>
                            <div class="col-md-5">Sobrenome: <strong style="color:#ff253a">*</strong><input
                                    class="form-control" type="text" name="sobrenome"
                                    value="${cliente.sobrenome}" required="true"/></div>
                            <div class="col-md-2"><%--@declare id="cidade"--%><label for="cidade">Gênero: <strong
                                    style="color:#ff253a">*</strong></label>
                                <select class="form-control" name="genero" required="true">
                                    <option value="">Escolha...</option>
                                    <option value="FEMININO" ${cliente.genero == 'FEMININO' ? 'selected' : ''}>
                                        Feminino
                                    </option>
                                    <option value="MASCULINO" ${cliente.genero == 'MASCULINO' ? 'selected' : ''}>
                                        Masculino
                                    </option>
                                </select>
                            </div>
                        </div>


                        <div class="row d-flex justify-content-between">
                            <div class="col-md-2">DDD: <strong style="color:#ff253a">*</strong><input
                                    class="form-control" type="text" name="dddResidencial"
                                    value="${cliente.telefoneResidencial.ddd}" required="true"
                                    placeholder="00"/>
                            </div>
                            <div class="col-md-4">Telefone residencial: <strong style="color:#ff253a">*</strong><input
                                    class="form-control" type="tel"
                                    name="numeroTelResidencial"
                                    value="${cliente.telefoneResidencial.numero}"
                                    placeholder="
                                                                               0000-0000" required="true"/>
                            </div>
                            <div class="col-md-2">DDD: <strong style="color:#ff253a">*</strong><input
                                    class="form-control" type="text" name="dddCelular"
                                    value="${cliente.telefoneCelular.ddd}" required="true"
                                    placeholder="00"/>
                            </div>
                            <div class="col-md-4">Telefone celular: <strong style="color:#ff253a">*</strong><input
                                    class="form-control" type="tel"
                                    name="numeroTelCelular"
                                    value="${cliente.telefoneCelular.numero}"
                                    placeholder="
                                                                           00000-0000"/>
                            </div>
                        </div>

                        <div class="row d-flex justify-content-between">
                            <div class="col-md-6">Data de Nascimento:
                                <strong style="color:#ff253a">*</strong><input class="form-control" type="text"
                                                                               name="dataNascimento"
                                                                               value="<fmt:formatDate value="${cliente.dataNascimento }" pattern="dd/MM/yyyy"/>"
                                                                               required="true"/>
                            </div>
                            <div class="col-md-6">CPF: <strong style="color:#ff253a">*</strong><input
                                    class="form-control" type="text" name="cpf"
                                    value="${cliente.cpf}" required="true"/></div>
                        </div>

                        <div>E-mail: <strong style="color:#ff253a">*</strong><input class="form-control" type="email"
                                                                                    name="email"
                                                                                    value="${cliente.usuario.email}"
                                                                                    required="true"/></div>


                        <div><input type="hidden" name="score" value="${cliente.score}"/></div>
                        <div><input type="hidden" name="status" value="true"/></div>

                        <input type="hidden" name="acao" value="alterar"/>
                        <input type="hidden" name="viewHelper" value="AlterarClienteVH"/>
                        <br/>

                        <div class="d-flex justify-content-center align-items-center" style="height: 5vh;">
                            <button type="submit" class="btn btn-secondary w-26 text-white" alt="Salvar" title="Salvar"
                                    style="background-color: #173B21; border-color: #173B21; margin-top: 15px; margin-bottom: 20px; margin-right: 10px;">
                                Salvar
                            </button>
                            <a href="listaClientes.jsp" class="btn btn-secondary w-26" type="submit" title="Voltar"
                               alt="Voltar"
                               style="background-color: #ECBD69; border-color: #ECBD69; margin-top: 15px; margin-bottom: 20px;">Voltar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<br/>
</body>
</html>

<script>
    function validateForm() {
        var form = document.getElementById("formAtualizaCliente");
        var inputs = form.querySelectorAll("input, select");
        var isValid = true;
        var errorMessage = "";

        inputs.forEach(function (input) {
            if (input.hasAttribute("required") && !input.value.trim()) {
                isValid = false;
                input.classList.add("invalid-input");
            } else {
                input.classList.remove("invalid-input");
            }
        });

        var errorElement = document.getElementById("formErrorMessage");
        errorElement.innerHTML = errorMessage;

        return isValid;
    }
</script>
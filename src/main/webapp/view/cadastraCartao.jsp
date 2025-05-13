<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url value="/controlador" var="stub"/>

<!DOCTYPE html>
<html>
<head>
    <c:import url="template-head.jsp"/>
    <title>Cadastro de cart�o</title>
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
</header>

<div class="container" style="margin-top: 5%; width: 80%">

    <div>
        <h1 style="color:#173B21;text-align: center; font-size: 35px;">CADASTRO DE CART�O</h1>
    </div>
    <div style="background-color: #173B21; height: 5px; margin-bottom: 3%"></div>

    <div class="card shadow">
        <div class="card-body">
            <div id="formErrorMessage" class="error-message"></div>
            <div class="row justify-content-center">
                <div class="col-9 mb-3">
                    <form id="formCadastroCartao" action="${stub }" method="post" onsubmit="return validateForm()"
                          novalidate>
                        <div class="row d-flex align-items-center">
                            <div class="col-md-9">
                                N�mero do cart�o: <strong style="color:#ff253a">*</strong>
                                <input class="form-control" type="text" maxlength="19"
                                       name="numeroCartao" id="numeroCartao" required="true"
                                       placeholder="Digite o n�mero do cart�o..." style="margin-bottom: 2%"/>
                            </div>

                            <script>
                                $(document).ready(function () {
                                    $("#numeroCartao").on("input", function () {
                                        var inputValue = $(this).val();
                                        inputValue = inputValue.replace(/\D/g, '');
                                        var formattedValue = formatCreditCardNumber(inputValue);
                                        $(this).val(formattedValue);
                                    });
                                });

                                function formatCreditCardNumber(value) {
                                    var formattedValue = value.replace(/(\d{4})/g, '$1 ').trim();
                                    return formattedValue;
                                }
                            </script>

                            <div class="col-3 mb-2">
                                Bandeira: <strong style="color:#ff253a">*</strong>
                                <select class="form-control" name="bandeira" id="bandeira" required="true"
                                        style="margin-bottom: 2%">
                                    <option value="">Escolha...</option>
                                    <option value="Visa">Visa</option>
                                    <option value="Mastercard">Mastercard</option>
                                    <option value="Elo">Elo</option>
                                </select>
                            </div>
                        </div>

                        <div class="row d-flex ">
                            <div class="col-8 mb-4">
                                Nome impresso no cart�o: <strong style="color:#ff253a">*</strong><input
                                    class="form-control" type="text"
                                    name="nomeImpresso" required="true"
                                    placeholder="Digite o nome impresso no cart�o..."/>
                            </div>
                            <div class="col-4 mb-4">
                                C�digo de seguran�a (CVV): <strong style="color:#ff253a">*</strong><input
                                    class="form-control" type="text" maxlength="3"
                                    name="codigoSeguranca" id="codigoSeguranca" required="true"
                                    placeholder="Informe o c�digo..."/>
                            </div>
                        </div>

                        <div class="row mb-4 align-items-center" style="margin-left: 2%">
                            <div class="col-5">
                                <input class="form-check-input" type="checkbox" value="true" name="preferencial"
                                       id="preferencial">
                                <label class="form-check-label" for="preferencial">Usar como cart�o
                                    preferencial?</label>
                            </div>
                        </div>

                        <input type="hidden" name="acao" value="salvar"/>
                        <input type="hidden" name="viewHelper" value="CadastroCart�oVH"/>

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
        var form = document.getElementById("formCadastroCartao");
        var inputs = form.querySelectorAll("input, select");
        var isValid = true;
        var errorMessage = "Campo obrigat�rio";
        var cvvRegex = /^\d+$/; // Express�o regular para aceitar apenas n�meros

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
                errorSpan.textContent = errorMessage;
                errorSpan.style.display = "block";
            } else if (input.name === "codigoSeguranca" && !cvvRegex.test(input.value.trim())) {
                isValid = false;
                input.classList.add("invalid-input");
                if (!errorSpan) {
                    errorSpan = document.createElement("span");
                    errorSpan.id = input.id + "Error";
                    errorSpan.className = "error-message";
                    input.parentNode.appendChild(errorSpan);
                }
                errorSpan.textContent = "CVV deve conter apenas n�meros";
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

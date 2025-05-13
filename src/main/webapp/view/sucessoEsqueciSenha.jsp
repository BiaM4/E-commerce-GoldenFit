<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url value="/controlador" var="stub"/>

<!DOCTYPE html>
<html>
<head>
    <c:import url="template-head.jsp"/>

    <title>Senha Alterada</title>
</head>
<body>
<header>
    <script src="javascript/validacaoFormulario/validaFormConfirmacaoSenha.js" charset="UTF-8"></script>
</header>

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

<div class="container" style="margin-top: 5%; width: 30%">
    <div class="card shadow">
        <div>
            <h1 class="card-header" style="color:#173B21; text-align: center; font-size: 25px;"> SUA SOLICITAÇÃO FOI
                RECEBIDA </h1>
        </div>
        <div class="card-header" style="font-size: 18px; text-align: center;">
            <p>E-mail de redefinição de senha enviado!
            </p>
            <p>Siga as instruções e faça o login
                novamente.
            </p>
        </div>


        <div class="row justify-content-center" style="margin-top: 15px; margin-bottom: 20px;">
            <div class="col-auto">
                <a href="login.jsp" class="btn btn-secondary" type="submit" title="login" alt="login" style="background-color: #173B21; border-color: #173B21" onclick="return validarFormulario()">Entrar</a>
            </div>
        </div>

        </form>
    </div>
</div>
</div>

</div>
</div>
</div>
</body>
</html>
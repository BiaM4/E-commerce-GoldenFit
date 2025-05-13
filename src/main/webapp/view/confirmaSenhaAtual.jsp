<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url value="/controlador" var="stub"/>

<!DOCTYPE html>
<html>
<head>
    <c:import url="template-head.jsp"/>

    <title>Confirmação de senha</title>
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
            <h1 class="card-header" style="color:#173B21;text-align: center; font-size: 27px;"> CONFIRMAÇÃO DE
                SENHA </h1>
        </div>
        <div class="card-header" style="font-size: 19px; justify-content: left">
            <p>Insira a senha atual do Usuário</p>
        </div>

        <div class="card-body">
            <div class="row justify-content-center">
                <div class="col-8 mb-3">

                    <form id="formConfirmacaoSenha" action="${stub }" method="post" novalidate>
                        <span class="error"><%=request.getParameter("mensagemErro") != null ? (String) request.getParameter("mensagemErro") : ""%></span>
                        <div class="text-center">
                            <label for="senha" class="form-label">Senha:</label>
                            <input class="form-control form-control-lg" type="password" name="senha" id="senha"
                                   required="true"/>
                            <br/>
                            <label for="confirmacaoSenha" class="form-label">Confirme a senha:</label>
                            <input class="form-control form-control-lg" type="password" name="confirmacaoSenha"
                                   id="confirmacaoSenha" required="true"/>
                            <br/>
                        </div>

                        <input type="hidden" name="acao" value="consultar"/>
                        <input type="hidden" name="viewHelper" value="ConsultarSenhaVH"/>
                        <div class="row justify-content-center" style="margin-top: 15px; margin-bottom: 20px;">
                            <div class="col-auto">
                                <button type="submit" class="btn btn-secondary w-26 text-white" alt="Salvar"
                                        title="Salvar" style="background-color: #173B21; border-color: #173B21;">
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
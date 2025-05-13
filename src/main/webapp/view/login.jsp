<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url value="/controlador" var="linkEntradaServlet"/>

<!DOCTYPE html>
<html>
<head>
    <c:import url="template-head.jsp"/>
    <title>Login</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">

    <link rel="stylesheet" href="view/css/stylesView.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
</head>
<body>
<header>
    <script src="javascript/validacaoFormulario/validaFormLogin.js" charset="UTF-8"></script>
</header>
<div class="d-flex flex-row mb-3">
    <div class="container" style="background-color: #173B21; width:100%; height: 920px;">
        <img style="width:100%; height: 920px;" src="images/login.png">
    </div>
    <div class="container" style="margin-left: 12%; margin-top: 15%">
        <div>
            <h1 class="card-header" style="color:#173B21; margin-right: 150px; text-align: center; font-size: 25px">
                <strong>B E M - V I N D O !</strong></h1>
        </div>

        <div class="card-header">
            <p style="color:#173B21; margin-right: 150px; text-align: center; font-size: 15px"><strong>E N T R A R</strong></p>
        </div>

        <div class="card-body">
            <div class="row">
                <div class="col-8 mb-3">

                    <form id="formLogin" action="${linkEntradaServlet }" method="post" novalidate>
                        <span class="error"><%=request.getParameter("mensagemErro") != null ? (String) request.getParameter("mensagemErro") : ""%></span>
                        <div class="col-6">E-mail: <input style="width: 225%" class="form-control" type="email"
                                                          name="email" id="email" required="true"/></div>
                        <div class="col-6">Senha: <input style="width: 225%; margin-bottom: 20px" class="form-control"
                                                         type="password" name="senha" id="senha" required="true"/></div>

                        <div style="width: 225%">
                            <a class="navbar-brand" style="color:#173B21; font-size: 14px;">Ainda não tem cadastro?</a>
                            <a id="ainda não tem uma conta?" href="cadastraCliente.jsp"
                               style="color:#173B21; font-size: 14px;">faça o cadastro!</a>
							<a id="esqueci a senha" href="esqueciSenha.jsp"
							   style="color:#173B21; font-size: 14px; margin-left: 16%">Esqueci minha senha</a>
                        </div>

                        <input type="hidden" name="acao" value="consultar"/>
                        <input type="hidden" name="viewHelper" value="LoginVH"/>

                        <br/>
                        <div>
                            <button class="btn btn-secondary w-25" type="submit" title="Entrar" alt="Entrar"
                                    style=" background-color: #173B21; margin-left: 50%; border-color: #173B21; margin-top: 15px; margin-bottom: 20px">
                                Entrar
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
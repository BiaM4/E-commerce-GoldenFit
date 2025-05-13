<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url value="/controlador" var="stub"/>

<!DOCTYPE html>
<html>
<head>
<c:import url="template-head.jsp"/>
<title>Inativação de Conta</title>
</head>
<body>
		<nav class="navbar" style="background-color: #173B21; height: 60px ">
			<div class="d-flex flex-row mb-3" style="justify-content: space-between; align-items: center; margin-top: 5px; margin-left: 30px">
				<div class="d-flex flex-row mb-3" style="justify-content: center; align-items: center;">
					<img src="images/LogoGold.png" style="height: 35px; width: 35px">
					<a class="navbar-brand" href="/GoldenFit_war/view/index" style="color:#ECBD69; margin-right: 70px ">GOLDENFIT</a>
				</div>
			</div>
			</div>
		</nav>

		<div style="background-color: #ECBD69; height: 5px;"></div>

			<div class="container" style="margin-top: 5%; width: 50%">
				<div class="card shadow">
					<div>
					<h1 class="card-header" style="color:#173B21;text-align: center; font-size: 27px;"> INATIVAÇÃO DE CONTA </h1>
					</div>
					<div class="card-body">
						<div class="row">
							<div class="col-8 mb-3">
							<form  action="${stub }" method="post">
							<p>Ao inativar sua conta, você perderá o acesso a sua conta.</p>
								<p>Deseja realmente desativar sua conta?</p>

								<input type="hidden" name="acao" value="listaClientes" />

								<input type="hidden" name="acao" value="excluir" />	
								<input type="hidden" name="viewHelper" value="InativarClienteVH"/>	
								<br/>
								<div style="text-align: center;">
									<div class="d-flex justify-content-center">
										<a href="listaClientes.jsp" class="btn btn-secondary w-26" type="submit" title="Cancelar" alt="Cancelar" style="background-color: darkred; border-color: darkred; margin-left: 50%; margin-top: 15px; margin-bottom: 20px; margin-right: 10px;">CANCELAR</a>

										<a href="/GoldenFit_war/controlador?acao=excluir&viewHelper=InativarUsuarioVH" class="btn btn-secondary w-26" type="submit" title="Confirmar" alt="Confirmar" style="background-color: #173B21; border-color: #173B21; margin-top: 15px; margin-bottom: 20px;">CONFIRMAR</a>
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
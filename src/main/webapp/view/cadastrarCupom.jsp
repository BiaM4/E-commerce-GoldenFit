<%@ page import="java.util.List" %>
<%@ page import="br.com.fatec.goldenfit.model.Cliente" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/controlador" var="stub"/>
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
<c:import url="template-head-admin.jsp" />
<body>
<c:import url="template-header-admin.jsp" />

<%
	List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
%>


<div class="container" style="margin-top: 5%">
	<h1 style="color:#173B21;text-align: center; font-size: 30px; margin-top: 10%; margin-bottom:3%">CADASTRO DE
		CUPOM</h1>
	<div style="background-color: #173B21; height: 5px;"></div>
	<div class="card-body">
		<div class="row mt-4">
			<div class="col-1">
				<p class="h5 required-label">Código</p>
			</div>
			<div class="col-4">
				<p class="h5 required-label">Nome</p>
			</div>
			<div class="col-1">
				<p class="h5 required-label">Valor</p>
			</div>
			<div class="col-2">
				<p class="h5 required-label">Validade</p>
			</div>
			<div class="col-2">
				<p class="h5 required-label">Tipo</p>
			</div>
			<div class="col-2">
				<p class="h5 required-label">Cliente</p>
			</div>
		</div>

		<form id="formCadastroCupom" action="${stub }" method="post" onsubmit="return validateForm()" novalidate>
			<div class="row pt-2 mb-2 d-flex align-items-center">
				<div class="col-1">
					<input class="form-control mt-2" type="text" id="codigo"
						   name="codigo" required="true" />
					<span class="error-message" id="codigoError"></span>
				</div>
				<div class="col-4">
					<input class="form-control mt-2" type="text" id="nome"
						   name="nome" required="true" />
					<span class="error-message" id="nomeError"></span>
				</div>
				<div class="col-1">
					<input class="form-control mt-1" type="text" id="valor"
						   name="valor" required="true" style="margin-top: 4%"/>
					<span class="error-message" id="valorError"></span>
				</div>
				<div class="col-2">
					<input class="form-control mt-2" type="date" id="validade"
						   name="validade" required="true" />
					<span class="error-message" id="validadeError"></span>
				</div>
				<div class="col-2">
					<select class="form-control" name="tipo" id="tipo" required="true" style="margin-top: 4%">
						<option value="">Escolha...</option>
						<option value="PROMOCIONAL">Promocional</option>
						<option value="TROCA">Troca</option>
					</select>
					<span class="error-message" id="tipoError"></span>
				</div>
				<div class="col-2">
					<select class="form-control" name="cliente" id="cliente" required="true" style="margin-top: 4%">
						<option value="">Escolha o Cliente...</option>
						<c:forEach var="cliente" items="${clientes}">
							<option value="${cliente.id}">${cliente.id} - ${cliente.nome}</option>
						</c:forEach>
					</select>
					<span class="error-message" id="clienteError"></span>
				</div>

				<input type="hidden" name="acao" value="salvar" />
				<input type="hidden" name="viewHelper" value="CadastroCupomVH" />
			</div>

			<div class="d-flex justify-content-center">
				<button type="submit" class="btn btn-secondary w-40 text-white" alt="Salvar"
						title="Salvar"
						style="background-color: #173B21; border-color: #173B21; margin-top: 4%">
					Cadastrar
				</button>
			</div>
		</form>
	</div>
</div>
</body>
</html>

<script>
	function validateForm() {
		var isValid = true;

		// Verifica o campo de Código
		var codigo = document.getElementById("codigo");
		var codigoError = document.getElementById("codigoError");
		if (!codigo.value.trim()) {
			codigo.classList.add("invalid-input");
			codigoError.innerText = "Campo obrigatório";
			isValid = false;
		} else {
			codigo.classList.remove("invalid-input");
			codigoError.innerText = "";
		}

		// Verifica o campo de Nome
		var nome = document.getElementById("nome");
		var nomeError = document.getElementById("nomeError");
		if (!nome.value.trim()) {
			nome.classList.add("invalid-input");
			nomeError.innerText = "Campo obrigatório";
			isValid = false;
		} else {
			nome.classList.remove("invalid-input");
			nomeError.innerText = "";
		}

		// Verifica o campo de Valor
		var valor = document.getElementById("valor");
		var valorError = document.getElementById("valorError");
		if (!valor.value.trim()) {
			valor.classList.add("invalid-input");
			valorError.innerText = "Campo obrigatório";
			isValid = false;
		} else {
			valor.classList.remove("invalid-input");
			valorError.innerText = "";
		}

		// Verifica o campo de Validade
		var validade = document.getElementById("validade");
		var validadeError = document.getElementById("validadeError");
		if (!validade.value.trim()) {
			validade.classList.add("invalid-input");
			validadeError.innerText = "Campo obrigatório";
			isValid = false;
		} else {
			validade.classList.remove("invalid-input");
			validadeError.innerText = "";
		}

		// Verifica o campo de Tipo
		var tipo = document.getElementById("tipo");
		var tipoError = document.getElementById("tipoError");
		if (!tipo.value.trim()) {
			tipo.classList.add("invalid-input");
			tipoError.innerText = "Campo obrigatório";
			isValid = false;
		} else {
			tipo.classList.remove("invalid-input");
			tipoError.innerText = "";
		}

		// Verifica o campo de Cliente
		var cliente = document.getElementById("cliente");
		var clienteError = document.getElementById("clienteError");
		if (!cliente.value.trim()) {
			cliente.classList.add("invalid-input");
			clienteError.innerText = "Campo obrigatório";
			isValid = false;
		} else {
			cliente.classList.remove("invalid-input");
			clienteError.innerText = "";
		}

		return isValid;
	}
</script>

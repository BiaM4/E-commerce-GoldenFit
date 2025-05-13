<%@ page import="br.com.fatec.goldenfit.model.GrupoPrecificacao" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
<c:import url="template-head-admin.jsp" />
<c:url value="/controlador" var="stub"/>
	<%
    List<GrupoPrecificacao> grupos = (List<GrupoPrecificacao>) request.getAttribute("grupos");
%>
<body>
<c:import url="template-header-admin.jsp" />
<div class="container" style="margin-top: 5%">
	<h1 style="color:#173B21;text-align: center; font-size: 30px; margin-top: 10%; margin-bottom:3%">CADASTRO DE GRUPO DE PRECIFICAÇÃO</h1>
	<div style="background-color: #173B21; height: 5px; margin-top: 2%"></div>
	<div class="card-body">
		<div class="row mt-4" style="margin-left: 15%">
			<div class="col-5">
				<p class="h5">Descrição <span style="color: red;">*</span></p>
			</div>
			<div class="col-3">
				<p class="h5">Margem de lucro (%) <span style="color: red;">*</span></p>
			</div>
			<div class="col-2">
				<p class="h5" style="color: #FFF;"></p>
			</div>
		</div>
		<form id="formCadastroPrecificacao" action="${stub}" method="post" novalidate>
			<div class="row pt-2 mb-2 d-flex align-items-center" style="margin-left: 15%">
				<div class="col-5">
					<input class="form-control mt-2" type="text" id="descricao" name="descricao" required="true" />
					<div id="descricaoError" style="color: red; display: none;">Campo obrigatório</div>
				</div>
				<div class="col-2">
					<input class="form-control mt-2" type="number" id="margemLucro" name="margemLucro" required="true" min="1"/>
					<div id="margemLucroError" style="color: red; display: none;">Campo obrigatório</div>
				</div>

				<input type="hidden" name="acao" value="salvar" />
				<input type="hidden" name="viewHelper" value="CadastroGrupoPrecificacaoVH" />

				<div class="col-2" style="margin-left: 5%">
					<button type="submit" class="btn btn-secondary w-100 text-white" alt="Salvar" title="Salvar" style="background-color: #173B21; border-color: #173B21; margin-top: 4%">
						Salvar
					</button>
				</div>
			</div>
		</form>
	</div>

	<h1 style="color:#173B21;text-align: center; font-size: 30px; margin-top: 5%; margin-bottom:3%">LISTA DE GRUPOS CADASTRADOS</h1>
	<div style="background-color: #173B21; height: 5px; margin-top: 2%"></div>
	<div class="card-body">
		<div class="row mt-4" style="margin-left: 5%">
			<div class="col-3">
				<p class="h5">Descrição</p>
			</div>
			<div class="col-2">
				<p class="h5">Margem de lucro</p>
			</div>
			<div class="col-2">
				<p class="h5">Data de cadastro</p>
			</div>
			<div class="col-2">
				<p class="h5" style="color: #FFF;"></p>
			</div>
			<div class="col-2">
				<p class="h5" style="color: #FFF;"></p>
			</div>
		</div>
		<%if(grupos != null){
			for(GrupoPrecificacao grupo : grupos){%>
		<form action="${stub}" method="post" novalidate>
			<div class="row pt-2 mb-2 d-flex align-items-center" style="margin-left: 5%">
				<div class="col-3">
					<input class="form-control mt-2" type="text" id="descricaoAtualizar" name="descricaoAtualizar" value="<%=grupo.getDescricao()%>" required="true" />
				</div>
				<div class="col-2">
					<input class="form-control mt-2" type="number" id="margemLucroAtualizar" name="margemLucroAtualizar" value="<%=grupo.getMargemLucro()%>" required="true" min="1"/>
				</div>
				<div class="col-2" style="margin-top: 1.5%">
					<p><fmt:formatDate value="<%=grupo.getDtCadastro()%>" pattern="dd/MM/yyyy"/></p>
				</div>
				<input type="hidden" name="id" value="<%=grupo.getId()%>" />
				<input type="hidden" name="acao" value="alterar" />
				<input type="hidden" name="viewHelper" value="AlterarGrupoPrecificacaoVH" />
				<div class="col-2">
					<button type="submit" class="btn btn-secondary w-100 text-white" alt="Atualizar" title="Atualizar" style="background-color: #173B21; border-color: #173B21; margin-top: 4%">
						Atualizar
					</button>
				</div>
				<div class="col-2">
					<a class="btn btn-secondary w-100" style="background-color: darkred; border-color: darkred; margin-top: 4%" alt="Excluir" title="Excluir" href="/GoldenFit_war/controlador?acao=excluir&viewHelper=ExcluirGrupoPrecificacaoVH&id=<%=grupo.getId()%>">
						Excluir
					</a>
				</div>
			</div>
		</form>
		<%}
		} %>
	</div>
</div>
</div>
<script>
	document.getElementById('formCadastroPrecificacao').addEventListener('submit', function(event) {
		var descricao = document.getElementById('descricao');
		var margemLucro = document.getElementById('margemLucro');
		var isValid = true;

		if (descricao.value.trim() === '') {
			document.getElementById('descricaoError').style.display = 'block';
			descricao            .style.borderColor = 'red'; // Define a borda do campo como vermelha
			isValid = false; // Define que o formulário não é válido
		} else {
			document.getElementById('descricaoError').style.display = 'none';
			descricao.style.borderColor = ''; // Remove a cor da borda
		}

		if (margemLucro.value.trim() === '' || isNaN(margemLucro.value.trim()) || parseFloat(margemLucro.value.trim()) < 1) {
			document.getElementById('margemLucroError').style.display = 'block';
			margemLucro.style.borderColor = 'red';
			isValid = false;
		} else {
			document.getElementById('margemLucroError').style.display = 'none';
			margemLucro.style.borderColor = '';
		}

		if (!isValid) {
			event.preventDefault(); // Impede o envio do formulário se não for válido
		}
	});
</script>
</body>
</html>


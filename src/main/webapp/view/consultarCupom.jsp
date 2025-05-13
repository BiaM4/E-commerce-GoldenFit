<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List, br.com.fatec.goldenfit.model.Cupom, br.com.fatec.goldenfit.model.enums.TipoCupom" %>
<!DOCTYPE html>
<html lang="pt-br">

<c:import url="template-head-admin.jsp"/>
<c:url value="/controlador" var="stub"/>
<%
    List<Cupom> cupons = (List<Cupom>) request.getAttribute("cupons");
    List<TipoCupom> tipos = TipoCupom.getTiposCupom();
%>
<body>
<c:import url="template-header-admin.jsp"/>

<div class="container" style="margin-top: 5%">
    <h1 style="color:#173B21;text-align: center; font-size: 30px; margin-top: 10%; margin-bottom:2%">LISTAGEM DE
        CUPONS</h1>
    <div style="background-color: #173B21; height: 5px; margin-bottom: 1%"></div>
    <div class="card-body">

        <div class="row">

            <div class="col-1">
                <p class="h5">Código</p>
            </div>
            <div class="col-2">
                <p class="h5">Nome</p>
            </div>
            <div class="col-1">
                <p class="h5">Valor</p>
            </div>
            <div class="col-2">
                <p class="h5">Validade</p>
            </div>
            <div class="col-2">
                <p class="h5">Tipo</p>
            </div>
            <div class="col-2">
                <p class="h5">Status</p>
            </div>
            <div class="col-1">
                <p class="h5" style="color: #FFF;"></p>
            </div>
            <div class="col-1">
                <p class="h5" style="color: #FFF;"></p>
            </div>
        </div>


        <div class="row d-flex align-items-center">

            <%
                if (cupons != null) {
                    for (Cupom cupom : cupons) {
            %>
            <form action="${stub}" method="post" onsubmit="return validarFormulario()" novalidate>
                <div class="row pt-2 mb-2 d-flex align-items-center">

                    <div class="col-1">
                        <input class="form-control mt-2" type="text" id="codigo"
                               name="codigo" value="<%=cupom.getCodigo()%>" required="true" min="1"/>
                    </div>

                    <div class="col-2">
                        <input class="form-control mt-2" type="text" id="nome"
                               name="nome" value="<%=cupom.getNome()%>" required="true"/>
                    </div>


                    <div class="col-1">
                        <input class="form-control mt-2" type="text" id="valor"
                               name="valor" value="<%=cupom.getValor()%>" required="true"/>
                    </div>


                    <div class="col-2">
                        <fmt:formatDate var="fmtDate" value="<%=cupom.getValidade()%>" pattern="dd/MM/yyyy"/>
                        <input class="form-control mt-2" type="text" id="validade" name="validade" value="${fmtDate}"/>
                    </div>

                    <div class="col-2">
                        <select class="form-control mt-2" name="tipo" id="tipo" required="true">
                            <option value="">Escolha...</option>
                            <%
                                if (tipos != null) {
                                    for (TipoCupom tipo : tipos) {
                            %>
                            <option value="<%=tipo.name()%>" <%= cupom.getTipo().name() == tipo.name() ?
                                    "selected" : ""%>><%=tipo.getDescricao() %>
                            </option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <div class="col-2">
                        <select class="form-control mt-2" name="status" id="status" required="true">
                            <option value="true" <%= cupom.isStatus() ? "selected" : ""%>>Disponível</option>
                            <option value="false" <%= !cupom.isStatus() ? "selected" : ""%>>Utilizado</option>
                        </select>
                    </div>

                    <input type="hidden" name="id" value="<%=cupom.getId()%>"/>
                    <input type="hidden" name="acao" value="alterar"/>
                    <input type="hidden" name="viewHelper" value="AlterarCupomVH"/>
                    <div class="col-1">
                        <button type="submit" class="btn btn-secondary w-20 text-white" alt="Atualizar"
                                title="Atualizar"
                                style="background-color: #173B21; border-color: #173B21; margin-top: 4%">
                            Atualizar
                        </button>
                    </div>
                    <div class="col-1">
                        <a class="btn btn-secondary w-100"
                           style="background-color: darkred; border-color: darkred; margin-top: 4%"
                           alt="Excluir endereço" title="Excluir endereço"
                           href="/GoldenFit_war/controlador?acao=excluir&viewHelper=ExcluirCupomVH&id=<%=cupom.getId()%>">
                            Excluir
                        </a>
                    </div>
                </div>
            </form>
            <%
                    }
                }
            %>
        </div>
    </div>
</div>
</body>
</html>

<script>
    function validarFormulario() {
        var inputs = document.querySelectorAll('input[required], select[required]');
        var valido = true;

        inputs.forEach(function(input) {
            if (!input.value.trim()) {
                input.style.borderColor = 'red';
                if (!input.nextElementSibling || input.nextElementSibling.className !== 'mensagem-obrigatoria') {
                    var mensagem = document.createElement('span');
                    mensagem.className = 'mensagem-obrigatoria';
                    mensagem.style.color = 'red';
                    mensagem.textContent = 'Campo obrigatório';
                    input.parentNode.appendChild(mensagem);
                }
                valido = false;
            } else {
                input.style.borderColor = '';
                var mensagem = input.nextElementSibling;
                if (mensagem && mensagem.className === 'mensagem-obrigatoria') {
                    mensagem.parentNode.removeChild(mensagem);
                }
            }
        });

        var validadeInput = document.getElementById('validade');
        if (validadeInput && validadeInput.hasAttribute('required')) {
            var validadeValue = validadeInput.value.trim();
            if (!validadeValue) {
                validadeInput.style.borderColor = 'red';
                if (!validadeInput.nextElementSibling || validadeInput.nextElementSibling.className !== 'mensagem-obrigatoria') {
                    var mensagemValidade = document.createElement('span');
                    mensagemValidade.className = 'mensagem-obrigatoria';
                    mensagemValidade.style.color = 'red';
                    mensagemValidade.textContent = 'Campo obrigatório';
                    validadeInput.parentNode.appendChild(mensagemValidade);
                }
                valido = false;
            } else {
                // Valida o formato da data (dd/mm/aaaa)
                var dataRegex = /^\d{2}\/\d{2}\/\d{4}$/;
                if (!dataRegex.test(validadeValue)) {
                    validadeInput.style.borderColor = 'red';
                    if (!validadeInput.nextElementSibling || validadeInput.nextElementSibling.className !== 'mensagem-obrigatoria') {
                        var mensagemValidade = document.createElement('span');
                        mensagemValidade.className = 'mensagem-obrigatoria';
                        mensagemValidade.style.color = 'red';
                        mensagemValidade.textContent = 'Formato de data inválido (dd/mm/aaaa)';
                        validadeInput.parentNode.appendChild(mensagemValidade);
                    }
                    valido = false;
                } else {
                    validadeInput.style.borderColor = '';
                    var mensagemValidade = validadeInput.nextElementSibling;
                    if (mensagemValidade && mensagemValidade.className === 'mensagem-obrigatoria') {
                        mensagemValidade.parentNode.removeChild(mensagemValidade);
                    }
                }
            }
        }

        return valido;
    }
</script>

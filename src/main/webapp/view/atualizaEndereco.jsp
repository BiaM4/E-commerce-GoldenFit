<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page
        import="br.com.fatec.goldenfit.model.Endereco, br.com.fatec.goldenfit.model.Cidade,java.util.List, br.com.fatec.goldenfit.model.enums.Estado" %>

<c:url value="/controlador" var="stub"/>

<!DOCTYPE html>
<html>
<head>
    <c:import url="template-head.jsp"/>
    <title>Atualiza Endereço</title>
    <style>
        /* Estilo para campos obrigatórios em vermelho */
        .invalid-input {
            border-color: red !important;
        }
    </style>
</head>

<body onload="selecionarCidade()">
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
    Endereco endereco = (Endereco) request.getSession().getAttribute("endereco");
    List<Estado> estados = Estado.getEstadosOrdenados();
%>
<body>

<script type="text/javascript">
    window.onload = function () {
        const selectEstado = document.getElementById("estado");
        carregarCidades(selectEstado, '<%=endereco.getCidade().getNome()%>');

        const selectTipoEndereco = document.getElementById("tipoEndereco");
        const tipoEnderecoAtual = '<%=endereco.getTipoEndereco().name()%>';
        if (tipoEnderecoAtual && tipoEnderecoAtual === "Residencial") {
            selectTipoEndereco.setAttribute('disabled', "true");
        }
    };

</script>
<div class="container" style="margin-top: 5%; width: 80%">
    <div>
        <h1 style="color:#173B21;text-align: center; font-size: 35px;">ATUALIZAR ENDEREÇO</h1>
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
                                                                                          value="<%=endereco.getDescricao()%>"
                                                                                          id="descricaoEndereco"
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
                                    <option value="Rua" <%=endereco.getTipoLogradouro().name() == "Rua" ? "selected" : ""%>>
                                        Rua
                                    </option>
                                    <option value="Travessa" <%=endereco.getTipoLogradouro().name() == "Travessa" ? "selected" : ""%>>
                                        Travessa
                                    </option>
                                    <option value="Avenida" <%=endereco.getTipoLogradouro().name() == "Avenida" ? "selected" : ""%>>
                                        Avenida
                                    </option>
                                    <option value="Alameda" <%=endereco.getTipoLogradouro().name() == "Alameda" ? "selected" : ""%>>
                                        Alameda
                                    </option>
                                    <option value="Estrada" <%=endereco.getTipoLogradouro().name() == "Estrada" ? "selected" : ""%>>
                                        Estrada
                                    </option>
                                    <option value="Rodovia" <%=endereco.getTipoLogradouro().name() == "Rodovia" ? "selected" : ""%>>
                                        Rodovia
                                    </option>
                                    <option value="Jardim" <%=endereco.getTipoLogradouro().name() == "Jardim" ? "selected" : ""%>>
                                        Jardim
                                    </option>
                                </select>
                            </div>
                        </div>

                        <div class="row d-flex align-items-center" style="margin-bottom: 2%">
                            <div class="col-md-12">
                                Logradouro: <strong style="color:#ff253a">*</strong><input class="form-control"
                                                                                           type="text" name="logradouro"
                                                                                           id="logradouro"
                                                                                           value="<%=endereco.getLogradouro()%>"
                                                                                           required="true"/>
                            </div>
                        </div>

                        <div class="row d-flex align-items-center" style="margin-bottom: 1%">
                            <div class="col-md-3">
                                Nº: <strong style="color:#ff253a">*</strong><input class="form-control" type="text"
                                                                                   name="numeroEndereco"
                                                                                   id="numeroEndereco"
                                                                                   required="true"
                                                                                   value="<%=endereco.getNumero()%>"/>
                            </div>
                            <div class="col-md-6">
                                Complemento: <input class="form-control" type="text" name="complemento" id="complemento"
                                                    value="<%=endereco.getComplemento() != null ? endereco.getComplemento() : ""%>"
                            />
                            </div>
                            <div class="col-3 mb-2">
                                <label for="tipoResidencia">Tipo de residência: <strong style="color:#ff253a">*</strong></label>
                                <select class="form-control" name="tipoResidencia" id="tipoResidencia"
                                        required="true">
                                    <option value="">Escolha...</option>
                                    <option value="Casa" <%=endereco.getTipoResidencia().name() == "Casa" ? "selected" : ""%>>
                                        Casa
                                    </option>
                                    <option value="Apartamento" <%=endereco.getTipoResidencia().name() == "Apartamento" ? "selected" : ""%>>
                                        Apartamento
                                    </option>
                                    <option value="Flat" <%=endereco.getTipoResidencia().name() == "Flat" ? "selected" : ""%>>
                                        Flat
                                    </option>
                                    <option value="Kitnet" <%=endereco.getTipoResidencia().name() == "Kitnet" ? "selected" : ""%>>
                                        Kitnet
                                    </option>
                                    <option value="Loft" <%=endereco.getTipoResidencia().name() == "Loft" ? "selected" : ""%>>
                                        Loft
                                    </option>
                                </select>
                            </div>
                        </div>

                        <div class="row d-flex align-items-center" style="margin-bottom: 1%">
                            <div class="col-md-6">
                                Bairro: <strong style="color:#ff253a">*</strong><input class="form-control" type="text"
                                                                                       name="bairro" id="bairro"
                                                                                       value="<%=endereco.getBairro()%>"
                                                                                       required="true"/>
                            </div>
                            <div class="col-md-3">
                                CEP: <strong style="color:#ff253a">*</strong><input class="form-control" type="text"
                                                                                    name="cep" id="cep"
                                                                                    value="<%=endereco.getCep()%>"
                                                                                    required="true"/>
                            </div>
                            <div class="col-3 mb-2">
                                <label for="estado">Estado: <strong style="color:#ff253a">*</strong></label>
                                <select class="form-control" name="estado" id="estado"
                                        value=<%=endereco.getCidade().getEstado()%>
                                                required="true" onchange="carregarCidades(this);">
                                    <option value="">Escolha o estado...</option>
                                    <% for (Estado estado : estados) { %>
                                    <option value=<%=estado.getSigla()%> <%=endereco.getCidade().getEstado() == estado ? "selected" : ""%>><%=estado.getNome()%>
                                    </option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="row d-flex align-items-center" style="margin-bottom: 1%">
                                <div class="col-md-6 mb-2">
                                    Cidade: <strong style="color:#ff253a">*</strong><input class="form-control"
                                                                                           type="text" name="cidade"
                                                                                           id="cidade"
                                                                                           value="<%=endereco.getCidade().getNome()%>"
                                                                                           required="true"/>
                                </div>

                                <div class="col-md-6 mb-2" style="margin-top: 1%">
                                    Tipo de endereço: <strong style="color:#ff253a">*</strong><select
                                        class="form-control" name="tipoEndereco" id="tipoEndereco"
                                        required="true">
                                    <option value="">Escolha...</option>
                                    <option value="Cobranca" <%=endereco.getTipoEndereco().name() == "Cobranca" ? "selected" : ""%> >
                                        Cobrança
                                    </option>
                                    <option value="Entrega" <%=endereco.getTipoEndereco().name() == "Entrega" ? "selected" : ""%>>
                                        Entrega
                                    </option>
                                    <%if (endereco.getTipoEndereco().name() == "Residencial") {%>
                                    <option value="Residencial" selected="true">Residencial</option>
                                    <%} %>
                                </select>
                                </div>
                            </div>
                            <div class="row d-flex align-items-center">
                                <div>
                                    Observação: <strong style="color:#ff253a">*</strong><textarea class="form-control"
                                                                                                  type="text"
                                                                                                  required="true"
                                                                                                  maxlength="200"
                                                                                                  name="observacaoEndereco"
                                                                                                  id="observacaoEndereco"><%=endereco.getObservacao() != null ? endereco.getObservacao() : ""%> </textarea>
                                </div>
                            </div>
                            <input type="hidden" name="acao" value="alterar"/>
                            <input type="hidden" name="viewHelper" value="AlterarEnderecoVH"/>
                            <input type="hidden" name="id" value="<%=endereco.getId()%>"/>


                            <div style="text-align: center;">
                                <div class="d-flex justify-content-center">
                                    <button type="submit" class="btn btn-secondary w-26 text-white" alt="Salvar"
                                            title="Salvar"
                                            style="background-color: #173B21; border-color: #173B21; margin-top: 15px; margin-bottom: 20px; margin-right: 10px;">
                                        <i class="fas fa-save"></i> Salvar
                                    </button>
                                </div>
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
            // Verifica se o campo é obrigatório e está vazio
            if (input.hasAttribute("required") && !input.value.trim()) {
                isValid = false;
                input.classList.add("invalid-input");
            } else {
                input.classList.remove("invalid-input");
            }
        });

        if (!isValid) {
            // Se algum campo obrigatório estiver vazio, retorna false para impedir o envio do formulário
            return false;
        }

        // Se todos os campos obrigatórios estiverem preenchidos, o formulário pode ser enviado
        return true;
    }
</script>

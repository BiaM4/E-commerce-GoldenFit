<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.List, br.com.fatec.goldenfit.model.enums.Estado" %>
<%@page import="br.com.fatec.goldenfit.util.Conversao" %>
<c:url value="/controlador" var="stub"/>

<!DOCTYPE html>
<html>
<head>
    <c:import url="template-head.jsp"/>
    <title>Cadastro</title>

    <style>
        .campo-invalido {
            border-color: #ff0000;
        }

        .mensagem-erro {
            color: #ff0000;
            font-size: 12px;
            margin-top: 5px;
        }
    </style>
</head>
<body>


<header>
    <script src="javascript/validacaoFormulario/validaFormCadastroCliente.js" charset="UTF-8"></script>
    <script type="text/javascript" src="javascript/carregaCidades.js" charset="UTF-8"></script>
    <script src="javascript/validacaoFormulario/validaFormRedefinicaoSenha.js" charset="UTF-8"></script>

</header>
<%
    List<Estado> estados = Estado.getEstadosOrdenados();
%>

<nav class="navbar" style="background-color: #173B21; height: 60px ">
    <div class="d-flex flex-row mb-3"
         style="justify-content: space-between; align-items: center; margin-top: 5px; margin-left: 30px">
        <div class="d-flex flex-row mb-3" style="justify-content: center; align-items: center;">
            <img src="images/LogoGold.png" style="height: 35px; width: 35px">
            <a class="navbar-brand" href="/GoldenFit_war/view/index" style="color:#ECBD69; margin-right: 70px ">GOLDENFIT</a>
        </div>

        <div class="d-flex flex-row mb-3" style="justify-content: flex-end; align-items: center;margin-left: 280%">
            <img src="images/user.png" style="height: 30px; width: 30px; margin-left: 30px">
            <a class="navbar-brand" href="login.jsp"
               style="color:#ffffff ; font-size: 12px; margin-left: 20px">Entrar</a>
        </div>
    </div>
</nav>
<div style="background-color: #ECBD69; height: 5px;"></div>


<div style="padding-left: 20%; padding-right: 20%; margin-top: 5%">

    <div>
        <h1 style="color:#173B21;text-align: center; font-size: 30px;">CADASTRO</h1>
    </div>
    <div style="background-color: #173B21; height: 5px;margin-top: 5%; margin-bottom: 5%"></div>
    <div class="card-body">
        <div class="row">
            <div class="col-12 mb-3">

                <form id="formCadastroCliente" action="${stub }" method="post" novalidate
                      onsubmit="return validarFormulario();">
                    <div class="row mb-2">
                        <h4 style="color:#173B21;font-size: 30px;margin-bottom: 3%">Dados pessoais:</h4>
                    </div>

                    <div class="row d-flex justify-content-between">
                        <div class="col-md-5">Nome:<strong style="color:#ff253a">*</strong><input class="form-control"
                                                                                                  type="text"
                                                                                                  name="nome" id="nome"
                                                                                                  required="true"
                                                                                                  placeholder="Digite seu nome..."/>
                        </div>
                        <div class="col-md-5">Sobrenome:<strong style="color:#ff253a">*</strong> <input
                                class="form-control" type="text" name="sobrenome"
                                id="sobrenome" required="true" placeholder="Digite seu sobrenome..."/></div>
                        <div class="col-md-2"><label for="cidade">Gênero:<strong
                                style="color:#ff253a">*</strong></label>
                            <select class="form-control" name="genero" id="genero" required="true">
                                <option value="">Escolha...</option>
                                <option value="FEMININO">Feminino</option>
                                <option value="MASCULINO">Masculino</option>
                            </select>
                        </div>
                    </div>

                    <div class="row d-flex justify-content-between">
                        <div class="col-md-2">DDD:<strong style="color:#ff253a">*</strong> <input class="form-control"
                                                                                                  type="text"
                                                                                                  name="dddResidencial"
                                                                                                  id="dddResidencial"
                                                                                                  required="true"
                                                                                                  placeholder="00"/>
                        </div>
                        <div class="col-md-4">Telefone residencial:<strong style="color:#ff253a">*</strong> <input
                                class="form-control" type="tel" name="numeroTelResidencial" id="numeroTelResidencial"
                                placeholder="0000-0000" required="true"/></div>
                        <div class="col-md-2">DDD:<strong style="color:#ff253a">*</strong> <input class="form-control"
                                                                                                  type="text"
                                                                                                  name="dddCelular"
                                                                                                  id="dddCelular"
                                                                                                  required="true"
                                                                                                  placeholder="00"/>
                        </div>
                        <div class="col-md-4">Telefone celular:<strong style="color:#ff253a">*</strong> <input
                                class="form-control" type="tel" name="numeroTelCelular" id="numeroTelCelular"
                                placeholder="00000-0000"/></div>
                    </div>


                    <div class="row d-flex justify-content-between">
                        <div class="col-md-6">Data de Nascimento:<strong style="color:#ff253a">*</strong>
                            <input class="form-control" type="text" name="dataNascimento" id="dataNascimento"
                                   placeholder="dd/mm/aaaa" required="true"/>
                        </div>
                        <div class="col-md-6">CPF:<strong style="color:#ff253a">*</strong> <input class="form-control"
                                                                                                  type="text" name="cpf"
                                                                                                  id="cpf"
                                                                                                  placeholder="000.000.000-00"
                                                                                                  required="true"/>
                        </div>
                    </div>

                    <br/>

                    <div style="background-color: #173B21; height: 5px;margin-top: 5%; margin-bottom: 5%"></div>

                    <div class="row mb-2">
                        <h4 style="color:#173B21;font-size: 30px;margin-bottom: 3%">Endereço Residencial:</h4>
                    </div>

                    <div class="row">
                        <div>
                            Descrição:<strong style="color:#ff253a">*</strong> <input class="form-control" type="text"
                                                                                      name="descricaoEndereco"
                                                                                      id="descricaoEndereco"
                                                                                      placeholder="Exemplo: Minha Casa"
                                                                                      required="true" maxlength="120"/>
                        </div>
                    </div>

                    <div class="row d-flex">
                        <div class="col-3">
                            <label for="tipoLogradouro">Tipo de logradouro:<strong
                                    style="color:#ff253a">*</strong></label> <select required="true"
                                                                                     class="form-control"
                                                                                     name="tipoLogradouro"
                                                                                     id="tipoLogradouro">
                            <option value="">Escolha...</option>
                            <option value="Rua">Rua</option>
                            <option value="Travessa">Travessa</option>
                            <option value="Avenida">Avenida</option>
                            <option value="Alameda">Alameda</option>
                            <option value="Estrada">Estrada</option>
                            <option value="Rodovia">Rodovia</option>
                            <option value="Jardim">Jardim</option>
                        </select>
                        </div>
                        <div class="col-md-9">
                            Logradouro:<strong style="color:#ff253a">*</strong> <input class="form-control" type="text"
                                                                                       name="logradouro" id="logradouro"
                                                                                       required="true"
                                                                                       placeholder="Digite o logradouro..."/>
                        </div>
                    </div>

                    <div class="row d-flex">
                        <div class="col-3">
                            Nº:<strong style="color:#ff253a">*</strong> <input class="form-control" type="text"
                                                                               name="numeroEndereco" id="numeroEndereco"
                                                                               required="true" placeholder="0000"/>
                        </div>
                        <div class="col-6">
                            Complemento: <input class="form-control" type="text" name="complemento" id="complemento"
                                                placeholder="Digite o complemento..."/>
                        </div>

                        <div class="col-3">
                            <label for="tipoResidencia">Tipo de residência:<strong
                                    style="color:#ff253a">*</strong></label>
                            <select class="form-control" name="tipoResidencia" id="tipoResidencia" required="true">
                                <option value="">Escolha...</option>
                                <option value="Casa">Casa</option>
                                <option value="Apartamento">Apartamento</option>
                                <option value="Flat">Flat</option>
                                <option value="Kitnet">Kitnet</option>
                                <option value="Loft">Loft</option>
                            </select>
                        </div>
                    </div>

                    <div class="row d-flex">
                        <div class="col-4">
                            Bairro:<strong style="color:#ff253a">*</strong> <input class="form-control" type="text"
                                                                                   name="bairro" id="bairro"
                                                                                   required="true"
                                                                                   placeholder="Digite o bairro..."/>
                        </div>
                        <div class="col-2">
                            CEP: <strong style="color:#ff253a">*</strong><input class="form-control" type="text"
                                                                                name="cep" id="cep"
                                                                                placeholder="00000-000"
                                                                                required="true"/>
                        </div>
                        <div class="col-3">
                            <label for="estado">Estado:<strong style="color:#ff253a">*</strong></label>
                            <select class="form-control" name="estado" id="estado" required="true"
                                    onchange="carregarCidades(this);">
                                <option value="">Escolha o estado...</option>
                                <% for (Estado estado : estados) { %>
                                <option value=<%=estado.getSigla()%>><%=estado.getNome()%>
                                </option>
                                <% } %>
                            </select>
                        </div>
                        <div class="col-3">
                            <label for="cidade">Cidade:<strong style="color:#ff253a">*</strong></label> <select
                                class="form-control" name="cidade" id="cidade" required="true">
                            <option value="">Escolha...</option>
                        </select>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div>
                            Observação:<strong style="color:#ff253a">*</strong> <textarea class="form-control"
                                                                                          type="text" maxlength="200"
                                                                                          name="observacaoEndereco"
                                                                                          id="observacaoEndereco"></textarea>
                        </div>
                    </div>


                    <div style="background-color: #173B21; height: 5px;margin-top: 5%; margin-bottom: 5%"></div>

                    <div class="row">
                        <h4 style="color:#173B21;font-size: 30px;margin-bottom: 3%">Dados da conta:</h4>
                    </div>

                    <div class="col-md-12">E-mail:<strong style="color:#ff253a">*</strong> <input class="form-control"
                                                                                                  type="email"
                                                                                                  name="email"
                                                                                                  id="email"
                                                                                                  required="true"
                                                                                                  placeholder="exemplo@exemplo.com.br"/>
                    </div>

                    <div class="d-flex justify-content-between">
                        <div style=" width: 45%">
                            <label for="senha" class="form-label">Senha:<strong style="color:#ff253a">*</strong></label>
                            <input class="form-control" type="password" name="senha" id="senha"
                                   required="true"/>
                        </div>

                        <div style=" width: 45%">
                            <label for="confirmacaoSenha" class="form-label">Confirme a senha:<strong
                                    style="color:#ff253a">*</strong></label>
                            <input class="form-control" type="password" name="confirmacaoSenha"
                                   id="confirmacaoSenha" required="true"/>
                        </div>
                    </div>
                    <br/>

                    <div><input type="hidden" name="score" value="100"/></div>
                    <div><input type="hidden" name="status" value="true"/></div>

                    <input type="hidden" name="acao" value="salvar"/>
                    <input type="hidden" name="viewHelper" value="CadastroClienteVH"/>
                    <br/>
                    <div>
                        <button style="background-color: #173B21;margin-left:40%; margin-top: 5%; margin-bottom: 10%"
                                class="btn btn-secondary w-25" type="submit" title="Salvar" alt="Salvar">Cadastrar
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<script>
    function validarFormulario() {
        var camposObrigatorios = ["nome", "sobrenome", "genero", "dddResidencial", "numeroTelResidencial", "dddCelular", "numeroTelCelular", "dataNascimento", "cpf", "descricaoEndereco", "tipoLogradouro", "logradouro", "numeroEndereco", "tipoResidencia", "bairro", "cep", "estado", "cidade", "observacaoEndereco", "email", "senha", "confirmacaoSenha"];
        var senhaRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$*&@#!?$%&])[A-Za-z\d$*&@#!?$%&]{8,}$/;

        var formularioValido = true;

        for (var i = 0; i < camposObrigatorios.length; i++) {
            var campo = document.getElementById(camposObrigatorios[i]);
            var valorCampo = campo.value.trim();

            if (valorCampo === "") {
                campo.classList.add("campo-invalido");

                var mensagemErro = document.createElement("div");
                mensagemErro.textContent = "Campo obrigatório";
                mensagemErro.classList.add("mensagem-erro");

                if (!campo.parentNode.querySelector(".mensagem-erro")) {
                    campo.parentNode.appendChild(mensagemErro);
                }

                formularioValido = false;
            } else {
                campo.classList.remove("campo-invalido");
                var mensagemErro = campo.parentNode.querySelector(".mensagem-erro");
                if (mensagemErro) {
                    campo.parentNode.removeChild(mensagemErro);
                }
            }
        }

        var senha = document.getElementById("senha").value;
        var confirmacaoSenha = document.getElementById("confirmacaoSenha").value;

        if (!senha.match(senhaRegex)) {
            document.getElementById("senha").classList.add("campo-invalido");

            var mensagemErroSenha = document.createElement("div");
            mensagemErroSenha.textContent = "A senha deve conter ao menos 1 letra maiúscula, 1 letra minúscula, 1 número e 1 caractere especial ($*&@#!?$%&), com no mínimo 8 caracteres.";
            mensagemErroSenha.classList.add("mensagem-erro");

            var campoSenha = document.getElementById("senha");
            if (!campoSenha.parentNode.querySelector(".mensagem-erro")) {
                campoSenha.parentNode.appendChild(mensagemErroSenha);
            }

            formularioValido = false;
        } else {
            document.getElementById("senha").classList.remove("campo-invalido");
            var mensagemErroSenha = document.getElementById("senha").parentNode.querySelector(".mensagem-erro");
            if (mensagemErroSenha) {
                mensagemErroSenha.parentNode.removeChild(mensagemErroSenha);
            }
        }

        if (senha !== confirmacaoSenha) {
            document.getElementById("confirmacaoSenha").classList.add("campo-invalido");

            var mensagemErroConfirmacaoSenha = document.createElement("div");
            mensagemErroConfirmacaoSenha.textContent = "A senha e a confirmação de senha não coincidem.";
            mensagemErroConfirmacaoSenha.classList.add("mensagem-erro");

            var campoConfirmacaoSenha = document.getElementById("confirmacaoSenha");
            if (!campoConfirmacaoSenha.parentNode.querySelector(".mensagem-erro")) {
                campoConfirmacaoSenha.parentNode.appendChild(mensagemErroConfirmacaoSenha);
            }

            formularioValido = false;
        } else {
            document.getElementById("confirmacaoSenha").classList.remove("campo-invalido");
            var mensagemErroConfirmacaoSenha = document.getElementById("confirmacaoSenha").parentNode.querySelector(".mensagem-erro");
            if (mensagemErroConfirmacaoSenha) {
                mensagemErroConfirmacaoSenha.parentNode.removeChild(mensagemErroConfirmacaoSenha);
            }
        }

        return formularioValido;
    }
</script>

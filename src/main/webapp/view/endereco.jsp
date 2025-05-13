<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List,br.com.fatec.goldenfit.model.Cliente,br.com.fatec.goldenfit.model.Endereco,br.com.fatec.goldenfit.model.Cartao" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Endereços</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="view/css/stylesView.css">

    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<fmt:setLocale value="pt_BR"/>
<%
    Cliente cliente = (Cliente) request.getSession().getAttribute("clienteLogado");
%>

    <nav class="navbar" style="background-color: #173B21; height: 60px ">
        <div class="d-flex flex-row mb-3" style="justify-content: space-between; align-items: center; margin-top: 5px; margin-left: 30px">
            <div class="d-flex flex-row mb-3" style="justify-content: center; align-items: center;">
                <img src="images/LogoGold.png" style="height: 35px; width: 35px">
                <a class="navbar-brand" href="/GoldenFit_war/view/index" style="color:#ECBD69; margin-right: 70px ">GOLDENFIT</a>
            </div>

            <div class="d-flex flex-row mb-3" style="justify-content: flex-end; align-items: center;margin-left: 210%">
                <div class="btn-group">
                    <img src="images/user.png" style="height: 30px; width: 30px;">
                    <button style="color:#ffffff" type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        Olá, ${sessionScope.clienteLogado.nome}
                    </button>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="listaClientes.jsp">Meu perfil</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarCartaoVH">Meus cartões</a></li>
                        <li><a class="dropdown-item" href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarPedidosVH">Meus pedidos</a></li>
                        <li><a class="dropdown-item" href="/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarCuponsVH">Meus cupons</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="/GoldenFit_war/controlador?acao=Logout">Logout</a></li>
                    </ul>
                </div>
                <a href="carrinho.jsp" style="text-decoration: none;">
                    <img src="images/shopping-bag.png" alt="Shopping Bag" style="height: 30px; width: 30px; margin-left: 10px; cursor: pointer;">
                </a>
            </div>
        </div>
    </nav>

    <div>
        <div style="background-color: #ECBD69; height: 5px;"></div>

    <div>
        <div style="justify-content: space-between; margin-left: 5%" class="d-flex flex-row mb-3">
            <h2 style="color:#173B21;font-size: 28px;margin-top: 5%;">ENDEREÇOS</h2>
            <a style="background-color: #173B21;color:#ffffff;width:10%;margin-top:5%;margin-right:5%" href="cadastraEndereco.jsp" class="btn white" alt="Novo cartão" title="Novo cartão">Adicionar</a>
        </div>
    </div>

  <div class="container" style="margin-top: 5%; width: 80%">
    <div class="card-body">
        <div class="row">
            <div>
                <div class="col-12 mb-3">
                    <div class="row d-flex justify-content-between">
                        <table class="table" id="tabelaCartoes">
                            <thead class="table table-striped ">
                            <tr>
                                <th>Descrição</th>
                                <th>Tipo</th>
                                <th>Logradouro</th>
                                <th>Número</th>
                                <th>Cidade</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <%if(request.getSession().getAttribute("enderecos")!=null){
                                List<Endereco> enderecos = (List<Endereco>)request.getSession().getAttribute("enderecos");%>
                            <% for (Endereco endereco : enderecos) {
                                System.out.println(endereco.getDescricao());%>
                            <tr>
                                <td><%=endereco.getDescricao()%></td>
                                <td><%=endereco.getTipoEndereco().name()%></td>
                                <td><%=endereco.getLogradouro()%></td>
                                <td><%=endereco.getNumero()%></td>
                                <td><%=endereco.getCidade().getNome()%>-<%=endereco.getCidade().getEstado().getSigla()%></td>
                                <td>
                                    <div style="margin-left: -120px">
                                        <a class="btn btn-secondary w-20" style="margin-left: 30px" alt="Alterar endereço" title="Alterar endereço"
                                           href="/GoldenFit_war/controlador?acao=consultar&viewHelper=PreparaAlteracaoEnderecoVH&id=<%=endereco.getId()%>">
                                            Editar
                                        </a>
                                        <%if(endereco.getTipoEndereco().name() != "Residencial") {%>
                                        <a class="btn btn-secondary w-20" style="background-color: darkred; border-color: darkred;" alt="Excluir endereço" title="Excluir endereço"
                                           href="/GoldenFit_war/controlador?acao=excluir&viewHelper=ExcluirEnderecoVH&id=<%=endereco.getId()%>">
                                            Excluir
                                        </a>
                                        <% } %>
                                    </div>
                                </td>
                            </tr>
                            <% } %>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
          </div>
       </div>
    </div>
  </div>

</body>
</html>

<%@ page import="br.com.fatec.goldenfit.model.Categoria" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.fatec.goldenfit.model.Produto" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Currency" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pt-br">
<fmt:setLocale value="pt_BR"/>
<c:url value="/controlador" var="stub"/>
<c:import url="template-head-admin.jsp" />

<%
    List<Produto> produtos = (List<Produto>) request.getSession().getAttribute("produtos");
%>

<body>
    <c:import url="template-header-admin.jsp" />
    <div class="container" style="margin-top: 5%">
        <h1 style="color:#173B21;text-align: center; font-size: 30px; margin-bottom:3%">CONSULTAR PRODUTOS</h1>
        <div style="background-color: #173B21; height: 5px;margin-top: 3%; margin-bottom: 3%"></div>
        <div class="card shadow mb-5 pb-4">
            <div class="card-body">
                <form id="formConsultaCliente" action="${stub}" method="post" novalidate style="margin-left: 3%">
                    <div class="row pt-2 mb-2 d-flex align-items-center">
                        <div class="row mt-4">
                            <div class="row">
                                <div class="col-2">
                                    <p class="h5">Id</p>
                                </div>
                                <div class="col-5">
                                    <p class="h5">Nome</p>
                                </div>
                                <div class="col-5">
                                    <p class="h5">Data de Cadastro</p>
                                </div>
                            </div>
                            <div class="row d-flex align-items-center">
                                <div class="col-2">
                                    <input class="form-control mt-2" type="text" id="codigo" name="codigo"
                                        required="true" />
                                </div>
                                <div class="col-5">
                                    <input class="form-control mt-2" type="text" id="nome" name="nome"
                                        required="true" />
                                </div>
                                <div class="col-5">
                                    <input class="form-control mt-2" type="date" id="dtCadastro"
                                           name="dtCadastro" required="true"/>
                                </div>
                            </div>
                        </div>

                        <div class="row mt-4">
                            <div class="row">
                                <div class="col-3">
                                    <p class="h5">Marca</p>
                                </div>
                                <div class="col-5">
                                    <p class="h5">Gênero</p>
                                </div>
                                <div class="col-4">
                                    <p class="h5">Tamanho</p>
                                </div>
                            </div>
                            <div class="row d-flex align-items-center">
                                <div class="col-3">
                                    <input class="form-control" type="text" id="marca" name="marca"
                                        required="true" />
                                </div>
                                <div class="col-5">
                                    <select class="form-control" name="genero" id="genero" required="true">
                                        <option value="">Escolha...</option>
                                        <option value="FEMININO">Feminino</option>
                                        <option value="MASCULINO">Masculino</option>
                                    </select>
                                </div>
                                <div class="col-4">
                                    <select class="form-control" name="tamanho" id="tamanho" required="true">
                                        <option value="">Escolha...</option>
                                        <option value="PP">PP</option>
                                        <option value="P">P</option>
                                        <option value="M">M</option>
                                        <option value="G">G</option>
                                        <option value="GG">GG</option>
                                        <option value="EG">EG</option>
                                        <option value="EGG">EGG</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="row mt-4">
                            <div class="row">
                                <div class="col-5">
                                    <p class="h5">Categoria</p>
                                </div>
                                <div class="col-5">
                                    <p class="h5">Grupo precificação</p>
                                </div>
                                <div class="col-2">
                                    <p class="h5">Status</p>
                                </div>

                            </div>
                            <div class="row">
                                <div class="col-5">
                                    <select class="form-control" id="categoria" name="categoria" required>
                                        <option value="">Escolha...</option>
                                        <c:forEach var="categoria" items="${categorias}">
                                            <option value="${categoria.id}">${categoria.nome}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-5">
                                    <select class="form-control" id="grupoPrecificacao" name="grupoPrecificacao" required>
                                        <option value="">Escolha...</option>
                                        <c:forEach var="grupo" items="${gruposPrecificacao}">
                                            <option value="${grupo.id}">${grupo.descricao}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-2">
                                    <select class="form-control" name="status" id="status" required="true">
                                        <option value="">Escolha...</option>
                                        <option value="Ativo">Ativo</option>
                                        <option value="Inativo">Inativo</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="acao" value="consultar"/>
                        <input type="hidden" name="viewHelper" value="ConsultarProdutosVH"/>
                        <input type="hidden" name="tipoPesquisa" value="filtros"/>
                        <div class="row mt-4 d-flex justify-content-center">
                            <div class="col-2">
                                <button class="btn btn-secondary w-100" id="buscar" type="submit" name="buscar"
                                        title="Buscar" alt="Botão de busca"
                                        style="background-color: #173B21; border-color: #173B21; margin-top: 15px;">
                                    Buscar
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <h1 style="color:#173B21;text-align: center; font-size: 30px; margin-bottom:3%">PRODUTOS FILTRADOS</h1>
        <div style="background-color: #173B21; height: 5px;margin-top: 3%; margin-bottom: 3%"></div>
        <div class="card shadow mb-5 pb-4">
            <div class="card-body">
                <div class="row mt-4">
                    <div class="col-1">
                        <p class="h5">Id</p>
                    </div>
                    <div class="col-2">
                        <p class="h5">Nome</p>
                    </div>
                    <div class="col-2">
                        <p class="h5">Preço</p>
                    </div>
                    <div class="col-2">
                        <p class="h5">Marca</p>
                    </div>
                    <div class="col-2">
                        <p class="h5">Categoria(s)</p>
                    </div>
                    <div class="col-1">
                        <p class="h5">Status</p>
                    </div>
                    <div class="col-2">
                        <p class="h5" style="color: #FFF;"></p>
                    </div>
                </div>
                <% if(produtos != null){
                    for(Produto produto : produtos){%>
                <form action="${stub}" method="get" novalidate>
                    <div class="row pt-2 mb-4 align-items-center" style="margin-top: 1%">
                        <div class="col-1">
                            <p><%=produto.getId()%></p>
                        </div>
                        <div class="col-2">
                            <p><%=produto.getNome()%></p>
                        </div>
                        <%
                            NumberFormat nf = NumberFormat.getCurrencyInstance();
                            nf.setCurrency(Currency.getInstance("BRL"));

                            String precoFormatado = nf.format(produto.getPreco());
                        %>
                        <div class="col-2">
                            <p><%= precoFormatado %></p>
                        </div>

                        <div class="col-2">
                            <p><%=produto.getMarca()%></p>
                        </div>

                        <div class="col-2">
                            <% for (Categoria categoria : produto.getCategoria()) { %>
                            <p><%=categoria.getNome()%></p>
                            <% } %>
                        </div>

                        <div class="col-1">
                            <p><%=produto.getStatus()%></p>
                        </div>

                        <div class="col-1" style="margin-top: -1%">
                            <a class="btn btn-secondary w-100" style="background-color: #173B21; border-color: #173B21" alt="Alterar produto" title="Alterar produto"
                               href="/GoldenFit_war/controlador?acao=consultar&viewHelper=PreparaAlteracaoProdutoVH&id=<%=produto.getId()%>">
                                Editar
                            </a>
                        </div>

                        <div class="col-1" style="margin-top: -1%">
                            <a class="btn btn-secondary w-100" style="background-color: darkred; border-color: darkred" alt="Excluir produto" title="Excluir produto"
                               href="/GoldenFit_war/controlador?acao=excluir&viewHelper=ExcluirProdutoVH&id=<%=produto.getId()%>">
                                Excluir
                            </a>
                        </div>
                    </div>
                </form>
                <% }
                } else { %>
                <p class="h5">Nenhum resultado encontrado</p>
                <% } %>
            </div>
        </div>
    </div>
</body>
</html>
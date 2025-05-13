package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.*;
import br.com.fatec.goldenfit.util.Conversao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExcluirProdutoVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Produto produto = new Produto();
        produto.setId((Conversao.parseStringToInt((request.getParameter("id")))));
        produto.setPesquisa(("id"));
        return produto;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/view/carregarTodosProdutosAtualizados");
    }
}

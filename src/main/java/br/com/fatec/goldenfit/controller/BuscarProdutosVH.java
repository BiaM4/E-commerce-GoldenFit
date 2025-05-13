package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.Produto;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BuscarProdutosVH implements IViewHelper {

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Produto produto = new Produto();
        if (request.getParameter("tipoPesquisa") != null && !request.getParameter("tipoPesquisa").isEmpty()) {
            produto.setPesquisa(request.getParameter("tipoPesquisa"));

            if (request.getParameter("nome") != null && !request.getParameter("nome").isEmpty()) {
                produto.setNome(request.getParameter("nome").trim());
            }
        }
        return produto;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute("produtos", resultado.getEntidades());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/indexFiltro.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

}

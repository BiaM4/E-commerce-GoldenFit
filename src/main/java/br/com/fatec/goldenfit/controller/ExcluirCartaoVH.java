package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.Cartao;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ExcluirCartaoVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Cartao cartao = new Cartao();
        cartao.setId((Conversao.parseStringToInt((request.getParameter("id")))));
        cartao.setPesquisa(("id"));
        return cartao;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/carregarDadosCartoes");
    }
}

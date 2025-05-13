package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.Categoria;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AlterarCategoriaVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Categoria categoria = new Categoria (Conversao.parseStringToInt(request.getParameter("id")),
                              request.getParameter("descricaoAtualizar"), null);
        return categoria;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/view/categoria");
    }
}

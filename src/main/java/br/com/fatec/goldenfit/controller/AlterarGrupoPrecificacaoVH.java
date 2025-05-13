package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.GrupoPrecificacao;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AlterarGrupoPrecificacaoVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        GrupoPrecificacao grupo = new GrupoPrecificacao(Conversao.parseStringToInt(request.getParameter("id")),
                request.getParameter("descricaoAtualizar"), Conversao.parseStringToDouble(request.getParameter("margemLucroAtualizar")), null);

        return grupo;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/view/precificacao");
    }
}

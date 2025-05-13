package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Produto;
import br.com.fatec.goldenfit.model.Result;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class PreparaCadastroMovimentacaoVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Produto produto = new Produto();
        produto.setPesquisa("");
        return produto;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<EntidadeDominio> entidades = resultado.getEntidades();

        if(entidades != null && !entidades.isEmpty()) {
            request.getSession().setAttribute("produtos", entidades);
        }

        response.sendRedirect(request.getContextPath() + "/view/movimentacoes.jsp");
    }
}

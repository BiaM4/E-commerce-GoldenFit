package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.Endereco;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class PreparaAlteracaoEnderecoVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Endereco endereco = new Endereco();
        endereco.setId(Conversao.parseStringToInt(request.getParameter("id")));
        endereco.setPesquisa("id");
        return endereco;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<EntidadeDominio> entidades = resultado.getEntidades();

        if(entidades != null && !entidades.isEmpty()) {
            Endereco endereco = (Endereco) entidades.get(0);
            request.getSession().setAttribute("endereco", endereco);
        }

        response.sendRedirect(request.getContextPath() + "/view/atualizaEndereco.jsp");
    }
}

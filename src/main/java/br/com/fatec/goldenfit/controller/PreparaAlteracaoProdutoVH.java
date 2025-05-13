package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.Endereco;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Produto;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.util.Conversao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PreparaAlteracaoProdutoVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Produto produto = new Produto();
        produto.setId(Conversao.parseStringToInt(request.getParameter("id")));
        produto.setPesquisa("id");

        System.out.println(produto);
        return produto;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<EntidadeDominio> entidades = resultado.getEntidades();

        if(entidades != null && !entidades.isEmpty()) {
            Produto produto = (Produto) entidades.get(0);
            request.getSession().setAttribute("produto", produto);
        }

        response.sendRedirect(request.getContextPath() + "/view/carregarDadosAtualizarProduto");
    }
}

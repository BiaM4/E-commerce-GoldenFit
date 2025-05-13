package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.*;
import br.com.fatec.goldenfit.util.Conversao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public class ConsultarProdutosVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Produto produto = new Produto();

        if(request.getParameter("tipoPesquisa") != null && !request.getParameter("tipoPesquisa").isEmpty() ) {
            produto.setPesquisa(request.getParameter("tipoPesquisa"));

            if (request.getParameter("codigo") != null && !request.getParameter("codigo").isEmpty()) {
                produto.setId(Conversao.parseStringToInt(request.getParameter("codigo").trim()));
            }
            if (request.getParameter("nome") != null && !request.getParameter("nome").isEmpty()) {
                produto.setNome(request.getParameter("nome").trim());
            }
            if (request.getParameter("dtCadastro") != null && !request.getParameter("dtCadastro").isEmpty()) {
                try {
                    System.out.println(request.getParameter("dtCadastro"));
                    produto.setDtCadastro(Conversao.parseStringToDate(request.getParameter("dtCadastro").trim(), "yyyy-MM-dd"));
                    System.out.println(3);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (request.getParameter("marca") != null && !request.getParameter("marca").isEmpty()) {
                produto.setMarca(request.getParameter("marca").trim());
            }
            if (request.getParameter("genero") != null && !request.getParameter("genero").isEmpty()) {
                produto.setGenero(request.getParameter("genero").trim());
            }
            if (request.getParameter("tamanho") != null && !request.getParameter("tamanho").isEmpty()) {
                produto.setTamanho(request.getParameter("tamanho").trim());
            }
            if (request.getParameter("categoria") != null && !request.getParameter("categoria").isEmpty()) {
                produto.setCategoria(request.getParameter("categoria").trim());
            }
            if (request.getParameter("grupoPrecificacao") != null && !request.getParameter("grupoPrecificacao").isEmpty()) {
                String descricao = request.getParameter("grupoPrecificacao").trim();
                GrupoPrecificacao grupoPrecificacao = GrupoPrecificacao.criarGrupoPrecificacao(descricao);
                produto.setGrupoPrecificacao(grupoPrecificacao);
            }
            if (request.getParameter("status") != null && !request.getParameter("status").isEmpty()) {
                produto.setStatus(request.getParameter("status").trim());
            }
        }

        return produto;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("produtos", resultado.getEntidades());
        response.sendRedirect(request.getContextPath() + "/view/consultarDadosProduto");
    }
}

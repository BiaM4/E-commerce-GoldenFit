package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.MovimentacaoEstoque;
import br.com.fatec.goldenfit.model.Produto;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.model.enums.TipoMovimentacao;
import br.com.fatec.goldenfit.util.Conversao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public class ConsultarMovimentacoesVH implements IViewHelper {
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
        if (request.getParameter("tipoPesquisa") != null && !request.getParameter("tipoPesquisa").isEmpty()) {
            movimentacao.setPesquisa(request.getParameter("tipoPesquisa"));
            if (request.getParameter("produto") != null && !request.getParameter("produto").isEmpty()) {
                Produto produto = new Produto();
                produto.setNome(request.getParameter("produto").trim());
                movimentacao.setProduto(produto);
            }

            if (request.getParameter("tipo") != null && !request.getParameter("tipo").isEmpty()) {
                movimentacao.setTipo(TipoMovimentacao.valueOf(request.getParameter("tipo")));
            }

            if (request.getParameter("quantidade") != null && !request.getParameter("quantidade").isEmpty()) {
                movimentacao.setQuantidade(Conversao.parseStringToDouble(request.getParameter("quantidade").trim()));
            }

            if (request.getParameter("fornecedor") != null && !request.getParameter("fornecedor").isEmpty()) {
                movimentacao.setFornecedor(request.getParameter("fornecedor").trim());
            }

            if (request.getParameter("precoCusto") != null && !request.getParameter("precoCusto").isEmpty()) {
                movimentacao.setPrecoCusto(Conversao.parseStringToDouble(request.getParameter("precoCusto").trim()));
            }

            if (request.getParameter("data") != null && !request.getParameter("data").isEmpty()) {
                try {
                    movimentacao.setData(Conversao.parseStringToDate(request.getParameter("data"), "yyyy-MM-dd"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return movimentacao;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (resultado != null && resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            request.getSession().setAttribute("movimentacoes", resultado.getEntidades());
            response.sendRedirect(request.getContextPath() + "/view/consultarMovimentacoes.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/view/movimentacoes.jsp");
        }
    }
}

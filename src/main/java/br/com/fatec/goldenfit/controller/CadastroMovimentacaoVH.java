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

public class CadastroMovimentacaoVH implements IViewHelper {
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        MovimentacaoEstoque movimentacao = null;
        try {
            Produto produto = new Produto();
            String produtoId = request.getParameter("produto");
            if (produtoId != null && !produtoId.isEmpty()) {
                produto.setId(Conversao.parseStringToInt(produtoId));
            }

            TipoMovimentacao tipo = TipoMovimentacao.valueOf(request.getParameter("tipo"));

            movimentacao = new MovimentacaoEstoque(null, null,
                    Conversao.parseStringToDate(request.getParameter("data"),"yyyy-MM-dd"),
                    Conversao.parseStringToDouble(request.getParameter("quantidade")),
                    Conversao.parseStringToDouble(request.getParameter("precoCusto")),
                    request.getParameter("fornecedor"),
                    tipo,
                    tipo.isEntrada(),
                    null,
                    produto);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return movimentacao;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarMovimentacoesVH");
    }
}

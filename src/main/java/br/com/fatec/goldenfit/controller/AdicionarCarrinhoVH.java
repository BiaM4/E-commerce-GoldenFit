package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.CarrinhoItem;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Produto;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AdicionarCarrinhoVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Produto produto = new Produto();
        produto.setId(Conversao.parseStringToInt(request.getParameter("id")));
        produto.setPesquisa("id");
        return produto;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            Double quantidade = Conversao.parseStringToDouble(request.getParameter("quantidade"));
            Produto produto = (Produto) resultado.getEntidades().get(0);
            CarrinhoItem item = new CarrinhoItem(produto, quantidade);

            request.setAttribute("itemCarrinho", item);
            request.setAttribute("acao", "adicionarItem");

            RequestDispatcher rd = request.getRequestDispatcher("/carrinho");
            try {
                rd.forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

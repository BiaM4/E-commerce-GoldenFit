package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.*;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;

public class UsarCartaoVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Cartao cartao = new Cartao();
        cartao.setId(Conversao.parseStringToInt(request.getParameter("id")));
        cartao.setPesquisa("id");

        return cartao;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            Cartao cartao = (Cartao) resultado.getEntidades().get(0);
            FormaPagamento formaPagamento = new FormaPagamento(cartao);
            Pedido pedido = (Pedido) request.getSession().getAttribute("novoPedido");
            StringBuilder erroCartao = new StringBuilder();

            if (pedido.getQuantidadeCartoesUsados() < pedido.getQuantidadeMaxCartoes()) {
                pedido.getFormasPagamento().add(formaPagamento);
                request.getSession().setAttribute("novoPedido", pedido);
            }else {
                erroCartao.append("Não é possível adicionar mais cartões ao pedido, limite excedido.");
            }
            response.sendRedirect(request.getContextPath() + "/view/finalizarPedido?erroCartao="+ (erroCartao.length() > 0
                    ? URLEncoder.encode(erroCartao.toString(), "UTF-8" ) : ""));
        }
    }
}

package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.*;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;

public class RemoverCartaoPedidoVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Cartao cartao = new Cartao();
        cartao.setId(Conversao.parseStringToInt(request.getParameter("id")));
        cartao.setPesquisa("id");

        return cartao;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            Cartao cartao = (Cartao) resultado.getEntidades().get(0);
            FormaPagamento formaPagamentoRemover = null;
            Pedido pedido = (Pedido) request.getSession().getAttribute("novoPedido");
            StringBuilder erroCartao = new StringBuilder();

            for(FormaPagamento formaPagamento : pedido.getFormasPagamento()) {
                if(formaPagamento.getCartao() != null && formaPagamento.getCartao().equals(cartao)) {
                    formaPagamentoRemover = formaPagamento;
                }
            }
            if (formaPagamentoRemover != null && (pedido.getValorTotal() == 0 || pedido.getFormasPagamento().size() > 1)) {
                pedido.getFormasPagamento().remove(formaPagamentoRemover);
            }else {
                erroCartao.append("Não foi possível remover o cartão.\n"
                        + " É necessário no mínimo 1 forma de pagamento no pedido.");
            }
            request.getSession().setAttribute("novoPedido", pedido);
            response.sendRedirect(request.getContextPath() + "/view/finalizarPedido?erroCartao="+ (erroCartao.length() > 0
                    ? URLEncoder.encode(erroCartao.toString(), "UTF-8" ) : ""));
        }
    }
}

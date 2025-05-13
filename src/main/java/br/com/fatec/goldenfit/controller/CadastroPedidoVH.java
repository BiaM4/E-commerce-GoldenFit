package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.*;
import br.com.fatec.goldenfit.model.enums.StatusPedidoItem;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;

public class CadastroPedidoVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Pedido pedido = (Pedido) request.getSession().getAttribute("novoPedido");
        Endereco enderecoEntrega = new Endereco();
        Endereco enderecoCobranca = new Endereco();

        enderecoEntrega.setId(Conversao.parseStringToInt(request.getParameter("enderecoEntrega")));
        enderecoCobranca.setId(Conversao.parseStringToInt(request.getParameter("enderecoCobranca")));
        pedido.setEnderecoEntrega(enderecoEntrega);
        pedido.setEnderecoCobranca(enderecoCobranca);

        if(pedido.getItens() != null && !pedido.getItens().isEmpty()) {
            for(PedidoItem item : pedido.getItens()) {
                item.setStatus(StatusPedidoItem.EM_PROCESSAMENTO);
            }
        }

        return pedido;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String erroPedido = null;

        if(resultado.getResposta()==null) {
            request.getSession().setAttribute("carrinho", null);
            request.getSession().setAttribute("novoPedido", null);
            response.sendRedirect(request.getContextPath() + "/view/sucesso.jsp");
        } else {
            erroPedido = resultado.getResposta();
            response.sendRedirect(request.getContextPath()
                    + "/view/finalizarPedido?erroPedido="+ (erroPedido != null ? URLEncoder.encode(erroPedido, "UTF-8" ) : ""));
        }
    }
}

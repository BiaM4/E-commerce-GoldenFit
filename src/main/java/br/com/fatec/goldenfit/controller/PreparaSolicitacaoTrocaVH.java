package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PreparaSolicitacaoTrocaVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Pedido pedido = (Pedido) request.getSession().getAttribute("pedido");
        pedido.setPesquisa("id");

        return pedido;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            Pedido pedido = (Pedido) resultado.getEntidades().get(0);
            List<PedidoItemTroca> itensTroca = new ArrayList<PedidoItemTroca>();

            if(pedido != null && pedido.getItens() != null && !pedido.getItens().isEmpty()) {
                for(PedidoItem item : pedido.getItens()) {
                    if(item.getQuantidadeDisponivelTroca() != null && item.getQuantidadeDisponivelTroca() > 0) {
                        itensTroca.add(new PedidoItemTroca(item));
                    }
                }
            }

            request.getSession().setAttribute("itensTroca", itensTroca);
            RequestDispatcher rd = request.getRequestDispatcher("/view/solicitacaoTroca.jsp");
            try {
                rd.forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/view/index");
        }
    }
}

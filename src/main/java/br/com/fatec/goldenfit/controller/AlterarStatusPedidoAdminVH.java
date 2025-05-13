package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Pedido;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.model.enums.StatusPedido;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AlterarStatusPedidoAdminVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Pedido pedido = (Pedido) request.getSession().getAttribute("pedidoAdmin");

        if (request.getParameter("status") != null && !request.getParameter("status").isEmpty()) {
            System.out.println(request.getParameter("status"));
            pedido.setStatus(StatusPedido.valueOf(request.getParameter("status")));
        }

        return pedido;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Pedido pedido = (Pedido) request.getSession().getAttribute("pedidoAdmin");
        response.sendRedirect("/ecommerce/controlador?acao=consultar&viewHelper=ConsultarPedidoAdminVH&id=" + pedido.getId());
    }
}

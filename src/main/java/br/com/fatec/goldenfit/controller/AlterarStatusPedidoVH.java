package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Pedido;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.model.enums.StatusPedido;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AlterarStatusPedidoVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Pedido pedido = (Pedido) request.getSession().getAttribute("pedido");

        if (request.getParameter("status") != null && !request.getParameter("status").isEmpty()) {
            pedido.setStatus(StatusPedido.valueOf(request.getParameter("status")));
        }

        return pedido;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String caminhoRedirecionar = request.getParameter("caminhoRedirecionar") !=null ? request.getParameter("caminhoRedirecionar") : "/view/detalharPedido.jsp";
        Pedido pedido = (Pedido) request.getSession().getAttribute("pedido");
        response.sendRedirect("/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarPedidoVH&id=" + pedido.getId());
    }
}

package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.Cliente;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Pedido;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ConsultarPedidoVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Cliente cliente = (Cliente) request.getSession().getAttribute("clienteLogado");
        Pedido pedido = new Pedido();

        if(cliente != null) {
            pedido.setCliente(cliente);
        }

        if (request.getParameter("tipoPesquisa") != null) {
            pedido.setPesquisa(request.getParameter("tipoPesquisa"));
        }else if(request.getParameter("id") != null) {
            pedido.setId(Conversao.parseStringToInt(request.getParameter("id")));
            pedido.setPesquisa("id");
        }
        return pedido;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            Pedido pedido = (Pedido) resultado.getEntidades().get(0);
            request.getSession().setAttribute("pedido", pedido);

            response.sendRedirect(request.getContextPath() +  "/view/detalhePedido.jsp");
        }else {
            response.sendRedirect(request.getContextPath() + "/view/index");
        }
    }
}

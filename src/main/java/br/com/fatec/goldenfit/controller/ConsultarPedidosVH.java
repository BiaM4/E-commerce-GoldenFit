package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.Cliente;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Pedido;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ConsultarPedidosVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Pedido pedido = new Pedido();
        Cliente cliente;

        if(request.getParameter("idCliente") != null && !request.getParameter("idCliente").isEmpty()){
            cliente = new Cliente();
            cliente.setId(Conversao.parseStringToInt(request.getParameter("idCliente")));
        }else {
            cliente = (Cliente) request.getSession().getAttribute("clienteLogado");
        }

        if (cliente != null) {
            pedido.setCliente(cliente);
            pedido.setPesquisa("cliente");
        }

        return pedido;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cliente cliente = (Cliente) request.getSession().getAttribute("clienteLogado");
        String caminhoRedirecionar = "/view/historicoPedidosCliente.jsp";

        if(request.getParameter("nomeCliente") != null && request.getParameter("idCliente") != null) {
            request.getSession().setAttribute("nomeCliente", request.getParameter("nomeCliente"));
            request.getSession().setAttribute("idCliente", request.getParameter("idCliente"));
        }

        if (resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            request.getSession().setAttribute("pedidos", resultado.getEntidades());
            caminhoRedirecionar = cliente != null ? "/view/meusPedidos.jsp" : "/view/historicoPedidosCliente.jsp";
            if(cliente != null) {
                caminhoRedirecionar = "/view/meusPedidos.jsp";
            }
            response.sendRedirect(request.getContextPath() + caminhoRedirecionar);
        } else {
            request.getSession().setAttribute("pedidos", null);
            if(cliente != null) {
                caminhoRedirecionar = "/view/listaClientes.jsp";
            }
            response.sendRedirect(request.getContextPath() + caminhoRedirecionar);
        }
    }
}

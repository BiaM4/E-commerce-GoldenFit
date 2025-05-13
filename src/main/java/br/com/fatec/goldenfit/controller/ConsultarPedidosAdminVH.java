package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.*;
import br.com.fatec.goldenfit.model.enums.StatusPedido;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;

public class ConsultarPedidosAdminVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Pedido pedido = new Pedido();

        if(request.getParameter("tipoPesquisa") != null && !request.getParameter("tipoPesquisa").isEmpty() ) {
            pedido.setPesquisa(request.getParameter("tipoPesquisa"));


            if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
                pedido.setId(Conversao.parseStringToInt(request.getParameter("id").trim()));
            }

            if (request.getParameter("dtCadastro") != null && !request.getParameter("dtCadastro").isEmpty()) {
                try {
                    pedido.setDtCadastro((Conversao.parseStringToDate(request.getParameter("dtCadastro"), "yyyy-MM-dd")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if(request.getParameter("email") != null && !request.getParameter("email").isEmpty()) {
                Usuario usuario = new Usuario();
                Cliente cliente = new Cliente();
                usuario.setEmail(request.getParameter("email").trim());
                cliente.setUsuario(usuario);
                pedido.setCliente(cliente);
            }

            if(request.getParameter("cpf") != null && !request.getParameter("cpf").isEmpty()) {
                if (pedido.getCliente() == null) {
                    Cliente cliente = new Cliente();
                    pedido.setCliente(cliente);
                }
                pedido.getCliente().setCpf(request.getParameter("cpf").trim());
            }

            if(request.getParameter("valorTotal") != null && !request.getParameter("valorTotal").isEmpty()) {
                pedido.setValorTotal(Conversao.parseStringToDouble(request.getParameter("valorTotal").trim()));
            } else {
                pedido.setValorTotal(0d);
            }

            if(request.getParameter("status") != null && !request.getParameter("status").isEmpty()) {
                pedido.setStatus(StatusPedido.valueOf(request.getParameter("status")));
            }
        }
        return pedido;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("pedidosAdmin", resultado.getEntidades());
        response.sendRedirect(request.getContextPath() + "/view/consultarPedidos.jsp");
    }
}

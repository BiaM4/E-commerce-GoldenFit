package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.dao.ClienteDAO;
import br.com.fatec.goldenfit.model.Cliente;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.model.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AtivarUsuarioVH implements IViewHelper{

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        String clienteId = request.getParameter("id");
        System.out.println("ID do cliente: " + clienteId);

        if (clienteId != null && !clienteId.isEmpty()) {
            try {
                ClienteDAO clienteDAO = new ClienteDAO();
                Cliente cliente = clienteDAO.getClienteById(Integer.parseInt(clienteId));

                if (cliente != null) {
                    return cliente.getUsuario();
                } else {
                    return null;
                }
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();;
        response.sendRedirect(request.getContextPath() + "/view/consultarClientes.jsp");
    }
}

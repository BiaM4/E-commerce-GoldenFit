package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.Cliente;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.model.Usuario;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;

public class ConsultarClientesVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Cliente cliente = new Cliente();
        if(request.getParameter("tipoPesquisa") != null && !request.getParameter("tipoPesquisa").isEmpty() ) {
            cliente.setPesquisa(request.getParameter("tipoPesquisa"));

            if(request.getParameter("codigo") != null && !request.getParameter("codigo").isEmpty()) {
                cliente.setId(Conversao.parseStringToInt(request.getParameter("codigo").trim()));
            }
            if (request.getParameter("email") != null && !request.getParameter("email").isEmpty()) {
                Usuario usuario = new Usuario();
                usuario.setEmail(request.getParameter("email").trim());
                cliente.setUsuario(usuario);
            }
            if (request.getParameter("cpf") != null && !request.getParameter("cpf").isEmpty()) {
                cliente.setCpf(request.getParameter("cpf").trim());
            }
            if (request.getParameter("status") != null && !request.getParameter("status").isEmpty()) {
                if(cliente.getUsuario() == null) {
                    Usuario usuario = new Usuario();
                    cliente.setUsuario(usuario);
                }
                cliente.getUsuario().setStatus(Conversao.parseStringToBoolean(request.getParameter("status")));
            }
            if (request.getParameter("nome") != null && !request.getParameter("nome").isEmpty()) {
                cliente.setNome(request.getParameter("nome").trim());
            }
            if (request.getParameter("sobrenome") != null && !request.getParameter("sobrenome").isEmpty()) {
                cliente.setSobrenome(request.getParameter("sobrenome").trim());
            }
            if (request.getParameter("dataNascimento") != null && !request.getParameter("dataNascimento").isEmpty()) {
                try {
                    System.out.println(request.getParameter("dataNascimento"));
                    cliente.setDataNascimento(Conversao.parseStringToDate(request.getParameter("dataNascimento").trim(), "yyyy-MM-dd"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return cliente;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("clientes", resultado.getEntidades());
        response.sendRedirect(request.getContextPath() + "/view/consultarClientes.jsp");
    }
}

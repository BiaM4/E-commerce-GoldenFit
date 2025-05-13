package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.Cliente;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.util.Conversao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExcluirClienteVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("execute: removendo cliente");

        Cliente cliente = new Cliente();
        cliente.setId((Conversao.parseStringToInt((request.getParameter("id")))));

        request.getSession().invalidate();
        return cliente;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/view/consultarClientes.jsp");
    }
}

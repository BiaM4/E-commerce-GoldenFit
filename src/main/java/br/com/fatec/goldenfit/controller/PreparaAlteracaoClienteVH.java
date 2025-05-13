package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.Cliente;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class PreparaAlteracaoClienteVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Cliente cliente = (Cliente) request.getSession().getAttribute("clienteLogado");
        cliente.setId(Conversao.parseStringToInt(request.getParameter("id")));
        cliente.setPesquisa("id");
        return cliente;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<EntidadeDominio> entidades = resultado.getEntidades();

        if(entidades != null && !entidades.isEmpty()) {
            Cliente cliente = (Cliente) entidades.get(0);
            request.getSession().setAttribute("cliente", cliente);
        }

        response.sendRedirect(request.getContextPath() + "/view/atualizaCliente.jsp");
    }
}

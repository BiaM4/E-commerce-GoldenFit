package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.Cliente;
import br.com.fatec.goldenfit.model.Cupom;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ConsultarCuponsVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Cupom cupom = new Cupom();
        Cliente cliente = (Cliente) request.getSession().getAttribute("clienteLogado");
        if (cliente != null) {
            cupom.setIdCliente(cliente.getId());
        }
        cupom.setPesquisa("validadeAtiva");
        return cupom;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            request.getSession().setAttribute("cupons", resultado.getEntidades());

            response.sendRedirect(request.getContextPath() + "/view/cuponsDisponiveis.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/view/listaClientes.jsp");
        }
    }
}

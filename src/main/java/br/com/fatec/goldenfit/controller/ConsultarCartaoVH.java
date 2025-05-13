package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.controller.IViewHelper;
import br.com.fatec.goldenfit.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ConsultarCartaoVH implements IViewHelper {
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Cartao cartao = new Cartao();

        Cliente cliente = (Cliente) request.getSession().getAttribute("clienteLogado");
        if (cliente != null) {
            cartao.setIdCliente(cliente.getId());
        }
        cartao.setPesquisa("validadeAtiva");
        return cartao;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            request.getSession().setAttribute("cartoes", resultado.getEntidades());

            response.sendRedirect(request.getContextPath() + "/carregarDadosCartoes");
        } else {
            response.sendRedirect(request.getContextPath() + "/carregarDadosCartoes");
        }
    }
}

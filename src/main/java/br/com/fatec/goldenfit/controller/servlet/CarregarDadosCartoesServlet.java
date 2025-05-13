package br.com.fatec.goldenfit.controller.servlet;

import br.com.fatec.goldenfit.command.ConsultarCommand;
import br.com.fatec.goldenfit.model.*;
import br.com.fatec.goldenfit.util.Conversao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/carregarDadosCartoes")
public class CarregarDadosCartoesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ConsultarCommand command = new ConsultarCommand();

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();

        Cliente clienteLogado = (Cliente) sessao.getAttribute("clienteLogado");

        if (clienteLogado == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Cartao cartao = new Cartao();
        cartao.setPesquisa("cliente");
        cartao.setCliente(clienteLogado);

        Result resultado = command.executar(cartao);

        sessao.setAttribute("cartoes", resultado.getEntidades());

        response.sendRedirect(request.getContextPath() + "/view/meusCartoes.jsp");
    }
}

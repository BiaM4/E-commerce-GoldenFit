package br.com.fatec.goldenfit.controller.servlet;

import br.com.fatec.goldenfit.command.ConsultarCommand;
import br.com.fatec.goldenfit.model.Cartao;
import br.com.fatec.goldenfit.model.Cliente;
import br.com.fatec.goldenfit.model.Endereco;
import br.com.fatec.goldenfit.model.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/carregarDadosEndereco")
public class CarregarDadosEnderecoServlet extends HttpServlet {
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

        Endereco endereco = new Endereco();
        endereco.setPesquisa("cliente");
        endereco.setCliente(clienteLogado);

        Result resultado = command.executar(endereco);

        sessao.setAttribute("enderecos", resultado.getEntidades());

        response.sendRedirect(request.getContextPath() + "/view/endereco.jsp");
    }
}

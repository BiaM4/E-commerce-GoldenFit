package br.com.fatec.goldenfit.controller.servlet;

import br.com.fatec.goldenfit.command.ConsultarCommand;
import br.com.fatec.goldenfit.model.Cliente;
import br.com.fatec.goldenfit.model.Result;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/view/consultarClientes")
public class ConsultarClientesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ConsultarCommand command = new ConsultarCommand();
    private Result resultadoClientes = new Result();
    private Cliente cliente = new Cliente();

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        cliente.setPesquisa("");
        resultadoClientes = command.executar(cliente);

        if(resultadoClientes.getEntidades() != null && !resultadoClientes.getEntidades().isEmpty()) {
            request.setAttribute("clientes", resultadoClientes.getEntidades());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/view/cadastrarCupom.jsp");
        rd.forward(request, response);
    }
}

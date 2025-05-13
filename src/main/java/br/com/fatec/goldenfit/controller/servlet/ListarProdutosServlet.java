package br.com.fatec.goldenfit.controller.servlet;

import br.com.fatec.goldenfit.command.ConsultarCommand;
import br.com.fatec.goldenfit.model.Produto;
import br.com.fatec.goldenfit.model.Result;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/view/index")
public class ListarProdutosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ConsultarCommand command = new ConsultarCommand();
    private Result resultado = new Result();
    private Produto produto = new Produto();

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararConsultaEntidades();
        resultado = command.executar(produto);

        if(resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            request.setAttribute("produtos", resultado.getEntidades());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/view/index.jsp");
        rd.forward(request, response);
    }

    private void prepararConsultaEntidades() {
        produto.setPesquisa("status");
        produto.setStatus(String.valueOf(true));
    }
}

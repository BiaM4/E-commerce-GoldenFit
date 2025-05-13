package br.com.fatec.goldenfit.controller.servlet;

import br.com.fatec.goldenfit.command.ConsultarCommand;
import br.com.fatec.goldenfit.model.Categoria;
import br.com.fatec.goldenfit.model.Result;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/view/categoria")
public class ListarCategoriasServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ConsultarCommand command = new ConsultarCommand();
    private Result resultado = new Result();
    private Categoria categoria = new Categoria();

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        categoria.setPesquisa("");
        resultado = command.executar(categoria);

        if(resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            request.setAttribute("categorias", resultado.getEntidades());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/view/categoria.jsp");
        rd.forward(request, response);
    }
}

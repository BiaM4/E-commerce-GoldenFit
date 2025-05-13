package br.com.fatec.goldenfit.controller.servlet;

import br.com.fatec.goldenfit.command.ConsultarCommand;
import br.com.fatec.goldenfit.model.Cupom;
import br.com.fatec.goldenfit.model.Result;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/view/consultarCupom")
public class ListarCuponsServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;
        private ConsultarCommand command = new ConsultarCommand();
        private Result resultado = new Result();
        private Cupom cupom = new Cupom();

        protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
                    cupom.setPesquisa("");
                    resultado = command.executar(cupom);

                    if(resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
                    request.setAttribute("cupons", resultado.getEntidades());
                    }

                    RequestDispatcher rd = request.getRequestDispatcher("/view/consultarCupom.jsp");
                    rd.forward(request, response);
        }
}

package br.com.fatec.goldenfit.controller.servlet;

import br.com.fatec.goldenfit.command.ConsultarCommand;
import br.com.fatec.goldenfit.model.MovimentacaoEstoque;
import br.com.fatec.goldenfit.model.Result;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/view/consultarMovimentacoes")
public class CarregaMovimentacoesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ConsultarCommand command = new ConsultarCommand();
    private Result resultado = new Result();
    private MovimentacaoEstoque movimentacaoEstoque  = new MovimentacaoEstoque();

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        movimentacaoEstoque.setPesquisa("");
        resultado = command.executar(movimentacaoEstoque);

        if(resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            request.setAttribute("movimentacoes", resultado.getEntidades());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/view/consultarMovimentacoes.jsp");
        rd.forward(request, response);
    }
}

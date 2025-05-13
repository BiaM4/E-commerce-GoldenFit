package br.com.fatec.goldenfit.controller.servlet;

import br.com.fatec.goldenfit.command.AlterarCommand;
import br.com.fatec.goldenfit.command.ICommand;
import br.com.fatec.goldenfit.model.PedidoItemTroca;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;

@WebServlet("/view/marcarNotificacaoTrocaComoLida")
public class NotificacaoTrocaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ICommand command = new AlterarCommand();
    private List<PedidoItemTroca> itensTrocaNotificacao;

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        itensTrocaNotificacao = (List<PedidoItemTroca>) request.getSession().getAttribute("itensTrocaNotificacao");

        if (itensTrocaNotificacao != null && !itensTrocaNotificacao.isEmpty()) {
            for (PedidoItemTroca itemTroca : itensTrocaNotificacao) {
                itemTroca.setNotificacao(true);
                command.executar(itemTroca);
            }
            request.getSession().setAttribute("itensTrocaNotificacao", null);
        }

        response.sendRedirect(request.getContextPath() + "/view/listaClientes.jsp");
    }
}

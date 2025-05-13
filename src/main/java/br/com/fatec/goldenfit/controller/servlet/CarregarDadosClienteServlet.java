package br.com.fatec.goldenfit.controller.servlet;

import br.com.fatec.goldenfit.command.ConsultarCommand;
import br.com.fatec.goldenfit.model.*;
import br.com.fatec.goldenfit.util.Conversao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = "/carregarDadosCliente")
public class CarregarDadosClienteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ConsultarCommand command = new ConsultarCommand();
    private Result resultado = new Result();
    private Usuario usuario = new Usuario();
    private Cliente cliente = new Cliente();
    private Cartao cartao = new Cartao();
    private Endereco endereco = new Endereco();
    PedidoItemTroca itemTroca = new PedidoItemTroca();

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        prepararConsultaEntidades();

        if (sessao.getAttribute("clienteLogado") == null) {
            usuario.setId(Conversao.parseStringToInt(request.getParameter("id")));
        } else {
            usuario = ((Cliente) sessao.getAttribute("clienteLogado")).getUsuario();
        }

        resultado = command.executar(usuario);

        if (resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            usuario = (Usuario) resultado.getEntidades().get(0);
            cliente.setUsuario(usuario);
        }

        resultado = command.executar(cliente);
        if (resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            cliente = (Cliente) resultado.getEntidades().get(0);
            itemTroca.getItem().getPedido().setCliente(cliente);
        }

        cartao.setCliente(cliente);
        endereco.setCliente(cliente);
        sessao.setAttribute("clienteLogado", cliente);
        sessao.setAttribute("cartoes", command.executar(cartao).getEntidades());
        sessao.setAttribute("enderecos", command.executar(endereco).getEntidades());
        sessao.setAttribute("itensTrocaNotificacao", command.executar(itemTroca).getEntidades());

        response.sendRedirect(request.getContextPath() + "/view/index");
    }

    private void prepararConsultaEntidades() {
        usuario.setPesquisa("id");
        cliente.setPesquisa("usuario");
        cartao.setPesquisa("cliente");
        endereco.setPesquisa("cliente");

        PedidoItem item = new PedidoItem();
        Pedido pedido = new Pedido();
        item.setPedido(pedido);
        itemTroca.setItem(item);
        itemTroca.setPesquisa("notificacaoPendente");
    }
}

package br.com.fatec.goldenfit.controller.servlet;

import br.com.fatec.goldenfit.command.ConsultarCommand;
import br.com.fatec.goldenfit.model.*;
import br.com.fatec.goldenfit.model.enums.StatusPedido;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/view/finalizarPedido")
public class FinalizarPedidoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ConsultarCommand command = new ConsultarCommand();
    private Result resultado = new Result();
    private Pedido pedido = new Pedido();
    private Endereco endereco = new Endereco();
    private Cupom cupom = new Cupom();
    private Cartao cartao = new Cartao();
    private Cliente cliente = new Cliente();

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("clienteLogado") != null) {
            cliente = (Cliente) request.getSession().getAttribute("clienteLogado");
            Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");

            List<PedidoItem> itens = new ArrayList<PedidoItem>();
            if (carrinho != null && carrinho.getItens() != null) {
                for (CarrinhoItem itemCarrinho : carrinho.getItens()) {
                    if (itemCarrinho.getQuantidade() > 0) {
                        itens.add(new PedidoItem(itemCarrinho));
                    }
                }
            }

            if(request.getSession().getAttribute("novoPedido") == null) {
                pedido = new Pedido(cliente, StatusPedido.EM_PROCESSAMENTO, null, null, itens);
            }else {
                pedido = (Pedido) request.getSession().getAttribute("novoPedido");
                pedido.setItens(itens);
            }
            request.getSession().setAttribute("novoPedido", pedido);
            request.setAttribute("erroCartao", request.getParameter("erroCartao"));
            request.setAttribute("erroCupom", request.getParameter("erroCupom"));
            request.setAttribute("erroPedido", request.getParameter("erroPedido"));
            prepararConsultaEntidades();
            consultarEntidades(request);
            RequestDispatcher rd;

            if (itens != null && !itens.isEmpty()) {
                rd = request.getRequestDispatcher("/view/finalizarPedido.jsp");
            } else {
                rd = request.getRequestDispatcher("/view/index");
            }
                rd.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");
        }
    }

    private void prepararConsultaEntidades() {
        endereco.setCliente(cliente);
        cartao.setCliente(cliente);

        endereco.setPesquisa("cliente");
        cartao.setPesquisa("cliente");
        cupom.setPesquisa("validadeAtiva");;
    }

    private void consultarEntidades(HttpServletRequest request) {
        resultado = command.executar(endereco);
        if (resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            request.setAttribute("enderecos", resultado.getEntidades());
        }

        resultado = command.executar(cartao);
        if (resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            List<Cartao> cartoes = new ArrayList<Cartao>();
            for(EntidadeDominio entidade : resultado.getEntidades()) {
                Cartao cartao = (Cartao) entidade;
                for(FormaPagamento formaPagamento : pedido.getFormasPagamento()) {
                    if(formaPagamento.getCartao() != null && formaPagamento.getCartao().equals(cartao)) {
                        cartao.setUsado(true);
                        break;
                    }
                }
                cartoes.add(cartao);
            }
            request.setAttribute("cartoes", cartoes);
        }

        cupom.setIdCliente(cliente.getId());
        resultado = command.executar(cupom);
        if (resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            List<Cupom> cupons = new ArrayList<Cupom>();
            for(EntidadeDominio entidade : resultado.getEntidades()) {
                Cupom cupom = (Cupom) entidade;
                cupom.setAplicado(false);

                for(FormaPagamento formaPagamento : pedido.getFormasPagamento()) {
                    if(formaPagamento.getCupom() != null && formaPagamento.getCupom().equals(cupom)) {
                        cupom.setAplicado(true);
                        break;
                    }
                }
                cupons.add(cupom);
            }
            request.setAttribute("cupons", cupons);
        }
    }
}

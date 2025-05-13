package br.com.fatec.goldenfit.controller.servlet;

import br.com.fatec.goldenfit.command.ConsultarCommand;
import br.com.fatec.goldenfit.model.Carrinho;
import br.com.fatec.goldenfit.model.CarrinhoItem;
import br.com.fatec.goldenfit.model.PedidoItem;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.util.Conversao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    List<PedidoItem> itens = new ArrayList<PedidoItem>();
    private ConsultarCommand command = new ConsultarCommand();
    private Result resultado = new Result();
    private CarrinhoItem item = new CarrinhoItem();

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Carrinho carrinho = new Carrinho();
        String acao = (String) request.getParameter("acaoCarrinho");
        String caminhoRedirecionar = request.getParameter("caminhoRedirecionar") != null ?
                request.getParameter("caminhoRedirecionar") : "/view/carrinho.jsp";

        if (request.getSession().getAttribute("carrinho") != null) {
            carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
        }

        if (request.getAttribute("itemCarrinho") != null) {
            item = (CarrinhoItem) request.getAttribute("itemCarrinho");
        }

        if (acao != null && !acao.isEmpty()) {
            executarAcoesCarrinho(acao, carrinho, request);
        }

        request.getSession().setAttribute("carrinho", carrinho);
        response.sendRedirect(request.getContextPath() + caminhoRedirecionar);
    }

    private void adicionarItemCarrinho(CarrinhoItem item, Carrinho carrinho) {
        if (item != null && carrinho != null && item.getQuantidade() > 0) {
            boolean adicionado = false;

            for (CarrinhoItem itemCarrinho : carrinho.getItens()) {
                if (itemCarrinho.getProduto().getId() == item.getProduto().getId()) {
                    itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() + item.getQuantidade());
                    adicionado = true;
                    break;
                }
            }

            if (!adicionado) {
                carrinho.getItens().add(item);
            }
        }
    }

    public void removerItemCarrinho(Integer produtoId, Carrinho carrinho) {
        if (carrinho != null && produtoId != null) {
            CarrinhoItem itemRemover = null;

            for (CarrinhoItem itemCarrinho : carrinho.getItens()) {
                if (itemCarrinho.getProduto().getId() == produtoId) {
                    itemRemover = itemCarrinho;
                }
            }
            carrinho.getItens().remove(itemRemover);
        }
    }

    public void mudarQuantidade(Integer produtoId, String acao, Carrinho carrinho) {
        if (carrinho != null && produtoId != null && acao != null) {

            for (CarrinhoItem itemCarrinho : carrinho.getItens()) {
                if (itemCarrinho.getProduto().getId() == produtoId) {
                    if (acao.equals("aumentarQuantidade")
                            && itemCarrinho.getQuantidade() < itemCarrinho.getProduto().getQtdEstoque()) {
                        itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() + 1);
                    }
                    if (acao.equals("diminuirQuantidade") && itemCarrinho.getQuantidade() > 1) {
                        itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() - 1);
                    }
                }
            }
        }
    }

    public void executarAcoesCarrinho(String acao, Carrinho carrinho, HttpServletRequest request) {
        if (acao.equals("adicionarItem")) {
            adicionarItemCarrinho(item, carrinho);
        } else {
            Integer produtoId = Conversao.parseStringToInt(request.getParameter("produtoId"));
            if (acao.equals("removerItem") && request.getParameter("produtoId") != null) {
                removerItemCarrinho(produtoId, carrinho);
            } else {
                mudarQuantidade(produtoId, acao, carrinho);
            }
        }
    }
}

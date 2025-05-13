package br.com.fatec.goldenfit.controller.servlet;

import br.com.fatec.goldenfit.command.*;
import br.com.fatec.goldenfit.model.Pedido;
import br.com.fatec.goldenfit.model.PedidoItemTroca;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.model.enums.StatusPedido;
import br.com.fatec.goldenfit.model.enums.StatusPedidoItem;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/view/solicitarTroca")
public class SolicitarTrocaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ICommand command;
    private Map<String, ICommand> commandMap = new HashMap<String, ICommand>();
    private Pedido pedido;
    private List<PedidoItemTroca> itensTroca;
    private Result resultado = new Result();

    public SolicitarTrocaServlet() {
        commandMap.put("salvar", new SalvarCommand());
        commandMap.put("alterar", new AlterarCommand());
        commandMap.put("excluir", new ExcluirCommand());
        commandMap.put("consultar", new ConsultarCommand());
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        pedido = (Pedido) request.getSession().getAttribute("pedido");
        itensTroca = (List<PedidoItemTroca>) request.getSession().getAttribute("itensTroca");
        List<PedidoItemTroca> itensRemover = new ArrayList<PedidoItemTroca>();
        boolean isQuantidadeDiferentePedido = false;

        if (itensTroca != null && !itensTroca.isEmpty()) {
            for (PedidoItemTroca itemTroca : itensTroca) {
                itemTroca.setQuantidade(Conversao.parseStringToDouble(
                        request.getParameter("quantidadeTrocaItem" + itemTroca.getItem().getId())));

                if (itemTroca.getQuantidade() == 0 || itemTroca.getQuantidade() == null) {
                    itensRemover.add(itemTroca);
                }

                if (!itemTroca.getQuantidade().equals(itemTroca.getItem().getQuantidade())) {
                    if (itemTroca.getQuantidade() > itemTroca.getItem().getQuantidade()) {
                        itemTroca.setQuantidade(itemTroca.getItem().getQuantidade());
                    } else {
                        isQuantidadeDiferentePedido = true;
                    }
                }
            }
        }

        if (itensRemover != null && !itensRemover.isEmpty()) {
            itensTroca.removeAll(itensRemover);
        }

        if(itensTroca != null && !itensTroca.isEmpty()) {
            for(PedidoItemTroca itemTroca : itensTroca) {
                command = commandMap.get("salvar");
                command.executar(itemTroca);

                command = commandMap.get("alterar");
                itemTroca.getItem().setStatus(StatusPedidoItem.TROCA_SOLICITADA);
                command.executar(itemTroca.getItem());
            }
        }

//        if(!isQuantidadeDiferentePedido && itensTroca.size() == pedido.getItens().size()) {
            command = commandMap.get("alterar");
            pedido.setStatus(StatusPedido.TROCA_SOLICITADA);
            Result resultado = command.executar(pedido);
//        }

        request.getSession().setAttribute("itensTroca", null);
        response.sendRedirect("/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarPedidosVH");
    }
}

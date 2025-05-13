package br.com.fatec.goldenfit.controller.servlet;

import br.com.fatec.goldenfit.command.*;
import br.com.fatec.goldenfit.model.*;
import br.com.fatec.goldenfit.model.enums.StatusPedido;
import br.com.fatec.goldenfit.model.enums.StatusPedidoItem;
import br.com.fatec.goldenfit.model.enums.TipoCupom;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/view/gerenciamentoStatusPedido")
public class GerenciaStatusPedidoAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ICommand command;
    Pedido pedido;
    StatusPedido status = null;
    Result resultado;
    PedidoItemTroca itemTroca;
    Double valorTotalCupomTroca;
    private Map<String,ICommand> commandMap = new HashMap<String,ICommand>();

    public GerenciaStatusPedidoAdminServlet() {
        commandMap.put("salvar", new SalvarCommand());
        commandMap.put("alterar", new AlterarCommand());
        commandMap.put("excluir", new ExcluirCommand());
        commandMap.put("consultar", new ConsultarCommand());
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        itemTroca = new PedidoItemTroca();
        pedido = (Pedido) request.getSession().getAttribute("pedidoAdmin");
        valorTotalCupomTroca = 0d;

        if (request.getParameter("status") != null && !request.getParameter("status").isEmpty()) {
            System.out.println(request.getParameter("status"));
            status = StatusPedido.valueOf(request.getParameter("status"));
        }

        if(pedido == null && request.getParameter("id") != null) {
            command = commandMap.get("consultar");
            pedido = new Pedido();
            pedido.setId(Conversao.parseStringToInt(request.getParameter("id")));
            pedido.setPesquisa("id");
            pedido = (Pedido) command.executar(pedido).getEntidades().get(0);
        }

        if(status != null) {
            if ((status != StatusPedido.TROCA_AUTORIZADA && status != StatusPedido.TROCA_REALIZADA &&
                    status != StatusPedido.PEDIDO_CANCELADO && status != StatusPedido.CANCELAMENTO_AUTORIZADO)
                    || (!pedido.isPossuiTrocaParcialSolicitada() && !pedido.isPossuiTrocaParcialAutorizada()
                    && status != StatusPedido.CANCELAMENTO_AUTORIZADO)) {

                pedido.setStatus(status);
                command = commandMap.get("alterar");
                command.executar(pedido);

                System.out.println(1);

                if(status != StatusPedido.CANCELAMENTO_AUTORIZADO || status != StatusPedido.CANCELAMENTO_REPROVADO
                || status != StatusPedido.PEDIDO_CANCELADO || status != StatusPedido.CANCELAMENTO_SOLICITADO){
                    for(PedidoItem item : pedido.getItens()) {
                        item.setStatus(StatusPedidoItem.valueOf(status.name()));
                        command.executar(item);
                    }
                }

                valorTotalCupomTroca = pedido.getValorTotalItens();

            } else {
                command = commandMap.get("alterar");

                System.out.println(2);


                if(status == StatusPedido.TROCA_AUTORIZADA) {
                    //Altera status do pedido para Troca Autorizada
                    pedido.setStatus(status);
                    command = commandMap.get("alterar");
                    command.executar(pedido);

                    gerenciarTrocaParcialAutorizada();
                }
                if(status == StatusPedido.TROCA_REALIZADA) {
                    //Altera status do pedido para Troca Realizada
                    pedido.setStatus(status);
                    command = commandMap.get("alterar");
                    command.executar(pedido);

                    gerenciarTrocaParcialRealizada();
                }
                if(status == StatusPedido.CANCELAMENTO_AUTORIZADO){
                    boolean peloMenosUmEntregue = false;
                    for (PedidoItem item : pedido.getItens()) {
                        if (item.getStatus().equals(StatusPedidoItem.ENTREGUE)) {
                            peloMenosUmEntregue = true;
                            break;
                        }
                    }
                    //Caso os itens tenham sido entregue
                    if (peloMenosUmEntregue) {
                        StatusPedido statusPedido = StatusPedido.CANCELAMENTO_AUTORIZADO;

                        pedido.setStatus(statusPedido);
                        command = commandMap.get("alterar");
                        command.executar(pedido);
                    } else { //Caso contrário
                        StatusPedido statusPedido = StatusPedido.ITENS_DEVOLVIDOS;

                        pedido.setStatus(statusPedido);
                        command = commandMap.get("alterar");
                        command.executar(pedido);
                    }

                }
            }
        }

        if(status == StatusPedido.TROCA_REALIZADA) {
            gerarCupomDeTroca(valorTotalCupomTroca, pedido);
        }

        if(status == StatusPedido.PEDIDO_CANCELADO){
            Double valor = valorTotalCupomTroca + pedido.getValorFrete();

            gerarCupomDeCancelamento(valor, pedido);
        }

        response.sendRedirect("/GoldenFit_war/controlador?acao=consultar&viewHelper=ConsultarPedidoAdminVH&id=" + pedido.getId());
    }

    public void gerenciarTrocaParcialAutorizada() {
        for (PedidoItem item : pedido.getItens()) {
            if (item.getStatus() != null && item.getStatus().equals(StatusPedidoItem.TROCA_SOLICITADA)) {
                Double quantidadeTroca = item.getQuantidade() - item.getQuantidadeDisponivelTroca();

                if(quantidadeTroca != null && quantidadeTroca > 0) {
                    item.setStatus(StatusPedidoItem.TROCA_AUTORIZADA);
                    command.executar(item);
                }
            }
        }
    }

    public void gerenciarTrocaParcialRealizada() {

        for (PedidoItem item : pedido.getItens()) {
            if (item.getStatus() != null && item.getStatus().equals(StatusPedidoItem.TROCA_AUTORIZADA)) {
                Double quantidadeTroca = item.getQuantidade() - item.getQuantidadeDisponivelTroca();
                valorTotalCupomTroca += quantidadeTroca * item.getValorUnitario();

                if(quantidadeTroca != null && quantidadeTroca > 0) {
                    item.setStatus(StatusPedidoItem.TROCA_REALIZADA);
                    command.executar(item);
                }
            }
        }
    }

    public void gerarCupomDeTroca(Double valorCupom, Pedido pedido) {
        if (valorCupom != null && valorCupom > 0 && pedido != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.YEAR, 1);
            Date validade = calendar.getTime(); // Atribuindo validade de um ano ao cupom de troca

            Cupom cupom = new Cupom(null, "TPED" + pedido.getId(), "Troca do pedido " + pedido.getId(), valorCupom,
                    validade, TipoCupom.TROCA, pedido.getCliente().getId(), pedido.getId(), true);

            command = commandMap.get("salvar");
            command.executar(cupom);
        }
    }

    public void gerarCupomDeCancelamento(Double valorCupom, Pedido pedido) {
        if (valorCupom != null && valorCupom > 0 && pedido != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.YEAR, 1);
            Date validade = calendar.getTime(); // Atribuindo validade de um ano ao cupom de troca

            Cupom cupom = new Cupom(null, "CPED" + pedido.getId(), "Cancel. do pedido " + pedido.getId(), valorCupom,
                    validade, TipoCupom.CANCELAMENTO, pedido.getCliente().getId(), pedido.getId(), true);

            command = commandMap.get("salvar");
            command.executar(cupom);
        }
    }
}

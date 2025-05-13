package br.com.fatec.goldenfit.model.enums;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {
    EM_PROCESSAMENTO("Em processamento"),
    APROVADO("Pedido aprovado"),
    REPROVADO("Pedido reprovado"),
    EM_TRANSITO("Em trânsito"),
    ENTREGUE("Entregue"),
    TROCA_SOLICITADA("Troca solicitada"),
    TROCA_AUTORIZADA("Troca autorizada"),
    ITENS_ENVIADOS_PARA_TROCA( "Iten(s) enviados p/ a troca"),
    TROCA_REPROVADA("Troca reprovada"),
    TROCA_REALIZADA("Troca realizada"),

    CANCELAMENTO_SOLICITADO("Cancelamento solicitado"),
    CANCELAMENTO_AUTORIZADO("Cancelamento autorizado"),
    ITENS_DEVOLVIDOS( "Iten(s) devolvidos"),

    CANCELAMENTO_REPROVADO("Cancelamento reprovado"),
    PEDIDO_CANCELADO("Pedido cancelado");

    public String descricao;

    private StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

    public static List<StatusPedido> getTiposStatus (){
        List<StatusPedido> tipos = Arrays.asList(StatusPedido.values());
        return tipos;
    }
}

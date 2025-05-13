package br.com.fatec.goldenfit.model;

import java.util.Date;

public class PedidoItemTroca extends EntidadeDominio{
    private PedidoItem item;
    private Double quantidade;
    private Boolean notificacao;

    public PedidoItemTroca(){
    }

    public PedidoItemTroca(PedidoItem pedidoItem){
        if(pedidoItem != null) {
            this.item = pedidoItem;
            this.quantidade = pedidoItem.getQuantidadeDisponivelTroca();
        }
    }

    public PedidoItemTroca(Integer id, Date dtCadastro, PedidoItem item, Double quantidade, Boolean notificacao){
        if(item != null) {
            this.item = item;
        }
        if (id != null) {
            this.setId(id);
        }
        if(dtCadastro != null) {
            this.setDtCadastro(dtCadastro);
        }
        this.quantidade = quantidade;
        this.notificacao = notificacao;
    }

    public PedidoItem getItem() {
        return item;
    }

    public void setItem(PedidoItem item) {
        this.item = item;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Boolean getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(Boolean notificacao) {
        this.notificacao = notificacao;
    }

    public Pedido getPedido() {
        if(item != null && item.getPedido() != null ) {
            return item.getPedido();
        }
        return null;
    }

    public Cliente getCliente() {
        if(getPedido() != null && getPedido().getCliente() != null ) {
            return getPedido().getCliente();
        }
        return null;
    }
}

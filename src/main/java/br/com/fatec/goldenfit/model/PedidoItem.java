package br.com.fatec.goldenfit.model;

import br.com.fatec.goldenfit.model.enums.StatusPedidoItem;

import java.util.Date;

public class PedidoItem extends EntidadeDominio{
    private Pedido pedido;
    private Produto produto;
    private Double quantidade;
    private Double quantidadeDisponivelTroca;
    private Double valorUnitario;
    private StatusPedidoItem status;

    public PedidoItem() {
    }

    public PedidoItem(CarrinhoItem item) {
        this.produto = item.getProduto();
        this.quantidade = item.getQuantidade();
        this.valorUnitario = item.getValorUnitario();
    }

    public PedidoItem(Integer id, Date dtCadastro, Double quantidade, Double valorUnitario, Produto produto, StatusPedidoItem status) {
        super();
        if(id != null) {
            this.setId(id);
        }
        if(dtCadastro != null) {
            this.setDtCadastro(dtCadastro);
        }
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.produto = produto;
        if(status != null) {
            this.status = status;
        }
    }

    public Double getQuantidade() {
        if (quantidade == null) {
            quantidade = 0d;
        }
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorUnitario() {
        if (valorUnitario == null) {
            valorUnitario = 0d;
        }
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getValorTotal() {
        return getQuantidade() * getValorUnitario();
    }

    public StatusPedidoItem getStatus() {
        return status;
    }

    public void setStatus(StatusPedidoItem status) {
        this.status = status;
    }

    public Double getQuantidadeDisponivelTroca() {
        return quantidadeDisponivelTroca;
    }

    public void setQuantidadeDisponivelTroca(Double quantidadeDisponivelTroca) {
        this.quantidadeDisponivelTroca = quantidadeDisponivelTroca;
    }

    public Double getQuantidadeTroca() {
        Double quantidadeTroca = 0d;
        if(quantidade != null && quantidadeDisponivelTroca != null) {
            quantidadeTroca = quantidade - quantidadeDisponivelTroca;
        }
        return quantidadeTroca;
    }
}

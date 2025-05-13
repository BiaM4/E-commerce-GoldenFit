package br.com.fatec.goldenfit.model;

import java.util.Date;

public class FormaPagamento extends EntidadeDominio{
    private Cupom cupom;
    private Cartao cartao;
    private Pedido pedido;

    public FormaPagamento() {

    }

    public FormaPagamento(Cupom cupom) {
        this.cupom = cupom;
    }

    public FormaPagamento(Cartao cartao) {
        this.cartao = cartao;
    }

    public FormaPagamento(Integer id, Date dtCadastro, Cupom cupom, Cartao cartao, Pedido pedido) {
        super();
        if(id != null) {
            this.setId(id);
        }
        if(dtCadastro != null) {
            this.setDtCadastro(dtCadastro);
        }
        if(cupom != null) {
            this.cupom = cupom;
        }
        if(cartao != null) {
            this.cartao = cartao;
        }
        if(pedido != null){
            this.pedido = pedido;
        }
    }

    public Cupom getCupom() {
        return cupom;
    }

    public void setCupom(Cupom cupom) {
        this.cupom = cupom;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

}
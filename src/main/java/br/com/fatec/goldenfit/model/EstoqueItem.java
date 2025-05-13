package br.com.fatec.goldenfit.model;

import java.util.Date;

public class EstoqueItem extends EntidadeDominio{
    private Produto produto;
    private Double quantidade;

    public EstoqueItem() {
    }

    public EstoqueItem(Integer id, Date dtCadastro, Produto produto, Double quantidade) {
        super();
        if(id != null) {
            this.setId(id);
        }
        if(dtCadastro != null) {
            this.setDtCadastro(dtCadastro);
        }

        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
}

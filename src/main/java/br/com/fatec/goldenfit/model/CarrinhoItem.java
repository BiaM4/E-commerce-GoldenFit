package br.com.fatec.goldenfit.model;

public class CarrinhoItem {
    private Produto produto;
    private Double quantidade;
    private Double valorUnitario;

    public CarrinhoItem() {
    }

    public CarrinhoItem(Produto livro, Double quantidade){
        this.produto = livro;
        this.quantidade = quantidade;
        this.valorUnitario = livro.getPreco();
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getQuantidade() {
        if(quantidade == null) {
            quantidade = 0d;
        }
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorUnitario() {
        if(valorUnitario == null) {
            valorUnitario = 0d;
        }
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getValorTotal() {
        return getQuantidade() * getValorUnitario();
    }
}

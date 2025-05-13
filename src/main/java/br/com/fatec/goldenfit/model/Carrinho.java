package br.com.fatec.goldenfit.model;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private Double valorTotal;
    private Double quantidadeTotal;
    List<CarrinhoItem> itens = new ArrayList<CarrinhoItem>();

    public Carrinho() {
    }

    public Double getValorTotal() {
        valorTotal = 0d;
        if (itens != null) {
            for (CarrinhoItem item : itens) {
                valorTotal += item.getValorTotal();
            }
        }
        return valorTotal;
    }
    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getQuantidadeTotal() {
        quantidadeTotal = 0d;
        if (itens != null) {
            for (CarrinhoItem item : itens) {
                quantidadeTotal += item.getQuantidade();
            }
        }
        return quantidadeTotal;
    }
    public void setQuantidadeTotal(Double quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }
    public List<CarrinhoItem> getItens() {
        return itens;
    }
    public void setItens(List<CarrinhoItem> itens) {
        this.itens = itens;
    }
}


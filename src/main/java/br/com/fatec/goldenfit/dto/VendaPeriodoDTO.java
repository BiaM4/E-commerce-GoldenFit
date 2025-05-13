package br.com.fatec.goldenfit.dto;

import br.com.fatec.goldenfit.util.Conversao;

public class VendaPeriodoDTO {
    private String nome;
    private String periodo;
    private Integer quantidadeTotal;
    private Double valorTotal;

    public VendaPeriodoDTO() {
    }

    public VendaPeriodoDTO(String nome, String periodo, Double quantidadeTotal, Double valorTotal) {
        super();
        this.nome = nome;
        this.periodo = periodo;
        this.quantidadeTotal = Conversao.parseDoubleToInteger(quantidadeTotal);
        this.valorTotal = valorTotal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Integer getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(Integer quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

}

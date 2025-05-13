package br.com.fatec.goldenfit.model.enums;

import java.util.Arrays;
import java.util.List;

public enum TipoMovimentacao {
    SAIDA_POR_VENDA("Saida por venda", false),
    ENTRADA_POR_TROCA("Entrada por troca", true),
    ENTRADA_POR_COMPRA("Entrada por compra", true);

    private String descricao;
    private boolean entrada;

    private TipoMovimentacao(String descricao, boolean entrada) {
        this.descricao = descricao;
        this.entrada = entrada;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isEntrada() {
        return entrada;
    }

    public static List<TipoMovimentacao> getTiposMovimentacao (){
        List<TipoMovimentacao> tipos = Arrays.asList(TipoMovimentacao.values());
        return tipos;
    }
}

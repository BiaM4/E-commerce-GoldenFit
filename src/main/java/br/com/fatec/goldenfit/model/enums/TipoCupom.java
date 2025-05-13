package br.com.fatec.goldenfit.model.enums;

import java.util.Arrays;
import java.util.List;

public enum TipoCupom {
    TROCA("Troca"),
    CANCELAMENTO("Cancelamento"),
    PROMOCIONAL("Promocional");


    private String descricao;

    private TipoCupom(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

    public static List<TipoCupom> getTiposCupom() {
        List<TipoCupom> lista = Arrays.asList(TipoCupom.values());
        return lista;
    }
}

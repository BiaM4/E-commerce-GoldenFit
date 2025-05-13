package br.com.fatec.goldenfit.model.enums;

public enum TipoResidencia {
    Casa,
    Apartamento,
    Flat,
    Kitnet,
    Loft;

    @Override
    public String toString() {
        return this.name();
    }
}

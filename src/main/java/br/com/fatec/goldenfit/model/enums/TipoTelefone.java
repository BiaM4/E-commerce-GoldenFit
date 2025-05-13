package br.com.fatec.goldenfit.model.enums;

public enum TipoTelefone {
    CELULAR(1),
    RESIDENCIAL(2);

    int codigo;

    private TipoTelefone(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public static TipoTelefone getByCodigo(int id) {
        for (TipoTelefone tipoTelefone : values()) {
            if (tipoTelefone.getCodigo() == id) {
                return tipoTelefone;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
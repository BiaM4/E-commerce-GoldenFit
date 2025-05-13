package br.com.fatec.goldenfit.model.enums;

public enum TipoEndereco {
    Cobranca(1),
    Entrega(2),
    Residencial(3);

    int codigo;

    private TipoEndereco(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public static TipoEndereco getByCodigo(int id) {
        for (TipoEndereco tipoEndereco : values()) {
            if (tipoEndereco.getCodigo() == id) {
                return tipoEndereco;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }
}

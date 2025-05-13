package br.com.fatec.goldenfit.model.enums;

public enum Bandeira {
    Visa(1),
    Mastercard(2),
    Elo(3);

    int codigo;

    private Bandeira(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public static Bandeira getByCodigo(int id) {
        for (Bandeira bandeira : values()) {
            if (bandeira.getCodigo() == id) {
                return bandeira;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }
}

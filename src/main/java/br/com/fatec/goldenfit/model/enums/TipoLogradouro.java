package br.com.fatec.goldenfit.model.enums;

public enum TipoLogradouro {
    Rua,
    Travessa,
    Avenida,
    Alameda,
    Estrada,
    Rodovia,
    Jardim;


    @Override
    public String toString() {
        return this.name();
    }
}

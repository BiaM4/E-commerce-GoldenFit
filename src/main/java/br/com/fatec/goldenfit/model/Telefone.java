package br.com.fatec.goldenfit.model;

import br.com.fatec.goldenfit.model.enums.TipoTelefone;

public class Telefone extends EntidadeDominio {
    private String ddd;
    private String numero;
    private TipoTelefone tipoTelefone;

    public Telefone() {
    }

    public Telefone(String dddResidencial, String numeroResidencial) {
    }

    public Telefone(String ddd, String numero, TipoTelefone tipoTelefone) {
        this.ddd = ddd;
        this.numero = numero;
        this.tipoTelefone = tipoTelefone;
    }

    public Telefone(Integer id,String ddd, String numero, TipoTelefone tipoTelefone) {
        super();
        if(id != null) {
            this.setId(id);
        }
        this.ddd = ddd;
        this.numero = numero;
        this.tipoTelefone = tipoTelefone;
    }


    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoTelefone getTipoTelefone() {
        return tipoTelefone;
    }

    public void setTipoTelefone(TipoTelefone tipoTelefone) {
        this.tipoTelefone = tipoTelefone;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ddd == null) ? 0 : ddd.hashCode());
        result = prime * result + ((numero == null) ? 0 : numero.hashCode());
        result = prime * result + ((tipoTelefone == null) ? 0 : tipoTelefone.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Telefone other = (Telefone) obj;
        if (ddd == null) {
            if (other.ddd != null)
                return false;
        } else if (!ddd.equals(other.ddd))
            return false;
        if (numero == null) {
            if (other.numero != null)
                return false;
        } else if (!numero.equals(other.numero))
            return false;
        if (tipoTelefone != other.tipoTelefone)
            return false;
        return true;
    }
}

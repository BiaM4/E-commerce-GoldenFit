package br.com.fatec.goldenfit.model;

import br.com.fatec.goldenfit.model.enums.Bandeira;

public class Cartao extends EntidadeDominio {
    private Cliente cliente;
    private Integer idCliente;
    private String numero;
    private Bandeira bandeira;
    private String nome;
    private String cvv;
    private Boolean preferencial;
    private boolean usado;

    public Cartao() {
    }

    public Cartao(String numero, Bandeira bandeira, String nome, String cvv,
                  Boolean preferencial, Cliente cliente) {
        this.numero = numero;
        this.bandeira = bandeira;
        this.nome = nome;
        this.cvv = cvv;
        this.preferencial = preferencial;
        this.cliente = cliente;
    }

    public Cartao(Integer id, String numero, Bandeira bandeira, String nome, String cvv,
                  Boolean preferencial, Integer idCliente) {
        this.setId(id);
        this.numero = numero;
        this.bandeira = bandeira;
        this.nome = nome;
        this.cvv = cvv;
        this.preferencial = preferencial;
        if(idCliente != null && idCliente > 0) {
            this.idCliente = idCliente;
        }
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Bandeira getBandeira() {
        return bandeira;
    }

    public void setBandeira(Bandeira bandeira) {
        this.bandeira = bandeira;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Boolean getPreferencial() {
        return preferencial;
    }

    public void setPreferencial(Boolean preferencial) {
        this.preferencial = preferencial;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bandeira == null) ? 0 : bandeira.hashCode());
        result = prime * result + ((cvv == null) ? 0 : cvv.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((numero == null) ? 0 : numero.hashCode());
        result = prime * result + ((preferencial == null) ? 0 : preferencial.hashCode());
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
        Cartao other = (Cartao) obj;
        if (bandeira != other.bandeira)
            return false;
        if (cvv == null) {
            if (other.cvv != null)
                return false;
        } else if (!cvv.equals(other.cvv))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (numero == null) {
            if (other.numero != null)
                return false;
        } else if (!numero.equals(other.numero))
            return false;
        if (preferencial == null) {
            if (other.preferencial != null)
                return false;
        } else if (!preferencial.equals(other.preferencial))
            return false;
        return true;
    }
}
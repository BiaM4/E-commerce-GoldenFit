package br.com.fatec.goldenfit.model;

import java.util.Date;

public class EntidadeDominio {
    private int id;
    private String pesquisa;
    private Date dtCadastro;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getDtCadastro() {
        return dtCadastro;
    }
    public void setDtCadastro(Date dtCadastro) {
        this.dtCadastro = dtCadastro;
    }
    public String getPesquisa() {
        return pesquisa;
    }
    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }
}

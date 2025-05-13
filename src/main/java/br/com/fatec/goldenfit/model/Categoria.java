package br.com.fatec.goldenfit.model;

import java.util.Date;

public class Categoria extends EntidadeDominio{
    private String nome;
    public Categoria() {
    }

    public Categoria(Integer id, String nome, Date dtCadastro) {
        super();
        if(id != null) {
            this.setId(id);
        }
        if(dtCadastro != null) {
            this.setDtCadastro(dtCadastro);
        }
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

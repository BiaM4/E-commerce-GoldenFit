package br.com.fatec.goldenfit.model;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private List<EntidadeDominio> entidades = new ArrayList();
    private String resposta;

    public Result() {
    }

    public List<EntidadeDominio> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<EntidadeDominio> entidades) {
        this.entidades = entidades;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public void addEntidades(EntidadeDominio entidade) {
        entidades.add(entidade);
    }
}

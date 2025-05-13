package br.com.fatec.goldenfit.model;

import br.com.fatec.goldenfit.model.enums.TipoEndereco;
import br.com.fatec.goldenfit.model.enums.TipoLogradouro;
import br.com.fatec.goldenfit.model.enums.TipoResidencia;

public class Endereco extends EntidadeDominio{
    private Cliente cliente;
    private Integer idCliente;
    private String descricao;
    private TipoLogradouro tipoLogradouro;
    private String logradouro;
    private String numero;
    private String complemento;
    private TipoResidencia tipoResidencia;
    private String bairro;
    private String cep;
    private Cidade cidade;
    private String observacao;
    private TipoEndereco tipoEndereco;

    public Endereco() {
    }


    public Endereco(String descricao, TipoLogradouro tipoLogradouro, String logradouro, String numero,
                    String complemento, TipoResidencia tipoResidencia, String bairro, String cep, Cidade cidade,
                    String observacao, TipoEndereco tipoEndereco) {
        super();
        this.descricao = descricao;
        this.tipoLogradouro = tipoLogradouro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.tipoResidencia = tipoResidencia;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.observacao = observacao;
        this.tipoEndereco = tipoEndereco;
    }

    public Endereco(String descricao, TipoLogradouro tipoLogradouro, String logradouro, String numero,
                    TipoResidencia tipoResidencia, String bairro, String cep, Cidade cidade,
                    String observacao, TipoEndereco tipoEndereco) {
        super();
        this.descricao = descricao;
        this.tipoLogradouro = tipoLogradouro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.tipoResidencia = tipoResidencia;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.observacao = observacao;
        this.tipoEndereco = tipoEndereco;
    }

    public Endereco(Integer id,String descricao, TipoLogradouro tipoLogradouro, String logradouro, String numero,
                    String complemento, TipoResidencia tipoResidencia, String bairro, String cep, Cidade cidade,
                    String observacao, TipoEndereco tipoEndereco, Integer idCliente) {
        super();
        if (id != null) {
            this.setId(id);
        }
        this.descricao = descricao;
        this.tipoLogradouro = tipoLogradouro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.tipoResidencia = tipoResidencia;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.observacao = observacao;
        this.tipoEndereco = tipoEndereco;
        if(idCliente != null && idCliente > 0) {
            this.idCliente = idCliente;
        }
    }



    public Endereco(Cliente cliente, String descricao, TipoLogradouro tipoLogradouro, String logradouro, String numero,
                    String complemento, TipoResidencia tipoResidencia, String bairro, String cep, Cidade cidade,
                    String observacao, TipoEndereco tipoEndereco) {
        super();
        this.cliente = cliente;
        this.descricao = descricao;
        this.tipoLogradouro = tipoLogradouro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.tipoResidencia = tipoResidencia;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.observacao = observacao;
        this.tipoEndereco = tipoEndereco;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoLogradouro getTipoLogradouro() {
        return tipoLogradouro;
    }

    public void setTipoLogradouro(TipoLogradouro tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public TipoResidencia getTipoResidencia() {
        return tipoResidencia;
    }

    public void setTipoResidencia(TipoResidencia tipoResidencia) {
        this.tipoResidencia = tipoResidencia;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public TipoEndereco getTipoEndereco() {
        return tipoEndereco;
    }

    public void setTipoEndereco(TipoEndereco tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getLabel() {
        StringBuilder label = new StringBuilder();
        if(descricao != null && !descricao.isEmpty()) {
            label.append(descricao);
        }
        label.append(" - ");
        if(tipoLogradouro != null && !tipoLogradouro.name().isEmpty()) {
            label.append(tipoLogradouro + " ");
        }
        if(logradouro != null && !logradouro.isEmpty()) {
            label.append(logradouro);
        }
        label.append(", ");
        if(numero != null && !numero.isEmpty()) {
            label.append(numero + ", ");
        }
        if(cidade != null && !cidade.getNome().isEmpty()) {
            label.append(cidade.getEstado().getNome()).append(" - ");

            if(cidade.getEstado() != null && !cidade.getEstado().getNome().isEmpty()) {
                label.append(cidade.getEstado().getSigla());
            }
        }

        if(cep != null && !cep.isEmpty()) {
            label.append(", " + cep);
        }

        return label.toString();
    }

}

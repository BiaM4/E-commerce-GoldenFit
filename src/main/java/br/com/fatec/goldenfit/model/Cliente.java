package br.com.fatec.goldenfit.model;

import java.util.Date;

public class Cliente extends EntidadeDominio {

    private String nome;
    private String sobrenome;
    private String genero;
    private Date dataNascimento;
    private String cpf;
    private Integer score;
    private Telefone telefoneResidencial;
    private Telefone telefoneCelular;
    private Endereco enderecoResidencial;

    private Usuario Usuario;

    public Cliente() {

    }

    public Cliente(Usuario usuario) {
        this.Usuario = usuario;
    }

    public Cliente(Integer id, String nome, String sobrenome, String genero, Date dataNascimento, String cpf, Integer score,
                   Telefone telefoneResidencial, Telefone telefoneCelular, Endereco enderecoResidencial, Usuario usuario) {
        super();
        this.setId(id);
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.score = score;
        this.telefoneResidencial = telefoneResidencial;
        this.telefoneCelular = telefoneCelular;
        this.enderecoResidencial = enderecoResidencial;
        this.Usuario = usuario;
    }

    public Cliente(String nome, String sobrenome, String genero, Date dataNascimento, String cpf, Integer score,
                   Telefone telefoneResidencial, Telefone telefoneCelular, Endereco enderecoResidencial, Usuario usuario) {
        super();
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.score = score;
        this.telefoneResidencial = telefoneResidencial;
        this.telefoneCelular = telefoneCelular;
        this.enderecoResidencial = enderecoResidencial;
        this.Usuario = usuario;
    }

    public Cliente(Integer id, String nome, String sobrenome, String genero, Date dataNascimento, String cpf, Integer score,
                   Telefone telefoneResidencial, Telefone telefoneCelular, Usuario usuario) {
        super();
        this.setId(id);
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.score = score;
        this.telefoneResidencial = telefoneResidencial;
        this.telefoneCelular = telefoneCelular;
        this.Usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Usuario getUsuario() {
        return Usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.Usuario = usuario;
    }

    public Telefone getTelefoneResidencial() {
        return telefoneResidencial;
    }

    public void setTelefoneResidencial(Telefone telefoneResidencial) {
        this.telefoneResidencial = telefoneResidencial;
    }

    public Telefone getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(Telefone telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public Endereco getEnderecoResidencial() {
        return enderecoResidencial;
    }

    public void setEnderecoResidencial(Endereco enderecoResidencial) {
        this.enderecoResidencial = enderecoResidencial;
    }

    public String getNomeCompleto() {
        return this.getNome() != null && this.getSobrenome() != null ?
                this.getNome() + " " + this.getSobrenome() : null;
    }


}

package br.com.fatec.goldenfit.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Usuario extends EntidadeDominio {
    private String email;
    private String senha;
    private Boolean status;
    private Boolean admin;

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    //Cria usuário com a senha criptografada
    public Usuario(String email, String senha, Boolean status, Boolean admin) {
        this.email = email;
        this.senha = criptografarSenha(senha);
        this.status = status;
        this.admin = admin;
    }

    public Usuario(Integer id, String email, String senha, Boolean admin) {
        super();
        this.setId(id);
        this.email = email;
        this.senha = senha;
        this.admin = admin;
    }

    public Usuario() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = criptografarSenha(senha);
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public boolean autentica(String email, String senha) {
        if (!this.email.equals(email)) {
            return false;
        }

        if (!this.senha.equals(senha)) {
            return false;
        }

        return true;
    }

    public boolean autenticaSenha(String senhaInformada, String confirmacaoSenha) {
        if (senhaInformada == null || confirmacaoSenha == null) {
            return false;
        }

        if (senhaInformada.equals("") || confirmacaoSenha.equals("")) {
            return false;
        }

        if (!senhaInformada.equals(confirmacaoSenha)) {
            return false;
        }

        if (!senhaInformada.equals(this.getSenha())) {
            return false;
        }

        return true;
    }

    //Criptografa a senha
    private String criptografarSenha(String senha) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(senha);
    }
}

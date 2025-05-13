package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.dao.UsuarioDAO;
import org.apache.commons.codec.digest.DigestUtils; // Você pode precisar adicionar uma dependência para Apache Commons Codec


import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.model.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;

public class LoginVH implements IViewHelper {

    private final BCryptPasswordEncoder passwordEncoder;

    public LoginVH() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        Usuario usuario = usuarioDAO.getUsuarioByEmail(email);

        String senhaCriptografadaDoBanco = usuario.getSenha();

        // Comparar a senha fornecida pelo usuário com a senha criptografada
        if (!passwordEncoder.matches(senha, senhaCriptografadaDoBanco)) {
            //Caso as senhas não se coincidem
            System.out.println("Senha invalida");
        } else {
            System.out.println("Senha válida");
            senha = usuario.getSenha();
        }

        usuario = new Usuario(email, senha);
        usuario.setPesquisa("email,senha");

        return usuario;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (resultado.getEntidades() == null || resultado.getEntidades().isEmpty()) {
            String mensagem = "Usuário ou senha inválido";
            response.sendRedirect(request.getContextPath() + "/view/login.jsp?mensagemErro="
                    + URLEncoder.encode(mensagem, "UTF-8"));
        } else {
            Usuario usuario = (Usuario) resultado.getEntidades().get(0);

            if (passwordEncoder.matches(request.getParameter("senha"), usuario.getSenha())) {
                if (usuario.getStatus()) {
                    if (usuario.getAdmin() != null && usuario.getAdmin()) {
                        response.sendRedirect(request.getContextPath() + "/view/painelAdmin.jsp");
                    } else {
                        String userId = String.valueOf(usuario.getId());
                        response.sendRedirect(request.getContextPath() + "/carregarDadosCliente?id=" + userId);
                    }
                } else {
                    String mensagem = "Usuário inativo. Entre em contato com o suporte.";
                    response.sendRedirect(request.getContextPath() + "/view/login.jsp?mensagemErro="
                            + URLEncoder.encode(mensagem, "UTF-8"));
                }
            } else {
                String mensagem = "Usuário ou senha inválido";
                response.sendRedirect(request.getContextPath() + "/view/login.jsp?mensagemErro="
                        + URLEncoder.encode(mensagem, "UTF-8"));
            }
        }
    }
}
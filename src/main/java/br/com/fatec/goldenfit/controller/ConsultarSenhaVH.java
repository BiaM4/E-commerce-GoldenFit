package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.dao.UsuarioDAO;
import br.com.fatec.goldenfit.model.Cliente;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.model.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;

public class ConsultarSenhaVH implements IViewHelper{

    private final BCryptPasswordEncoder passwordEncoder;

    public ConsultarSenhaVH() { this.passwordEncoder = new BCryptPasswordEncoder(); }

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        int usuarioId = ((Cliente) request.getSession().getAttribute("clienteLogado")).getUsuario().getId();

        String senhaFornecida = request.getParameter("senha");

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        Usuario usuario = usuarioDAO.getUsuarioById(usuarioId);

        if (usuario == null) {
            System.out.println("Usuário não encontrado");
            return null;
        }

        String senhaCriptografadaDoBanco = usuario.getSenha();

        if (!passwordEncoder.matches(senhaFornecida, senhaCriptografadaDoBanco)) {
            System.out.println("Senha inválida");
            return null;
        } else {
            System.out.println("Senha válida");
        }

        usuario.setPesquisa("senha,usuario");

        return usuario;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/view/redefinirSenhaCliente.jsp");
        }else {
            String mensagem = "A senha informada está incorreta";
            response.sendRedirect(request.getContextPath()
                    + "/view/confirmaSenhaAtual.jsp?mensagemErro=" + URLEncoder.encode(mensagem, "UTF-8" ));
        }
    }
}

package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.Cliente;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.model.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;

public class AlterarSenhaVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Usuario usuario = ((Cliente)request.getSession().getAttribute("clienteLogado")).getUsuario();
        usuario.setSenha(request.getParameter("senha"));
        usuario.setPesquisa("senha,usuario");

        return usuario;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(resultado.getResposta()==null) {
            response.sendRedirect(request.getContextPath() + "/view/listaClientes.jsp");
        }else {
            String mensagem = "Erro ao redefinir senha.";
            response.sendRedirect(request.getContextPath() + "/view/redefinirSenha.jsp?mensagemErro=" + URLEncoder.encode(mensagem, "UTF-8"));
        }
    }
}

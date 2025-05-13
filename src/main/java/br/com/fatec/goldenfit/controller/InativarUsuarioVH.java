package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.Cliente;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.model.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class InativarUsuarioVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Usuario usuario = ((Cliente) request.getSession().getAttribute("clienteLogado")).getUsuario();
        return usuario;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();;
        response.sendRedirect(request.getContextPath() + "/view/index");
    }
}

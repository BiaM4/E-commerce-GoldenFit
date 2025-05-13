package br.com.fatec.goldenfit.business;


import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Usuario;

public class ValidadorSenha implements IStrategy{
    @Override
    public String processar(EntidadeDominio entidadeDominio) {
        Usuario usuario = (Usuario) entidadeDominio;

        if(usuario.getSenha() == null && usuario.getSenha() == "") {
            return "A senha é obrigatória";
        }
        return null;
    }
}

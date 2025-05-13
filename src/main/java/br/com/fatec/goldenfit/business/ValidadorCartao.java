package br.com.fatec.goldenfit.business;

import br.com.fatec.goldenfit.dao.CartaoDAO;
import br.com.fatec.goldenfit.model.Cartao;
import br.com.fatec.goldenfit.model.EntidadeDominio;

public class ValidadorCartao implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
        Cartao cartao = (Cartao) entidadeDominio;
        cartao.setPesquisa("preferencial");
        CartaoDAO dao = new CartaoDAO();
        StringBuilder erros = new StringBuilder();

        if (cartao.getNumero() == null || cartao.getNumero() == "") {
            erros.append("O número do cartão é obrigatório");
        }

        if (cartao.getNome() == null || cartao.getNome() =="") {
            erros.append("O nome impresso no cartão é obrigatório");
        }

        if (cartao.getCvv() == null || cartao.getCvv() == "") {
            erros.append("O código de segurança é obrigatório");
        }

        if (cartao.getBandeira() == null) {
            erros.append("A bandeira é obrigatória");
        }

//        if(dao.possuiCartaoPreferencial(cartao)) {
//            cartao.setPreferencial(false);
//        } else {
//            cartao.setPreferencial(true);
//        }

        if (erros != null && erros.length() > 0) {
            return erros.toString();
        } else {
            return null;
        }
    }
}

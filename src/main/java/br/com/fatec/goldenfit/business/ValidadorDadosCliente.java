package br.com.fatec.goldenfit.business;

import br.com.fatec.goldenfit.model.Cliente;
import br.com.fatec.goldenfit.model.EntidadeDominio;

public class ValidadorDadosCliente implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
        Cliente cliente = (Cliente) entidadeDominio;
        StringBuilder erros = new StringBuilder();

        if(cliente.getNome() == null || cliente.getNome().isEmpty()) {
            erros.append("O nome é obrigatório.\n");
        }

        if(cliente.getSobrenome() == null || cliente.getSobrenome().isEmpty()) {
            erros.append("O sobrenome é obrigatório.\n");
        }

        if(cliente.getGenero() == null || cliente.getGenero().isEmpty()) {
            erros.append("O gênero é obrigatório.\n");
        }

        if(cliente.getDataNascimento()== null) {
            erros.append("A data de nascimento é obrigatória.\n");
        }

        if(erros != null && erros.length() > 0) {
            return erros.toString();
        }else {
            return null;
        }
    }
}

package br.com.fatec.goldenfit.business;

import br.com.fatec.goldenfit.model.Endereco;
import br.com.fatec.goldenfit.model.EntidadeDominio;

public class ValidadorEndereco implements IStrategy{
    @Override
    public String processar(EntidadeDominio entidadeDominio) {
        Endereco endereco = (Endereco) entidadeDominio;
        StringBuilder erros = new StringBuilder();

        if (endereco.getLogradouro() == null || endereco.getLogradouro().isEmpty()) {
            erros.append("O logradouro é obrigatório");
        }

        if (endereco.getBairro() == null || endereco.getBairro().isEmpty()) {
            erros.append("O bairro é obrigatório");
        }

        if (endereco.getNumero() == null || endereco.getNumero().isEmpty()) {
            erros.append("O número é obrigatório");
        }

        if (endereco.getCidade() == null) {
            erros.append("A cidade é obrigatória");
        }

        if (endereco.getDescricao() == null || endereco.getDescricao().isEmpty()) {
            erros.append("A descricao é obrigatória");
        }

        if (endereco.getCep() == null || endereco.getCep().isEmpty()) {
            erros.append("O CEP é obrigatório");
        }

        if (endereco.getTipoLogradouro() == null) {
            erros.append("O tipo do logradouro é obrigatório");
        }
        if (endereco.getTipoResidencia() == null) {
            erros.append("O logradouro é obrigatório");
        }

        if(erros.toString() != null && erros.toString() != "") {
            return erros.toString();
        }else {
            return null;
        }
    }
}

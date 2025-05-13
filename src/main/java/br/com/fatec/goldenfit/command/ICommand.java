package br.com.fatec.goldenfit.command;

import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;

public interface ICommand {
    Result executar(EntidadeDominio entidade);
}

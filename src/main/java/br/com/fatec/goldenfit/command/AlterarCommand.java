package br.com.fatec.goldenfit.command;

import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;

public class AlterarCommand extends AbstractCommand {
    public Result executar(EntidadeDominio entidade) {
        return fachada.alterar(entidade);
    }
}

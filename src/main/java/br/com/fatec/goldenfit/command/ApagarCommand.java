package br.com.fatec.goldenfit.command;

import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;

public class ApagarCommand extends AbstractCommand {
    @Override
    public Result executar(EntidadeDominio entidade) {
        return fachada.excluir(entidade);
    }
}

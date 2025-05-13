package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;

public interface IFachada {
    public Result salvar(EntidadeDominio entidade);
    public Result alterar(EntidadeDominio entidade);
    public Result excluir(EntidadeDominio entidade);
    public Result consultar(EntidadeDominio entidade);
}

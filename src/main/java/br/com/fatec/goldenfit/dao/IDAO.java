package br.com.fatec.goldenfit.dao;

import br.com.fatec.goldenfit.model.EntidadeDominio;

import java.util.List;

public interface IDAO {
    public String salvar(EntidadeDominio entidade);
    public String alterar(EntidadeDominio entidade);
    public String excluir(EntidadeDominio entidade);
    public <T extends EntidadeDominio> List<T> consultar(EntidadeDominio entidade);

}

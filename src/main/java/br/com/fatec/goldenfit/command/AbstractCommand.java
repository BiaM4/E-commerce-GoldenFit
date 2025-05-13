package br.com.fatec.goldenfit.command;

import br.com.fatec.goldenfit.controller.Fachada;
import br.com.fatec.goldenfit.controller.IFachada;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;

public abstract class AbstractCommand implements ICommand{
         protected IFachada fachada = new Fachada();
    }

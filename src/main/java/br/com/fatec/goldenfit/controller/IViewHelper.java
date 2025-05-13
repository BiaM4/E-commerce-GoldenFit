package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IViewHelper {
        public EntidadeDominio getEntidade (HttpServletRequest request, HttpServletResponse response);
        void setView (Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException;
}

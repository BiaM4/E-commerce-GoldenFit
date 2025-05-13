package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ConsultarCuponsAdminVH implements IViewHelper {
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Cupom cupom = new Cupom();

        cupom.setPesquisa("cupons");
        return cupom;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/view/consultarCupom");
    }
}

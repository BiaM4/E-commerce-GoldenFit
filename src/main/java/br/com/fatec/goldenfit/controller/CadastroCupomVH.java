package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.Cupom;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.model.enums.TipoCupom;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;

public class CadastroCupomVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Cupom cupom = null;
        try {
            String tipoCupomString = request.getParameter("tipo");
            TipoCupom tipoCupom = TipoCupom.valueOf(tipoCupomString);
            cupom = new Cupom(
                    request.getParameter("codigo"),
                    request.getParameter("nome"),
                    Conversao.parseStringToDouble(request.getParameter("valor")),
                    Conversao.parseStringToDate(request.getParameter("validade"), "yyyy-MM-dd"),
                    tipoCupom,
                    Integer.parseInt(request.getParameter("cliente"))
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return cupom;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/view/consultarCupom");
    }
}

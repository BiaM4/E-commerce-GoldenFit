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

public class AlterarCupomVH implements IViewHelper {
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Cupom cupom = null;
        try {
            Integer id = Conversao.parseStringToInt(request.getParameter("id"));
            String codigo = request.getParameter("codigo");
            String nome = request.getParameter("nome");
            Double valor = Conversao.parseStringToDouble(request.getParameter("valor"));
            java.util.Date validade = Conversao.parseStringToDate(request.getParameter("validade"));
            TipoCupom tipo = TipoCupom.valueOf(request.getParameter("tipo"));
            boolean status = Boolean.parseBoolean(request.getParameter("status"));

            cupom = new Cupom(id, codigo, nome, valor, validade, tipo, null, null, status);

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return cupom;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/view/consultarCupom");
    }
}

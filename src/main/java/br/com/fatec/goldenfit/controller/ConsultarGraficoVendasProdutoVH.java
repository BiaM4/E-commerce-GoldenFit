package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.business.FiltroPesquisaPeriodoGrafico;
import br.com.fatec.goldenfit.dto.GraficoVendasDTO;
import br.com.fatec.goldenfit.dto.VendaPeriodoDTO;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class ConsultarGraficoVendasProdutoVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        FiltroPesquisaPeriodoGrafico filtroPesquisa = new FiltroPesquisaPeriodoGrafico();
        try {
            Date dataInicial = Conversao.parseStringToDate(request.getParameter("dataInicial"), "yyyy-MM-dd");
            Date dataFinal = Conversao.parseStringToDate(request.getParameter("dataFinal"), "yyyy-MM-dd");
            filtroPesquisa = new FiltroPesquisaPeriodoGrafico(dataInicial, dataFinal);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return filtroPesquisa;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder jsonSimulacao = new StringBuilder();

        if (resultado.getEntidades() != null && !resultado.getEntidades().isEmpty()) {
            jsonSimulacao.append("[");
            for (EntidadeDominio entidade : resultado.getEntidades()) {
                GraficoVendasDTO dto = (GraficoVendasDTO) entidade;
                jsonSimulacao.append("{\"category\":\"" + dto.getPeriodo() + "\",");

                for (VendaPeriodoDTO dadoVendaDto : dto.getDadosVenda()) {
                    jsonSimulacao.append("\"" + dadoVendaDto.getNome() + "\":" + dadoVendaDto.getQuantidadeTotal());

                    if (dadoVendaDto != dto.getDadosVenda().get(dto.getDadosVenda().size() - 1)) {
                        jsonSimulacao.append(",");
                    } else {
                        jsonSimulacao.append("}");
                    }
                }
                if (entidade != resultado.getEntidades().get(resultado.getEntidades().size() - 1)) {
                    jsonSimulacao.append(",");
                }
            }
            jsonSimulacao.append("]");
        } else {
            jsonSimulacao.append("null");
        }

        request.getSession().setAttribute("listaDto", resultado.getEntidades());
        request.getSession().setAttribute("dadosGrafico", jsonSimulacao.toString());
        response.sendRedirect(request.getContextPath() + "/view/dashboard.jsp");
    }
}

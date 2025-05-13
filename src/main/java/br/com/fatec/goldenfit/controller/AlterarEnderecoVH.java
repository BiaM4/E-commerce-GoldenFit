package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.Cidade;
import br.com.fatec.goldenfit.model.Endereco;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.model.enums.Estado;
import br.com.fatec.goldenfit.model.enums.TipoEndereco;
import br.com.fatec.goldenfit.model.enums.TipoLogradouro;
import br.com.fatec.goldenfit.model.enums.TipoResidencia;
import br.com.fatec.goldenfit.util.Conversao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AlterarEnderecoVH implements IViewHelper {
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Cidade cidade = new Cidade(request.getParameter("cidade"), Estado.getBySigla(request.getParameter("estado")));

        TipoEndereco tipoEndereco = request.getParameter("tipoEndereco") != null ?
                TipoEndereco.valueOf(request.getParameter("tipoEndereco")) : TipoEndereco.Residencial;

        String idClienteParam = request.getParameter("idCliente");
        int idCliente = idClienteParam != null ? Integer.parseInt(idClienteParam) : 0;

        Endereco endereco = new Endereco(Conversao.parseStringToInt(request.getParameter("id")),
                request.getParameter("descricaoEndereco"),
                TipoLogradouro.valueOf(request.getParameter("tipoLogradouro")), request.getParameter("logradouro"),
                request.getParameter("numeroEndereco"), request.getParameter("complemento"),
                TipoResidencia.valueOf(request.getParameter("tipoResidencia")), request.getParameter("bairro"),
                request.getParameter("cep"), cidade, request.getParameter("observacaoEndereco"),
                tipoEndereco, idCliente);

        return endereco;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/carregarDadosEndereco");
    }
}

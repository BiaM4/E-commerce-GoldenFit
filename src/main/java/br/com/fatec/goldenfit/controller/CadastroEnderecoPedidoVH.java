package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.*;
import br.com.fatec.goldenfit.model.enums.Estado;
import br.com.fatec.goldenfit.model.enums.TipoEndereco;
import br.com.fatec.goldenfit.model.enums.TipoLogradouro;
import br.com.fatec.goldenfit.model.enums.TipoResidencia;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CadastroEnderecoPedidoVH implements IViewHelper{
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Cliente cliente = (Cliente) request.getSession().getAttribute("clienteLogado");

        Cidade cidade = new Cidade(request.getParameter("cidade"), Estado.getBySigla(request.getParameter("estado")));

        Endereco endereco = new Endereco();
        endereco.setCliente(cliente);
        endereco.setDescricao(request.getParameter("descricaoEndereco"));
        endereco.setTipoLogradouro(TipoLogradouro.valueOf(request.getParameter("tipoLogradouro")));
        endereco.setLogradouro(request.getParameter("logradouro"));
        endereco.setNumero(request.getParameter("numeroEndereco"));

        String complemento = request.getParameter("complemento");
        if (complemento != null && !complemento.isEmpty()) {
            endereco.setComplemento(complemento);
        }

        endereco.setTipoResidencia(TipoResidencia.valueOf(request.getParameter("tipoResidencia")));
        endereco.setBairro(request.getParameter("bairro"));
        endereco.setCep(request.getParameter("cep"));
        endereco.setCidade(cidade);
        endereco.setObservacao(request.getParameter("observacaoEndereco"));

        endereco.setTipoEndereco(TipoEndereco.valueOf(request.getParameter("tipoEndereco")));

        return endereco;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/view/finalizarPedido");
    }
}

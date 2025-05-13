package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.Cliente;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Result;
import br.com.fatec.goldenfit.model.Telefone;
import br.com.fatec.goldenfit.model.enums.TipoTelefone;
import br.com.fatec.goldenfit.util.Conversao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;

public class AlterarClienteVH implements IViewHelper {
    private static final String DDD_RESIDENCIAL = "dddResidencial";
    private static final String NUMERO_RESIDENCIAL = "numeroTelResidencial";
    private static final String DDD_CELULAR = "dddCelular";
    private static final String NUMERO_CELULAR = "numeroTelCelular";
    private static final String EMAIL = "email";
    private static final String SENHA = "senha";
    private static final String NOME = "nome";
    private static final String SOBRENOME = "sobrenome";
    private static final String GENERO = "genero";
    private static final String DATANASCIMENTO = "dataNascimento";
    private static final String CPF = "cpf";

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        Cliente cliente = (Cliente) request.getSession().getAttribute("clienteLogado");

        String dataNascimento = request.getParameter(DATANASCIMENTO);
        String dddResidencial = request.getParameter(DDD_RESIDENCIAL);
        String numeroResidencial = request.getParameter(NUMERO_RESIDENCIAL);
        String dddCelular = request.getParameter(DDD_CELULAR);
        String numeroCelular = request.getParameter(NUMERO_CELULAR);
        String email = request.getParameter(EMAIL);
        String nome = request.getParameter(NOME);
        String sobrenome = request.getParameter(SOBRENOME);
        String genero = request.getParameter(GENERO);
        String cpf = request.getParameter(CPF);

        cliente.setNome(nome);
        cliente.setSobrenome(sobrenome);
        cliente.setGenero(genero);
        cliente.setCpf(cpf);
        cliente.getUsuario().setEmail(email);

        try {
            cliente.setDataNascimento(Conversao.parseStringToDate(dataNascimento));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Telefone telefoneResidencial = new Telefone(cliente.getTelefoneResidencial().getId(),
                                                    dddResidencial,
                                                    numeroResidencial,
                                                    TipoTelefone.RESIDENCIAL);

        Telefone telefoneCelular = new Telefone(cliente.getTelefoneCelular().getId(),
                                                dddCelular,
                                                numeroCelular,
                                                TipoTelefone.CELULAR);

        cliente.setTelefoneResidencial(telefoneResidencial);
        cliente.setTelefoneCelular(telefoneCelular);
        return cliente;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/carregarDadosCliente");
    }
}

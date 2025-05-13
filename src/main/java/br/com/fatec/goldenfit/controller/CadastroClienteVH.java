package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.*;
import br.com.fatec.goldenfit.model.enums.*;
import br.com.fatec.goldenfit.util.Conversao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;

public class CadastroClienteVH implements IViewHelper{

    private static final String CIDADE = "cidade";
    private static final String ESTADO = "estado";
    private static final String DESCRICAO_ENDERECO = "descricaoEndereco";
    private static final String TIPO_LOGRADOURO = "tipoLogradouro";
    private static final String LOGRADOURO = "logradouro";
    private static final String NUMERO_ENDERECO = "numeroEndereco";
    private static final String COMPLEMENTO = "complemento";
    private static final String TIPO_RESIDENCIA = "tipoResidencia";
    private static final String BAIRRO = "bairro";
    private static final String CEP = "cep";
    private static final String OBSERVACAO_ENDERECO = "observacaoEndereco";
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
    private static final String SCORE = "score";

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Cadastrando Novo Cliente");

        Cidade cidade = new Cidade();
        Endereco endereco = new Endereco();
        Telefone telefoneResidencial = new Telefone();
        Telefone telefoneCelular = new Telefone();
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente();

        String nomeCidade = request.getParameter(CIDADE);
        String nomeEstado = request.getParameter(ESTADO);
        String descricaoEndereco = request.getParameter(DESCRICAO_ENDERECO);
        String tipoLogradouro = request.getParameter(TIPO_LOGRADOURO);
        String logradouro = request.getParameter(LOGRADOURO);
        String numeroEndereco = request.getParameter(NUMERO_ENDERECO);
        String complemento = request.getParameter(COMPLEMENTO);
        String tipoResidencia = request.getParameter(TIPO_RESIDENCIA);
        String bairro = request.getParameter(BAIRRO);
        String cep = request.getParameter(CEP);
        String observacaoEndereco = request.getParameter(OBSERVACAO_ENDERECO);
        String dddResidencial = request.getParameter(DDD_RESIDENCIAL);
        String numeroResidencial = request.getParameter(NUMERO_RESIDENCIAL);
        String dddCelular = request.getParameter(DDD_CELULAR);
        String numeroCelular = request.getParameter(NUMERO_CELULAR);
        String email = request.getParameter(EMAIL);
        String senha = request.getParameter(SENHA);
        String nome = request.getParameter(NOME);
        String sobrenome = request.getParameter(SOBRENOME);
        String genero = request.getParameter(GENERO);
        String dataNascimento = request.getParameter(DATANASCIMENTO);
        String cpf = request.getParameter(CPF);
        String score = request.getParameter(SCORE);

        cidade = new Cidade(
                nomeCidade,
                Estado.getBySigla(nomeEstado)
        );

        if (complemento != null && !complemento.isEmpty()) {
            endereco = new Endereco(
                    descricaoEndereco,
                    TipoLogradouro.valueOf(tipoLogradouro),
                    logradouro,
                    numeroEndereco,
                    complemento,
                    TipoResidencia.valueOf(tipoResidencia),
                    bairro,
                    cep,
                    cidade,
                    observacaoEndereco,
                    TipoEndereco.Residencial
            );
        } else {
            endereco = new Endereco(
                    descricaoEndereco,
                    TipoLogradouro.valueOf(tipoLogradouro),
                    logradouro,
                    numeroEndereco,
                    TipoResidencia.valueOf(tipoResidencia),
                    bairro,
                    cep,
                    cidade,
                    observacaoEndereco,
                    TipoEndereco.Residencial
            );
        }

        System.out.println(endereco);

        telefoneResidencial = new Telefone(
                dddResidencial,
                numeroResidencial,
                TipoTelefone.RESIDENCIAL
        );

        telefoneCelular = new Telefone(
                dddCelular,
                numeroCelular,
                TipoTelefone.CELULAR
        );

        usuario = new Usuario(
                email,
                senha,
                true,
                false
        );

        cliente = null;

        try {
            cliente = new Cliente(
                    nome,
                    sobrenome,
                    genero,
                    Conversao.parseStringToDate(dataNascimento),
                    cpf,
                    Conversao.parseStringToInt(score),
                    telefoneResidencial,
                    telefoneCelular,
                    endereco,
                    usuario
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return cliente;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/view/login.jsp");
    }
}

package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.*;
import br.com.fatec.goldenfit.model.enums.Tamanho;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.String;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CadastroProdutoVH implements IViewHelper {
    private static final String NOME = "nome";
    private static final String CODIGO = "codigo";
    private static final String DESCRICAO = "descricao";
    private static final String COR = "cor";
    private static final String GENERO = "genero";
    private static final String TAMANHO = "tamanho";
    private static final String REFERENCIA = "referencia";
    private static final String STATUS = "status";
    private static final String IMAGEM = "imagem";
    private static final String CATEGORIA = "categorias";
    private static final String GRUPO_PRECIFICACAO = "grupoPrecificacao";
    private static final String MARCA = "marca";

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Cadastrando Novo Produto");

        Produto produto = new Produto();

        String nome = request.getParameter(NOME);
        String codigo = request.getParameter(CODIGO);
        String descricao = request.getParameter(DESCRICAO);
        String cor = request.getParameter(COR);
        String genero = request.getParameter(GENERO);
        String tamanho = request.getParameter(TAMANHO);
        String referencia = request.getParameter(REFERENCIA);
        String status = request.getParameter(STATUS);
        String imagem = request.getParameter(IMAGEM);
        String[] categoriaIds = request.getParameterValues(CATEGORIA);
        String grupoPrecificacaoId = request.getParameter(GRUPO_PRECIFICACAO);
        String marca = request.getParameter(MARCA);

        System.out.println(categoriaIds);

        List<Categoria> categorias = new ArrayList<>();
        if (categoriaIds != null) {
            for (String categoriaId : categoriaIds) {
                Categoria categoria = new Categoria();
                categoria.setId(Integer.parseInt(categoriaId));
                categorias.add(categoria);
            }
        }

        GrupoPrecificacao grupoPrecificacao = new GrupoPrecificacao();
        grupoPrecificacao.setId(Integer.parseInt(grupoPrecificacaoId));

        produto = new Produto(
                codigo,
                nome,
                descricao,
                status,
                referencia,
                cor,
                marca,
                genero,
                tamanho,
                imagem,
                categorias,
                grupoPrecificacao
        );

        System.out.println(produto);
        return produto;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/view/consultarProdutos.jsp");
    }
}

package br.com.fatec.goldenfit.controller;

import br.com.fatec.goldenfit.model.*;
import br.com.fatec.goldenfit.util.Conversao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlterarProdutoVH implements IViewHelper{
    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String CODIGO = "codigo";
    private static final String DESCRICAO = "descricao";
    private static final String COR = "cor";
    private static final String GENERO = "genero";
    private static final String TAMANHO = "tamanho";
    private static final String REFERENCIA = "referencia";
//    private static final String QTD_ESTOQUE = "qtdEstoque";
    private static final String STATUS = "status";
//    private static final String VALOR = "valor";
    private static final String IMAGEM = "imagem";
    private static final String CATEGORIA = "categorias";
    private static final String GRUPO_PRECIFICACAO = "grupoPrecificacao";
    private static final String MARCA = "marca";
    private static final String MOTIVO = "motivo";

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Editar Produto");

        Produto produto = new Produto();

        String produtoId = request.getParameter(ID);
        String nome = request.getParameter(NOME);
        String codigo = request.getParameter(CODIGO);
        String descricao = request.getParameter(DESCRICAO);
        String cor = request.getParameter(COR);
        String genero = request.getParameter(GENERO);
        String tamanho = request.getParameter(TAMANHO);
        String referencia = request.getParameter(REFERENCIA);
//        String qtdEstoque = request.getParameter(QTD_ESTOQUE);
        String status = request.getParameter(STATUS);
//        String valor = request.getParameter(VALOR);
        String imagem = request.getParameter(IMAGEM);
        String[] categoriaIds = request.getParameterValues(CATEGORIA);
        String grupoPrecificacaoId = request.getParameter(GRUPO_PRECIFICACAO);
        String marca = request.getParameter(MARCA);
        String motivo = request.getParameter(MOTIVO);

            try {
                Integer id = Integer.parseInt(produtoId);
//                Double preco = Double.parseDouble(valor);
//                Double qtd_estoque = Double.parseDouble(qtdEstoque);

                System.out.println(categoriaIds);

                List<Categoria> categorias = new ArrayList<>();
                if (categoriaIds != null) {
                    for (String categoriaId : categoriaIds) {
                        Categoria categoria = new Categoria();
                        categoria.setId(Integer.parseInt(categoriaId));
                        categorias.add(categoria);
                    }
                }

                int grupo = Integer.parseInt(grupoPrecificacaoId);

                GrupoPrecificacao grupoPrecificacao = new GrupoPrecificacao();
                grupoPrecificacao.setId(grupo);

                produto = new Produto(
                        id,
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
                        motivo,
                        categorias,
                        grupoPrecificacao
                );

                System.out.println(produto);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        return produto;
    }

    @Override
    public void setView(Result resultado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/view/carregarTodosProdutosAtualizados");
    }
}

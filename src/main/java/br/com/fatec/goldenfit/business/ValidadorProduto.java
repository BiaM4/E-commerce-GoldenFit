package br.com.fatec.goldenfit.business;

import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Produto;

public class ValidadorProduto implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
        Produto produto = (Produto) entidadeDominio;
        StringBuilder erros = new StringBuilder();

        if (produto.getNome() == null || produto.getNome().isEmpty()) {
            erros.append("O nome do pedido é obrigatório. ");
        }

        if (produto.getDescricao() == null || produto.getDescricao().isEmpty()) {
            erros.append("A descrição do pedido é obrigatória. ");
        }

//        if (produto.getPreco() == null || produto.getPreco() <= 0) {
//            erros.append("O preço do pedido deve ser maior que zero. ");
//        }

        if (produto.getStatus() == null || produto.getStatus().isEmpty()) {
            erros.append("O status do pedido é obrigatório. ");
        }

        if (produto.getReferencia() == null || produto.getReferencia().isEmpty()) {
            erros.append("A referência do pedido é obrigatória. ");
        }

        if (produto.getCor() == null || produto.getCor().isEmpty()) {
            erros.append("A cor do pedido é obrigatória. ");
        }

        if (produto.getMarca() == null || produto.getMarca().isEmpty()) {
            erros.append("A marca do pedido é obrigatória. ");
        }

        if (produto.getGenero() == null || produto.getGenero().isEmpty()) {
            erros.append("O gênero do pedido é obrigatório. ");
        }

        if (produto.getTamanho() == null || produto.getTamanho().isEmpty()) {
            erros.append("O tamanho do pedido é obrigatório. ");
        }

//        if (produto.getQtdEstoque() == null || produto.getQtdEstoque() <= 0) {
//            erros.append("A quantidade em estoque do pedido deve ser maior que zero. ");
//        }

        if (produto.getCategoria() == null) {
            erros.append("A categoria do pedido é obrigatória. ");
        }

        if (produto.getGrupoPrecificacao() == null) {
            erros.append("O grupo de precificação do pedido é obrigatório. ");
        }

        if (erros.length() > 0) {
            return erros.toString();
        } else {
            return null;
        }
    }
}

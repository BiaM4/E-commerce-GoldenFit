package br.com.fatec.goldenfit.model;

import java.util.ArrayList;
import java.util.List;

public class Produto extends EntidadeDominio{
    private String code;
    private String nome;
    private String descricao;
    private Double preco;
    private String status;
    private String referencia;
    private String cor;
    private String marca;
    private String genero;
    private String tamanho;
    private Double qtdEstoque;
    private Double qtdDisponivelCompra;
    private String linkImagem;
    private List<Categoria> categorias;
    private GrupoPrecificacao grupoPrecificacao;
    private Double qtdEstoqueItem;
    private String motivo;

    public Produto() {
    }

    public Produto(int pro_id,
                   String pro_codigo,
                   String pro_nome,
                   String pro_descricao,
                   double pro_valor,
                   String pro_status,
                   String pro_referencia,
                   String pro_cor,
                   String pro_marca,
                   String pro_genero,
                   String pro_tamanho,
                   String pro_linkImagem,
                   GrupoPrecificacao pro_gru_id,
                   Double proId) {
    }

    public Produto(Integer id, String code, String nome, String descricao, String status,
                   String referencia, String cor, String marca, String genero, String tamanho,
                   String linkImagem, String motivo, List<Categoria> categorias, GrupoPrecificacao grupoPrecificacao) {
        super();
        if (id != null) {
            this.setId(id);
        }
        if(code != null) {
            this.code = code;
        }
        if(nome != null) {
            this.nome = nome;
        }
        if(descricao != null) {
            this.descricao = descricao;
        }
        if(status != null) {
            this.status = status;
        }
        if(referencia != null) {
            this.referencia = referencia;
        }
        if(cor != null) {
            this.cor = cor;
        }
        if(marca != null) {
            this.marca = marca;
        }
        if(genero != null){
            this.genero = genero;
        }
        if(tamanho != null) {
            this.tamanho = tamanho;
        }
        if(linkImagem != null) {
            this.linkImagem = linkImagem;
        }
        if(motivo != null){
            this.motivo = motivo;
        }
        if(categorias != null) {
            this.categorias = categorias;
        }
        if (grupoPrecificacao != null) {
            this.grupoPrecificacao = grupoPrecificacao;
        }
    }

    public Produto(int id, String code, String nome, String descricao, Double preco, String status,
                   String referencia, String cor, String marca, String genero, String tamanho,
                   Double qtdEstoque, Double qtdDisponivelCompra, String linkImagem, List<Categoria> categorias,
                   GrupoPrecificacao grupoPrecificacao, Double qtdEstoqueItem) {
        this.setId(id);
        this.code = code;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.status = status;
        this.referencia = referencia;
        this.cor = cor;
        this.marca = marca;
        this.genero = genero;
        this.tamanho = tamanho;
        this.qtdEstoque = qtdEstoque;
        this.qtdDisponivelCompra = qtdDisponivelCompra;
        this.linkImagem = linkImagem;
        this.categorias = categorias;
        this.grupoPrecificacao = grupoPrecificacao;
        this.qtdEstoqueItem = qtdEstoqueItem;
    }

    public Produto(String code, String nome, String descricao, String status, String referencia,
                   String cor, String marca, String genero, String tamanho, String linkImagem,
                   List<Categoria> categorias, GrupoPrecificacao grupoPrecificacao) {
        this.code = code;
        this.nome = nome;
        this.descricao = descricao;
        this.status = status;
        this.referencia = referencia;
        this.cor = cor;
        this.marca = marca;
        this.genero = genero;
        this.tamanho = tamanho;
        this.linkImagem = linkImagem;
        this.categorias = categorias;
        this.grupoPrecificacao = grupoPrecificacao;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Double getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(Double qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public Double getQtdDisponivelCompra(Carrinho carrinho) {
        qtdDisponivelCompra = getQtdEstoque() != null ? getQtdEstoque() : 0;
        if(carrinho != null && carrinho.getItens() != null) {
            for(CarrinhoItem itemCarrinho : carrinho.getItens()) {
                if(itemCarrinho.getProduto().getId() == this.getId()
                        && getQtdEstoque() > itemCarrinho.getQuantidade()) {
                    qtdDisponivelCompra = this.getQtdEstoque() - itemCarrinho.getQuantidade();
                }
            }
        }
        return qtdDisponivelCompra;
    }

    public Double getQtdDisponivelCompra() {
        if(qtdDisponivelCompra == null) {
            qtdDisponivelCompra = 0d;
        }
        return qtdDisponivelCompra;
    }

    public void setQtdDisponivelCompra(Double qtdDisponívelCompra) {
        this.qtdDisponivelCompra = qtdDisponívelCompra;
    }

    public String getLinkImagem() {
        return linkImagem;
    }

    public void setLinkImagem(String linkImagem) {
        this.linkImagem = linkImagem;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public List<Categoria> getCategoria() {
        return categorias;
    }

    public void setCategoria(String nomeCategoria) {
        if (categorias == null) {
            categorias = new ArrayList<>();
        }
        Categoria novaCategoria = new Categoria();
        novaCategoria.setNome(nomeCategoria);
        this.categorias.add(novaCategoria);
    }

    public GrupoPrecificacao getGrupoPrecificacao() {
        return grupoPrecificacao;
    }

    public void setGrupoPrecificacao(GrupoPrecificacao grupoPrecificacao) {
        this.grupoPrecificacao = grupoPrecificacao;
    }

}

package br.com.fatec.goldenfit.dao;

import br.com.fatec.goldenfit.dao.db.Db;
import br.com.fatec.goldenfit.model.Categoria;
import br.com.fatec.goldenfit.model.Cliente;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Produto;
import br.com.fatec.goldenfit.util.Calculadora;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO extends AbstractDAO {
    @Override
    public String salvar(EntidadeDominio entidade) {
        java.util.Date dataAtual = new java.util.Date();
        java.sql.Date dataSql = new java.sql.Date(dataAtual.getTime());

        Produto produto = (Produto) entidade;
        try {
            inicializarConexao();
            conn = Db.getConnection();

            System.out.println(produto.getCategoria());

            PreparedStatement st = conn.prepareStatement("INSERT INTO produto (pro_dtCadastro, pro_codigo, pro_nome, pro_descricao, " +
                            "pro_status, pro_referencia, pro_cor, pro_marca, pro_genero, pro_tamanho, pro_linkImagem, pro_gru_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setDate(1, dataSql);
            st.setString(2, produto.getCode());
            st.setString(3, produto.getNome());
            st.setString(4, produto.getDescricao());
            st.setString(5, produto.getStatus());
            st.setString(6, produto.getReferencia());
            st.setString(7, produto.getCor());
            st.setString(8, produto.getMarca());
            st.setString(9, produto.getGenero());
            st.setString(10, produto.getTamanho());
            st.setString(11, produto.getLinkImagem());
            st.setInt(12, produto.getGrupoPrecificacao().getId());

            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Produto cadastrado com sucesso! Linhas afetadas: " + linhasAfetadas);

                ResultSet generatedKeys = st.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int produtoId = generatedKeys.getInt(1);

                    List<Categoria> categorias = produto.getCategoria();
                    if (categorias != null) {
                        for (Categoria categoria : categorias) {
                            PreparedStatement stRelacao = conn.prepareStatement("INSERT INTO produto_categoria (prc_cat_id, prc_pro_id) VALUES (?, ?)");
                            stRelacao.setInt(1, categoria.getId());
                            stRelacao.setInt(2, produtoId);
                            stRelacao.executeUpdate();
                        }
                    }

                    PreparedStatement stUpdate = conn.prepareStatement("INSERT INTO estoque_item (esi_dtCadastro, esi_quantidade, esi_pro_id) VALUES (?, ?, ?)");
                    stUpdate.setDate(1, dataSql);
                    stUpdate.setDouble(2, 0);
                    stUpdate.setInt(3, produtoId);
                    int linhasAfetadasUpdate = stUpdate.executeUpdate();
                    if (linhasAfetadasUpdate > 0) {
                        System.out.println("Produto adicionado ao estoque com sucesso!");
                    }
                }
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao salvar";
        } finally {
            finalizarConexao();
        }
    }

    @Override
    public String alterar(EntidadeDominio entidade) {
        Produto produto = (Produto) entidade;
        System.out.println(produto.getId());
        try {
            inicializarConexao();
            conn = Db.getConnection();

            System.out.println("Categorias: " + produto.getCategoria());

            List<Categoria> categoriasAntigas = getCategoriasDoProduto(produto.getId());

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE produto SET pro_codigo = ?, pro_nome = ?, pro_descricao = ?, ");
            sql.append("pro_status = ?, pro_referencia = ?, pro_cor = ?, pro_marca = ?, pro_genero = ?, pro_tamanho = ?, ");
            sql.append("pro_motivo = ?, pro_gru_id = ?");

            if (produto.getLinkImagem() != null && !produto.getLinkImagem().isEmpty()) {
                sql.append(", pro_linkImagem = ?");
            }

            sql.append(" WHERE pro_id = ?");

            st = conn.prepareStatement(sql.toString());

            int parameterIndex = 1;
            st.setString(parameterIndex++, produto.getCode());
            st.setString(parameterIndex++, produto.getNome());
            st.setString(parameterIndex++, produto.getDescricao());
            st.setString(parameterIndex++, produto.getStatus());
            st.setString(parameterIndex++, produto.getReferencia());
            st.setString(parameterIndex++, produto.getCor());
            st.setString(parameterIndex++, produto.getMarca());
            st.setString(parameterIndex++, produto.getGenero());
            st.setString(parameterIndex++, produto.getTamanho());
            st.setString(parameterIndex++, produto.getMotivo());
            st.setInt(parameterIndex++, produto.getGrupoPrecificacao().getId());

            if (produto.getLinkImagem() != null && !produto.getLinkImagem().isEmpty()) {
                st.setString(parameterIndex++, produto.getLinkImagem());
            }

            st.setInt(parameterIndex++, produto.getId());

            int rowsAffected = st.executeUpdate();
            System.out.println("Done! Rows affected: " + rowsAffected);

            if (!categoriasAntigas.equals(produto.getCategoria())) {
                atualizarCategoriasDoProduto(produto);
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao alterar";
        } finally {
            finalizarConexao();
        }
    }

    //Método que atualiza as categorias do produto
    private void atualizarCategoriasDoProduto(Produto produto) throws SQLException {
        // Delete existing categories
        String deleteSql = "DELETE FROM produto_categoria WHERE prc_pro_id = ?";
        try (PreparedStatement deleteSt = conn.prepareStatement(deleteSql)) {
            deleteSt.setInt(1, produto.getId());
            deleteSt.executeUpdate();
        }

        // Insert new categories
        String insertSql = "INSERT INTO produto_categoria (prc_pro_id, prc_cat_id) VALUES (?, ?)";
        try (PreparedStatement insertSt = conn.prepareStatement(insertSql)) {
            for (Categoria categoria : produto.getCategoria()) {
                insertSt.setInt(1, produto.getId());
                insertSt.setInt(2, categoria.getId());
                insertSt.executeUpdate();
            }
        }
    }

    // Método para obter as categorias associadas a um produto
    private List<Categoria> getCategoriasDoProduto(int produtoId) throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT prc_cat_id FROM produto_categoria WHERE prc_pro_id = ?");
            st.setInt(1, produtoId);
            rs = st.executeQuery();

            while (rs.next()) {
                int categoriaId = rs.getInt("prc_cat_id");
                Categoria categoria = new Categoria();
                categoria.setId(categoriaId);
                categorias.add(categoria);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }

        return categorias;
    }

    @Override
    public String excluir(EntidadeDominio entidade) {
        Produto produto = (Produto) entidade;
        System.out.println("Excluindo produto com ID: " + produto.getId());
        inicializarConexao();
        try {
            conn = Db.getConnection();

            PreparedStatement stExcluirMovimentacoes = conn.prepareStatement("DELETE FROM movimentacao_estoque WHERE mov_pro_id = ?");
            stExcluirMovimentacoes.setInt(1, produto.getId());
            int linhasAfetadasMovimentacoes = stExcluirMovimentacoes.executeUpdate();
            System.out.println("Movimentações de estoque do produto excluídas com sucesso! Linhas afetadas: " + linhasAfetadasMovimentacoes);

            PreparedStatement stExcluirEstoque = conn.prepareStatement("DELETE FROM estoque_item WHERE esi_pro_id = ?");
            stExcluirEstoque.setInt(1, produto.getId());
            int linhasAfetadasEstoque = stExcluirEstoque.executeUpdate();
            System.out.println("Entradas de estoque do produto excluídas com sucesso! Linhas afetadas: " + linhasAfetadasEstoque);

            PreparedStatement stExcluirProduto = conn.prepareStatement("DELETE FROM produto WHERE pro_id = ?");
            stExcluirProduto.setInt(1, produto.getId());
            int linhasAfetadasProduto = stExcluirProduto.executeUpdate();
            System.out.println("Produto excluído com sucesso! Linhas afetadas: " + linhasAfetadasProduto);

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao excluir produto";
        } finally {
            finalizarConexao();
        }
    }

    private String pesquisarAuxiliar(EntidadeDominio entidade) {
        if (entidade.getPesquisa() != null && entidade.getPesquisa().equals("id")) {
            return "select * from produto WHERE pro_id = ?";
        } else if (entidade.getPesquisa() != null && entidade.getPesquisa().equals("filtros")) {
            Produto produto = (Produto) entidade;
            StringBuilder sql = new StringBuilder();

            sql.append("select p.* from produto p ");

            // Check if category filter is applied
            boolean hasCategoriaFilter = produto.getCategoria() != null && !produto.getCategoria().isEmpty();
            if (hasCategoriaFilter) {
                sql.append("JOIN produto_categoria pc ON p.pro_id = pc.prc_pro_id ");
            }

            sql.append("WHERE 1 = 1 ");

            if (produto.getId() > 0) {
                sql.append("AND p.pro_id = ? ");
            }

            if (produto.getNome() != null) {
                sql.append("AND p.pro_nome like ? ");
            }

            if (produto.getDtCadastro() != null) {
                sql.append("AND p.pro_dtCadastro like ? ");
            }

            if (produto.getMarca() != null) {
                sql.append("AND p.pro_marca like ? ");
            }

            if (produto.getGenero() != null) {
                sql.append("AND p.pro_genero like ? ");
            }

            if (produto.getTamanho() != null) {
                sql.append("AND p.pro_tamanho like ? ");
            }

            if (hasCategoriaFilter) {
                sql.append("AND pc.prc_cat_id = ? ");
            }

            if (produto.getGrupoPrecificacao() != null) {
                sql.append("AND p.pro_gru_id like ? ");
            }

            if (produto.getStatus() != null) {
                sql.append("AND p.pro_status like ? ");
            }

            return sql.toString();
        } else {
            return "select * from produto";
        }
    }

    private PreparedStatement executarPesquisa(Produto produto, String sql) throws SQLException {
        PreparedStatement st = Db.getConnection().prepareStatement(sql);
        if (produto.getPesquisa() != null && produto.getPesquisa().equals("id")) {
            setaParametrosQuery(st, produto.getId());
        } else if (produto.getPesquisa() != null && produto.getPesquisa().equals("filtros")) {
            Integer filtroId = produto.getId() > 0 ? produto.getId() : null;
            String filtroNome = produto.getNome() != null ? "%" + produto.getNome() + "%" : null;
            String filtroMarca = produto.getMarca() != null ? "%" + produto.getMarca() + "%" : null;
            String filtroGenero = produto.getGenero() != null ? "%" + produto.getGenero() + "%" : null;
            String filtroTamanho = produto.getTamanho() != null ? "%" + produto.getTamanho() + "%" : null;

            String filtroCategoria = null;
            if (produto.getCategoria() != null && !produto.getCategoria().isEmpty()) {
                StringBuilder filtroBuilder = new StringBuilder();
                for (Categoria categoria : produto.getCategoria()) {
                    filtroBuilder.append(categoria.getNome());
                }
                filtroCategoria = filtroBuilder.toString();
            }

            String filtroGrupo = produto.getGrupoPrecificacao()!= null ? "%" + produto.getGrupoPrecificacao().getDescricao() : null;
            String filtroStatus = produto.getStatus() != null ? produto.getStatus() + "%" : null;

            setaParametrosQuery(st, filtroId, filtroNome, produto.getDtCadastro(), filtroMarca,
                    filtroGenero, filtroTamanho, filtroCategoria, filtroGrupo, filtroStatus);
        }
        return st;
    }

    public List<Produto> consultar(EntidadeDominio entidade) {
        Produto produto = (Produto) entidade;
        List<Produto> produtos = new ArrayList<>();
        GrupoPrecificacaoDAO grupoDAO = new GrupoPrecificacaoDAO();
        EstoqueItemDAO estoqueItemDAO = new EstoqueItemDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        try {
            inicializarConexao();
            conn = Db.getConnection();

            String sql = pesquisarAuxiliar(produto);
            st = executarPesquisa(produto, sql);

            Long inicioExecucao = System.currentTimeMillis();
            ResultSet rs = st.executeQuery();

            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Tempo de execução da consulta: "
                    + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");

            while (rs.next()) {
                int id = rs.getInt("pro_id");
                System.out.println(id);

                Produto produtoAux = new Produto(
                        rs.getInt("pro_id"),
                        rs.getString("pro_codigo"),
                        rs.getString("pro_nome"),
                        rs.getString("pro_descricao"),
                        rs.getDouble("pro_valor"),
                        rs.getString("pro_status"),
                        rs.getString("pro_referencia"),
                        rs.getString("pro_cor"),
                        rs.getString("pro_marca"),
                        rs.getString("pro_genero"),
                        rs.getString("pro_tamanho"),
                        rs.getDouble("pro_qtdEstoque"),
                        rs.getDouble("pro_qtdDisponivelCompra"),
                        rs.getString("pro_linkImagem"),
                        new ArrayList<>(), // Inicializar a lista de categorias
                        grupoDAO.getGrupoPrecificacaoById(rs.getInt("pro_gru_id")),
                        estoqueItemDAO.getQuantidadeEstoqueProduto(id)
                );

                // Buscar categorias associadas ao produto na tabela produto_categoria
                PreparedStatement stCategorias = conn.prepareStatement("SELECT prc_cat_id FROM produto_categoria WHERE prc_pro_id = ?");
                stCategorias.setInt(1, id);
                ResultSet rsCategorias = stCategorias.executeQuery();

                if (rsCategorias != null) {
                    while (rsCategorias.next()) {
                        int categoriaId = rsCategorias.getInt("prc_cat_id");
                        Categoria categoria = categoriaDAO.getCategoriaById(categoriaId);
                        if (categoria != null) {
                            produtoAux.getCategoria().add(categoria);
                        }
                    }
                } else {
                    System.out.println("ResultSet rsCategorias is null");
                }

                produtos.add(produtoAux);
            }
            return produtos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            finalizarConexao();
        }
    }

    public Produto getProdutoById(Integer produtoId) {
        Produto produto = null;
        GrupoPrecificacaoDAO grupoDAO = new GrupoPrecificacaoDAO();
        EstoqueItemDAO estoqueItemDAO = new EstoqueItemDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        System.out.println("Buscando produto id:" + produtoId);

        try {
            inicializarConexao();
            conn = Db.getConnection();

            st = conn.prepareStatement("SELECT * FROM produto WHERE pro_id = ? LIMIT 1");
            setaParametrosQuery(st, produtoId);
            Long inicioExecucao = System.currentTimeMillis();
            ResultSet rs = st.executeQuery();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Tempo de execução da consulta: "
                    + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");

            if (rs.next()) {
                int id = rs.getInt("pro_id");

                System.out.println("Produto encontrado.");
                produto = new Produto(
                        rs.getInt("pro_id"),
                        rs.getString("pro_codigo"),
                        rs.getString("pro_nome"),
                        rs.getString("pro_descricao"),
                        rs.getDouble("pro_valor"),
                        rs.getString("pro_status"),
                        rs.getString("pro_referencia"),
                        rs.getString("pro_cor"),
                        rs.getString("pro_marca"),
                        rs.getString("pro_genero"),
                        rs.getString("pro_tamanho"),
                        rs.getDouble("pro_qtdEstoque"),
                        rs.getDouble("pro_qtdDisponivelCompra"),
                        rs.getString("pro_linkImagem"),
                        new ArrayList<>(), // Inicializar a lista de categorias
                        grupoDAO.getGrupoPrecificacaoById(rs.getInt("pro_gru_id")),
                        estoqueItemDAO.getQuantidadeEstoqueProduto(id)
                );

                // Buscar categorias associadas ao produto na tabela produto_categoria
                PreparedStatement stCategorias = conn.prepareStatement("SELECT prc_cat_id FROM produto_categoria WHERE prc_pro_id = ?");
                stCategorias.setInt(1, id);
                ResultSet rsCategorias = stCategorias.executeQuery();

                while (rsCategorias.next()) {
                    int categoriaId = rsCategorias.getInt("prc_cat_id");
                    Categoria categoria = categoriaDAO.getCategoriaById(categoriaId);
                    if (categoria != null) {
                        produto.getCategoria().add(categoria);
                    }
                }

                System.out.println("Dados do produto: " + produto.getNome());
            } else {
                System.out.println("Produto não encontrado.");
            }

            return produto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            finalizarConexao();
        }
    }
}

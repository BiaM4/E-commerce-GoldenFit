package br.com.fatec.goldenfit.dao;

import br.com.fatec.goldenfit.dao.db.Db;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.MovimentacaoEstoque;
import br.com.fatec.goldenfit.model.enums.TipoMovimentacao;
import br.com.fatec.goldenfit.util.Calculadora;
import br.com.fatec.goldenfit.util.Conversao;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MovimentacaoEstoqueDAO extends AbstractDAO {
    @Override
    public String salvar(EntidadeDominio entidade) {
        java.util.Date dataAtual = new java.util.Date();
        java.sql.Date dataSql = new java.sql.Date(dataAtual.getTime());

        MovimentacaoEstoque movimentacao = (MovimentacaoEstoque) entidade;
        inicializarConexao();
        try {
            conn = Db.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement st = conn.prepareStatement("INSERT INTO movimentacao_estoque (mov_dtCadastro, mov_data, mov_quantidade, mov_precoCusto, "
                            + "mov_fornecedor, mov_entrada, mov_tipo, mov_pro_id)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setDate(1, dataSql);
            st.setDate(2, new java.sql.Date(movimentacao.getData().getTime()));
            st.setDouble(3, movimentacao.getQuantidade());
            st.setDouble(4, movimentacao.getPrecoCusto());
            st.setString(5, movimentacao.getFornecedor());
            st.setBoolean(6, movimentacao.getEntrada());
            st.setString(7, movimentacao.getTipo().name());
            st.setInt(8, movimentacao.getProduto().getId());

            st.executeUpdate();

            // Verifica o tipo de movimentação
            if (movimentacao.getTipo() == TipoMovimentacao.ENTRADA_POR_COMPRA) {
                atualizarEstoqueEntradaPorCompra(movimentacao);
            } else if (movimentacao.getTipo() == TipoMovimentacao.ENTRADA_POR_TROCA) {
                atualizarEstoqueEntradaPorTroca(movimentacao);
            } else if (movimentacao.getTipo() == TipoMovimentacao.SAIDA_POR_VENDA) {
                atualizarEstoqueSaidaPorVenda(movimentacao);
            }

            conn.commit();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return "Erro ao salvar";
        } finally {
            finalizarConexao();
        }
    }

    private void atualizarEstoqueEntradaPorCompra(MovimentacaoEstoque movimentacao) {
        try {
            // Atualiza a quantidade disponível para compra e em estoque do produto
            String queryUpdateProduto = "UPDATE produto SET pro_qtdDisponivelCompra = pro_qtdDisponivelCompra + ?, pro_qtdEstoque = pro_qtdEstoque + ? WHERE pro_id = ?";
            PreparedStatement stUpdateProduto = conn.prepareStatement(queryUpdateProduto);
            stUpdateProduto.setDouble(1, movimentacao.getQuantidade());
            stUpdateProduto.setDouble(2, movimentacao.getQuantidade());
            stUpdateProduto.setInt(3, movimentacao.getProduto().getId());
            stUpdateProduto.executeUpdate();

            // Atualiza a quantidade disponível para compra e em estoque do item na tabela estoque_item
            String queryUpdateEstoqueItem = "UPDATE estoque_item SET esi_quantidade = esi_quantidade + ? WHERE esi_pro_id = ?";
            PreparedStatement stUpdateEstoqueItem = conn.prepareStatement(queryUpdateEstoqueItem);
            stUpdateEstoqueItem.setDouble(1, movimentacao.getQuantidade());
            stUpdateEstoqueItem.setInt(2, movimentacao.getProduto().getId());
            stUpdateEstoqueItem.executeUpdate();

            // Calcula o valor de venda com base no maior valor de custo e na margem de lucro do grupo de precificação
            calcularValorVenda(movimentacao);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void atualizarEstoqueEntradaPorTroca(MovimentacaoEstoque movimentacao) {
        try {
            // Atualiza a quantidade disponível para compra e em estoque do produto
            String queryUpdateProduto = "UPDATE produto SET pro_qtdDisponivelCompra = pro_qtdDisponivelCompra + ?, pro_qtdEstoque = pro_qtdEstoque + ? WHERE pro_id = ?";
            PreparedStatement stUpdateProduto = conn.prepareStatement(queryUpdateProduto);
            stUpdateProduto.setDouble(1, movimentacao.getQuantidade());
            stUpdateProduto.setDouble(2, movimentacao.getQuantidade());
            stUpdateProduto.setInt(3, movimentacao.getProduto().getId());
            stUpdateProduto.executeUpdate();

            // Atualiza a quantidade disponível para compra e em estoque do item na tabela estoque_item
            String queryUpdateEstoqueItem = "UPDATE estoque_item SET esi_quantidade = esi_quantidade + ? WHERE esi_pro_id = ?";
            PreparedStatement stUpdateEstoqueItem = conn.prepareStatement(queryUpdateEstoqueItem);
            stUpdateEstoqueItem.setDouble(1, movimentacao.getQuantidade());
            stUpdateEstoqueItem.setInt(2, movimentacao.getProduto().getId());
            stUpdateEstoqueItem.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void atualizarEstoqueSaidaPorVenda(MovimentacaoEstoque movimentacao) {
        try {
            // Atualiza a quantidade disponível para compra e em estoque do produto
            String queryUpdateProduto = "UPDATE produto SET pro_qtdDisponivelCompra = pro_qtdDisponivelCompra - ?, pro_qtdEstoque = pro_qtdEstoque - ? WHERE pro_id = ?";
            PreparedStatement stUpdateProduto = conn.prepareStatement(queryUpdateProduto);
            stUpdateProduto.setDouble(1, movimentacao.getQuantidade());
            stUpdateProduto.setDouble(2, movimentacao.getQuantidade());
            stUpdateProduto.setInt(3, movimentacao.getProduto().getId());
            stUpdateProduto.executeUpdate();

            // Atualiza a quantidade disponível para compra e em estoque do item na tabela estoque_item
            String queryUpdateEstoqueItem = "UPDATE estoque_item SET esi_quantidade = esi_quantidade - ? WHERE esi_pro_id = ?";
            PreparedStatement stUpdateEstoqueItem = conn.prepareStatement(queryUpdateEstoqueItem);
            stUpdateEstoqueItem.setDouble(1, movimentacao.getQuantidade());
            stUpdateEstoqueItem.setInt(2, movimentacao.getProduto().getId());
            stUpdateEstoqueItem.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void calcularValorVenda(MovimentacaoEstoque movimentacao) {
        try {
            // Consulta SQL para obter o maior valor de custo do produto
            String queryMaiorValorCusto = "SELECT MAX(mov_precoCusto) FROM movimentacao_estoque WHERE mov_pro_id = ?";
            PreparedStatement stMaiorValorCusto = conn.prepareStatement(queryMaiorValorCusto);
            stMaiorValorCusto.setInt(1, movimentacao.getProduto().getId());
            ResultSet rsMaiorValorCusto = stMaiorValorCusto.executeQuery();

            double maiorValorCusto = 0.0;
            if (rsMaiorValorCusto.next()) {
                maiorValorCusto = rsMaiorValorCusto.getDouble(1);
            }

            // Consulta SQL para obter o grupo de precificação do produto
            String queryGrupoPrecificacao = "SELECT pro_gru_id FROM produto WHERE pro_id = ?";
            PreparedStatement stGrupoPrecificacao = conn.prepareStatement(queryGrupoPrecificacao);
            stGrupoPrecificacao.setInt(1, movimentacao.getProduto().getId());
            ResultSet rsGrupoPrecificacao = stGrupoPrecificacao.executeQuery();

            int idGrupoPrecificacao = 0;
            if (rsGrupoPrecificacao.next()) {
                idGrupoPrecificacao = rsGrupoPrecificacao.getInt("pro_gru_id");
            }

            // Consulta SQL para obter a margem de lucro do grupo de precificação
            String queryMarkup = "SELECT gru_margemLucro FROM grupo_precificacao WHERE gru_id = ?";
            PreparedStatement stMarkup = conn.prepareStatement(queryMarkup);
            stMarkup.setInt(1, idGrupoPrecificacao);
            ResultSet rsMarkup = stMarkup.executeQuery();

            double markup = 1.0; // Valor padrão caso não haja markup cadastrado
            if (rsMarkup.next()) {
                markup = rsMarkup.getDouble("gru_margemLucro");
            }

            // Calcula o valor de venda com base no maior valor de custo e na margem de lucro do grupo de precificação
            double valorVenda = maiorValorCusto * markup;

            // Atualiza o valor de venda do produto
            String queryUpdateValorVenda = "UPDATE produto SET pro_valor = ? WHERE pro_id = ?";
            PreparedStatement stUpdateValorVenda = conn.prepareStatement(queryUpdateValorVenda);
            stUpdateValorVenda.setDouble(1, valorVenda);
            stUpdateValorVenda.setInt(2, movimentacao.getProduto().getId());
            stUpdateValorVenda.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String alterar(EntidadeDominio entidade) {
        MovimentacaoEstoque movimentacao = (MovimentacaoEstoque) entidade;
        System.out.println(movimentacao.getId());
        inicializarConexao();
        try {
            conn = Db.getConnection();

            st = conn.prepareStatement("UPDATE movimentacao_estoque SET mov_quantidade = ?"
                    + "WHERE (mov_id = ?)");

            setaParametrosQuery(st, movimentacao.getQuantidade(), movimentacao.getId());

            int rowsAffected = st.executeUpdate();
            System.out.println("Done! Rows affected: " + rowsAffected);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao alterar";
        } finally {
            finalizarConexao();
        }
    }

    @Override
    public String excluir(EntidadeDominio entidade) {
        MovimentacaoEstoque movimentacao = (MovimentacaoEstoque) entidade;
        System.out.println(movimentacao.getId());
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("DELETE FROM movimentacao_estoque WHERE mov_id = ?");
            setaParametrosQuery(st, movimentacao.getId());

            int linhasAfetadas = st.executeUpdate();
            System.out.println("Movimentação excluida com sucesso! Linhas afetadas: " + linhasAfetadas);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao excluir";
        } finally {
            finalizarConexao();
        }
    }

    private String pesquisarAuxiliar(EntidadeDominio entidade) {
        if (entidade.getPesquisa() != null && entidade.getPesquisa().equals("id")) {
            return "SELECT * FROM movimentacao_estoque WHERE mov_id = ?";
        } else if (entidade.getPesquisa() != null && entidade.getPesquisa().equals("filtros")) {
            MovimentacaoEstoque mov = (MovimentacaoEstoque) entidade;
            StringBuilder sql = new StringBuilder("SELECT * FROM movimentacao_estoque WHERE 1=1");

            if (mov.getProduto() != null && mov.getProduto().getNome() != null) {
                sql.append(" AND mov_pro_id IN (SELECT pro_id FROM produto WHERE pro_nome LIKE ?)");
            }

            if (mov.getData() != null) {
                sql.append(" AND mov_data = ?");
            }

            if (mov.getTipo() != null) {
                sql.append(" AND mov_tipo = ?");
            }

            if (mov.getQuantidade() != null) {
                sql.append(" AND mov_quantidade = ?");
            }

            if (mov.getFornecedor() != null) {
                sql.append(" AND mov_fornecedor LIKE ?");
            }

            if (mov.getPrecoCusto() != null) {
                sql.append(" AND mov_precoCusto = ?");
            }

            return sql.toString();
        } else {
            return "SELECT * FROM movimentacao_estoque";
        }
    }

    private PreparedStatement executarPesquisa(MovimentacaoEstoque movimentacao, String sql) throws SQLException, ParseException {
        PreparedStatement st = Db.getConnection().prepareStatement(sql);

        if (movimentacao.getPesquisa() != null && movimentacao.getPesquisa().equals("id")) {
            setaParametrosQuery(st, movimentacao.getId());
        }else if (movimentacao.getPesquisa() != null && movimentacao.getPesquisa().equals("filtros")) {
            String filtroLivro = movimentacao.getProduto() != null && movimentacao.getProduto().getNome() != null ?
                    "%" + movimentacao.getProduto().getNome() + "%" : null;

            String filtroTipo = movimentacao.getTipo() != null ? movimentacao.getTipo().name() : null;

            String filtroFornecedor = movimentacao.getFornecedor() != null ?
                    "%" + movimentacao.getFornecedor() + "%" : null;

            setaParametrosQuery(st, filtroLivro, movimentacao.getData(), filtroTipo, movimentacao.getQuantidade(),
                    filtroFornecedor, movimentacao.getPrecoCusto());
        }
        return st;
    }

    public List<MovimentacaoEstoque> consultar(EntidadeDominio entidade) {
        MovimentacaoEstoque movimentacao = (MovimentacaoEstoque) entidade;
        List<MovimentacaoEstoque> movimentacoes = new ArrayList<>();
        inicializarConexao();
        EstoqueItemDAO itemDAO = new EstoqueItemDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();
        try {
            String sql = pesquisarAuxiliar(movimentacao);
            System.out.println("SQL da consulta: " + sql); // Adicionando log para depuração
            st = executarPesquisa(movimentacao, sql);

            Long inicioExecucao = System.currentTimeMillis();
            ResultSet rs = st.executeQuery();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Tempo de execução da consulta: "
                    + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");

            while (rs.next()) {
                MovimentacaoEstoque movimentacaoAux = new MovimentacaoEstoque(
                        rs.getInt("mov_id"),
                        Conversao.parseStringToDate(rs.getString("mov_dtCadastro"), "yyyy-MM-dd"),
                        Conversao.parseStringToDate(rs.getString("mov_data"), "yyyy-MM-dd"),
                        rs.getDouble("mov_quantidade"),
                        rs.getDouble("mov_precoCusto"),
                        rs.getString("mov_fornecedor"),
                        TipoMovimentacao.valueOf(rs.getString("mov_tipo")),
                        rs.getBoolean("mov_entrada"),
                        itemDAO.getEstoqueItemById(rs.getInt("mov_esi_id")),
                        produtoDAO.getProdutoById(rs.getInt("mov_pro_id"))
                );
                movimentacoes.add(movimentacaoAux);
            }
            return movimentacoes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            finalizarConexao();
        }
    }

    public MovimentacaoEstoque getMovimentacaoById(Integer movimentacaoId) {
        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
        inicializarConexao();
        EstoqueItemDAO itemDAO = new EstoqueItemDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();

        System.out.println("Buscando categoria id:" + movimentacaoId);
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("SELECT * FROM movimentacao_estoque WHERE mov_id = ? LIMIT 1");
            setaParametrosQuery(st, movimentacaoId);
            Long inicioExecucao = System.currentTimeMillis();
            ResultSet rs = st.executeQuery();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Tempo de execução da consulta: "
                    + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");
            while (rs.next()) {
                movimentacao = new MovimentacaoEstoque(
                        rs.getInt("mov_id"),
                        Conversao.parseStringToDate(rs.getString("mov_dtCadastro"), "yyyy-MM-dd"),
                        Conversao.parseStringToDate(rs.getString("mov_data"), "yyyy-MM-dd"),
                        rs.getDouble ("mov_quantidade"),
                        rs.getDouble ("mov_precoCusto"),
                        rs.getString ("mov_fornecedor"),
                        TipoMovimentacao.valueOf(rs.getString("mov_tipo")),
                        rs.getBoolean ("mov_entrada"),
                        itemDAO.getEstoqueItemById(rs.getInt("mov_esi_id")),
                        produtoDAO.getProdutoById(rs.getInt("mov_pro_id"))
                );
            }
            return movimentacao;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            finalizarConexao();
        }
    }
}

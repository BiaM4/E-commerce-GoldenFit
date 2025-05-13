package br.com.fatec.goldenfit.dao;

import br.com.fatec.goldenfit.dao.db.Db;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.EstoqueItem;
import br.com.fatec.goldenfit.util.Calculadora;
import br.com.fatec.goldenfit.util.Conversao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class EstoqueItemDAO extends AbstractDAO {
    @Override
    public String salvar(EntidadeDominio entidade) {
        EstoqueItem item = (EstoqueItem) entidade;
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("INSERT INTO estoque_item (esi_quantidade, esi_pro_id) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setDouble(1, item.getQuantidade());
            st.setInt(2, item.getProduto().getId());

            Long inicioExecucao = System.currentTimeMillis();
            int linhasAfetadas = st.executeUpdate();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println(
                    "Estoque item cadastrado com sucesso! \n Linhas afetadas: " + linhasAfetadas + "\nTempo de execução: "
                            + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao salvar";
        } finally {
            finalizarConexao();
        }
    }

    @Override
    public String alterar(EntidadeDominio entidade) {
        return null;
    }

    @Override
    public String excluir(EntidadeDominio entidade) {
        return null;
    }

    @Override
    public <T extends EntidadeDominio> List<T> consultar(EntidadeDominio entidade) {
        return null;
    }

    public Double getQuantidadeEstoqueProduto(Integer produtoId) {
        Double quantidadeEstoque = 0d;
        System.out.println("Buscando quantidade em estoque do produto id:" + produtoId);
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("SELECT esi_quantidade FROM estoque_item WHERE esi_pro_id = ? LIMIT 1");
            setaParametrosQuery(st, produtoId);
            Long inicioExecucao = System.currentTimeMillis();
            ResultSet rs = st.executeQuery();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Tempo de execução da consulta: "
                    + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");

            while (rs.next()) {
                quantidadeEstoque = rs.getDouble("esi_quantidade");
            }

            return quantidadeEstoque;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            finalizarConexao();
        }
    }

    public EstoqueItem getEstoqueItemById(Integer estoqueId) {
        EstoqueItem item = new EstoqueItem();
        System.out.println("Buscando item id:" + estoqueId);
        inicializarConexao();
        ProdutoDAO produtoDAO = new ProdutoDAO();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("SELECT * FROM estoque_item WHERE esi_id = ? LIMIT 1");
            setaParametrosQuery(st, estoqueId);
            Long inicioExecucao = System.currentTimeMillis();
            ResultSet rs = st.executeQuery();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Tempo de execução da consulta: "
                    + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");

            while (rs.next()) {
                item = new EstoqueItem(rs.getInt("esi_id"),
                        Conversao.parseStringToDate(rs.getString("esi_dtCadastro"), "yyyy-MM-dd"),
                        produtoDAO.getProdutoById(rs.getInt("esi_pro_id")),
                        rs.getDouble ("esi_quantidade"));
            }

            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            finalizarConexao();
        }
    }
}

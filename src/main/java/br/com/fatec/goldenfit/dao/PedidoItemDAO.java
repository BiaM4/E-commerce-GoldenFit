package br.com.fatec.goldenfit.dao;

import br.com.fatec.goldenfit.dao.db.Db;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Pedido;
import br.com.fatec.goldenfit.model.PedidoItem;
import br.com.fatec.goldenfit.model.Produto;
import br.com.fatec.goldenfit.model.enums.StatusPedidoItem;
import br.com.fatec.goldenfit.util.Calculadora;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PedidoItemDAO extends AbstractDAO {

    @Override
    public String salvar(EntidadeDominio entidade) {
        Date dataAtual = new Date();
        java.sql.Date dataSql = new java.sql.Date(dataAtual.getTime());

        PedidoItem pedidoItem = (PedidoItem) entidade;
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("INSERT INTO pedido_item (pei_dtCadastro, pei_quantidade, pei_valorunitario, pei_pro_id, pei_ped_id, pei_status) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            String status = pedidoItem.getStatus() != null ? pedidoItem.getStatus().name() : null;

            st.setDate(1, dataSql);
            st.setDouble(2, pedidoItem.getQuantidade());
            st.setDouble(3, pedidoItem.getValorUnitario());
            st.setInt(4, pedidoItem.getProduto().getId());
            st.setInt(5, pedidoItem.getPedido().getId());
            st.setString(6, status);

            long inicioExecucao = System.currentTimeMillis();
            int linhasAfetadas = st.executeUpdate();
            long terminoExecucao = System.currentTimeMillis();
            System.out.println(
                    "Item do pedido cadastrado com sucesso! \n Linhas afetadas: " + linhasAfetadas + "\nTempo de execução: "
                            + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao salvar";
        } finally {
            finalizarConexao();
        }
        return null;
    }

    @Override
    public String alterar(EntidadeDominio entidade) {
        PedidoItem pedidoItem = (PedidoItem) entidade;
        System.out.println(pedidoItem.getId());
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("UPDATE pedido_item SET pei_quantidade = ?, pei_valorunitario = ?, pei_pro_id = ?, pei_status = ? WHERE pei_id = ?");
            String status = pedidoItem.getStatus() != null ? pedidoItem.getStatus().name() : null;

            st.setDouble(1, pedidoItem.getQuantidade());
            st.setDouble(2, pedidoItem.getValorUnitario());
            st.setInt(3, pedidoItem.getProduto().getId());
            st.setString(4, status);
            st.setInt(5, pedidoItem.getId());

            int rowsAffected = st.executeUpdate();
            System.out.println("Done! Rows affected: " + rowsAffected);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao alterar item";
        } finally {
            finalizarConexao();
        }
    }

    @Override
    public String excluir(EntidadeDominio entidade) {
        PedidoItem pedidoItem = (PedidoItem) entidade;
        System.out.println("Excluindo item:" + pedidoItem.getId());
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("DELETE from pedido_item WHERE pei_id = ?");
            setaParametrosQuery(st, false, pedidoItem.getId());

            int linhasAfetadas = st.executeUpdate();
            System.out.println("Item excluido com sucesso! Linhas afetadas: " + linhasAfetadas);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao excluir item";
        } finally {
            finalizarConexao();
        }
    }

    @Override
    public List<PedidoItem> consultar(EntidadeDominio entidade) {
        PedidoItem pedidoItem = (PedidoItem) entidade;
        List<PedidoItem> itens = new ArrayList();
        ProdutoDAO produtoDAO = new ProdutoDAO();
        PedidoDAO pedidoDAO = new PedidoDAO();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(pedidoItem);
        inicializarConexao();
        try {
            String sql = pesquisarAuxiliar(pedidoItem);
            st = executarPesquisa(pedidoItem, sql);

            Long inicioExecucao = System.currentTimeMillis();
            ResultSet rs = st.executeQuery();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Tempo de execução da consulta: "
                    + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");

            while (rs.next()) {
                int produtoId = rs.getInt("pei_pro_id");

                Produto produto = produtoDAO.getProdutoById(produtoId);

                StatusPedidoItem status = rs.getString("pei_status") != null ? StatusPedidoItem.valueOf(rs.getString("pei_status")) : null;
                PedidoItem pedidoItemAux = new PedidoItem(
                        rs.getInt("pei_id"),
                        sdf.parse(rs.getString("pei_dtCadastro")),
                        rs.getDouble("pei_quantidade"),
                        rs.getDouble("pei_valorunitario"),
                        produto,
                        null
                );
                pedidoItemAux.setQuantidadeDisponivelTroca(getQuantidadeDisponivelTroca(rs.getInt("pei_id")));
                pedidoItemAux.setStatus(status);
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("pei_ped_id"));

                pedidoItemAux.setPedido(pedido);
                itens.add(pedidoItemAux);
            }

            return itens;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            finalizarConexao();
        }
    }

    private String pesquisarAuxiliar(EntidadeDominio entidade) {
        if (entidade.getPesquisa().equals("id")) {
            return "select * from pedido_item  WHERE pei_id =?";
        }else if(entidade.getPesquisa().equals("pedido")) {
            return "select * from pedido_item  WHERE pei_ped_id = ?";
        }else {
            return "select * from pedido_item";
        }
    }

    private PreparedStatement executarPesquisa(PedidoItem item, String sql) throws SQLException {
        PreparedStatement st = Db.getConnection().prepareStatement(sql);
        if (item.getPesquisa().equals("id")) {
            setaParametrosQuery(st, item.getId());
        } else if (item.getPesquisa().equals("pedido")) {
            setaParametrosQuery(st, item.getPedido().getId());
        }
        return st;
    }

    public List<PedidoItem> getItensByPedido(Integer idPedido) {
        PedidoItem item = new PedidoItem();
        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        item.setPedido(pedido);
        item.setPesquisa("pedido");

        if(idPedido != null) {
            return (List<PedidoItem>)consultar(item);
        }else {
            return new ArrayList<PedidoItem>();
        }
    }

    public PedidoItem getById(Integer idPedidoItem) {
        PedidoItem item = new PedidoItem();
        item.setId(idPedidoItem);
        item.setPesquisa("id");

        if(idPedidoItem != null) {
            return (PedidoItem) consultar(item).get(0);
        }else {
            return item;
        }
    }

    private Double getQuantidadeDisponivelTroca(Integer idPedidoItem) {
        Double quantidadeDisponivel = 0d;
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("SELECT pei_quantidade - SUM(IFNULL(pit_quantidade, 0)) AS quantidadeDisponivelTroca " +
                            "FROM pedido_item LEFT JOIN pedido_item_troca ON pit_pei_id = pei_id where pei_id = ? LIMIT 1;",
                    Statement.RETURN_GENERATED_KEYS);
            setaParametrosQuery(st, idPedidoItem);

            Long inicioExecucao = System.currentTimeMillis();
            ResultSet rs = st.executeQuery();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Tempo de execução da consulta: "
                    + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");

            while (rs.next()) {
                quantidadeDisponivel = rs.getDouble("quantidadeDisponivelTroca");
            }

            return quantidadeDisponivel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            finalizarConexao();
        }
    }
}



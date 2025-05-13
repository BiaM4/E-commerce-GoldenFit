package br.com.fatec.goldenfit.dao;

import br.com.fatec.goldenfit.dao.db.Db;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.PedidoItemTroca;
import br.com.fatec.goldenfit.util.Calculadora;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PedidoItemTrocaDAO extends AbstractDAO {
    @Override
    public String salvar(EntidadeDominio entidade) {
        Date dataAtual = new Date();
        java.sql.Date dataSql = new java.sql.Date(dataAtual.getTime());

        PedidoItemTroca itemTroca = (PedidoItemTroca) entidade;
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement(
                    "INSERT INTO pedido_item_troca (pit_dtCadastro, pit_quantidade, pit_notificacao, pit_pei_id) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setDate(1, dataSql);
            st.setDouble(2, itemTroca.getQuantidade());
            st.setBoolean(3, false);
            st.setInt(4, itemTroca.getItem().getId());

            long inicioExecucao = System.currentTimeMillis();
            int linhasAfetadas = st.executeUpdate();
            long terminoExecucao = System.currentTimeMillis();
            System.out.println("Item de troca cadastrado com sucesso! \n Linhas afetadas: " + linhasAfetadas
                    + "\nTempo de execução: " + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao)
                    + " segundos");
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
        PedidoItemTroca itemTroca = (PedidoItemTroca) entidade;
        System.out.println(itemTroca.getId());
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement(
                    "UPDATE pedido_item_troca SET pit_quantidade = ?, pit_notificacao = ? WHERE pit_id = ?");

            st.setDouble(1, itemTroca.getQuantidade());
            st.setBoolean(2, itemTroca.getNotificacao());
            st.setInt(3, itemTroca.getId());

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
        return null;
    }

    public List<PedidoItemTroca> consultar(EntidadeDominio entidade) {
        PedidoItemTroca itemTroca = (PedidoItemTroca) entidade;
        List<PedidoItemTroca> itens = new ArrayList();
        PedidoItemDAO pedidoItemDao = new PedidoItemDAO();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(itemTroca);
        inicializarConexao();
        try {
            String sql = pesquisarAuxiliar(itemTroca);
            System.out.println("Consulta SQL: " + sql);
            st = executarPesquisa(itemTroca, sql);

            Long inicioExecucao = System.currentTimeMillis();
            ResultSet rs = st.executeQuery();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Tempo de execução da consulta: " +
                    Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");

            while (rs.next()) {
                PedidoItemTroca itemTrocaAux = new PedidoItemTroca(
                        rs.getInt("pit_id"),
                        sdf.parse(rs.getString("pit_dtCadastro")),
                        pedidoItemDao.getById(rs.getInt("pit_pei_id")),
                        rs.getDouble("pit_quantidade"),
                        rs.getBoolean("pit_notificacao")
                );
                itens.add(itemTrocaAux);
            }

            return itens;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finalizarConexao();
        }
        return null;
    }

    private String pesquisarAuxiliar(EntidadeDominio entidade) {
        if (entidade.getPesquisa().equals("id")) {
            return "select * from pedido_item_troca  WHERE pit_id = ?";
        } else if (entidade.getPesquisa().equals("item")) {
            return "select * from pedido_item_troca  WHERE pit_pei_id = ?";
        } else if(entidade.getPesquisa().equals("notificacaoPendente")){
            return "select * from pedido_item_troca WHERE pit_notificacao = 0 and "
                    + "exists(select 1 from pedido_item join pedido on ped_id = pei_ped_id where ped_cli_id = ? and (pei_status like\r\n"
                    + " 'TROCA_AUTORIZADA' or ped_status like 'TROCA_AUTORIZADA') and pei_id = pit_pei_id);";
        }else{
            return "select * from pedido_item_troca";
        }
    }

    private PreparedStatement executarPesquisa(PedidoItemTroca item, String sql) throws SQLException {
        PreparedStatement st = Db.getConnection().prepareStatement(sql);
        if (item.getPesquisa().equals("id")) {
            setaParametrosQuery(st, item.getId());
        } else if (item.getPesquisa().equals("item")) {
            setaParametrosQuery(st, item.getItem().getId());
        }else if ("notificacaoPendente".equals(item.getPesquisa())) {
            if (item.getItem() != null && item.getItem().getPedido() != null && item.getItem().getPedido().getCliente() != null) {
                setaParametrosQuery(st, item.getItem().getPedido().getCliente().getId());
            }
        }
        return st;
    }
}

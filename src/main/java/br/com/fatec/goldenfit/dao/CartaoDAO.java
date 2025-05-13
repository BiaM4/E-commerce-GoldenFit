package br.com.fatec.goldenfit.dao;
import java.sql.Connection;

import br.com.fatec.goldenfit.dao.db.Db;
import br.com.fatec.goldenfit.model.Cartao;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.enums.Bandeira;
import br.com.fatec.goldenfit.util.Calculadora;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CartaoDAO extends AbstractDAO {

    @Override
    public String salvar(EntidadeDominio entidade) {
        java.util.Date dataAtual = new java.util.Date();
        java.sql.Date dataSql = new java.sql.Date(dataAtual.getTime());

        Cartao cartao = (Cartao) entidade;
        inicializarConexao();
        try {
            conn = Db.getConnection();

                PreparedStatement stConsulta = conn.prepareStatement("SELECT car_id FROM cartao WHERE car_cli_id = ? AND car_preferencial = 1");
                stConsulta.setInt(1, cartao.getCliente().getId());
                ResultSet rs = stConsulta.executeQuery();

                if (rs.next()) {
                    System.out.println("sendo executado");
                    int cartaoId = rs.getInt("car_id");
                    PreparedStatement stUpdate= conn.prepareStatement("UPDATE cartao SET car_preferencial = 0 WHERE car_id = ?");

                    stUpdate.setInt(1, cartaoId);
                    stUpdate.executeUpdate();
                }

            PreparedStatement st = conn.prepareStatement("INSERT INTO cartao (car_dtCadastro, car_numero, car_nome, " +
                    "car_cvv, car_preferencial, car_ban_id, car_cli_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);


            st.setDate(1, dataSql);
            st.setString(2, cartao.getNumero());
            st.setString(3, cartao.getNome());
            st.setString(4, cartao.getCvv());
            st.setBoolean(5, cartao.getPreferencial());
            st.setInt(6, cartao.getBandeira().getCodigo());
            st.setInt(7, cartao.getCliente().getId());

            Long inicioExecucao = System.currentTimeMillis();
            int linhasAfetadas = st.executeUpdate();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println(
                    "Cartão cadastrado com sucesso! \n Linhas afetadas: " + linhasAfetadas + "\nTempo de execução: "
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
        Cartao cartao = (Cartao) entidade;
        System.out.println(cartao.getId());
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("UPDATE cartao SET car_numero = ?, car_nome = ?, car_cvv = ?,"
                                        + "car_preferencial = ?, car_ban_id = ? WHERE (car_id = ?)");

            setaParametrosQuery(st, cartao.getNumero(), cartao.getNome(), cartao.getCvv(),
                    cartao.getPreferencial(), cartao.getBandeira().getCodigo(), cartao.getId());

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
        Cartao cartao = (Cartao) entidade;
        System.out.println(cartao.getId());
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("DELETE FROM cartao WHERE car_id = ?");
            setaParametrosQuery(st, cartao.getId());

            int linhasAfetadas = st.executeUpdate();
            System.out.println("Cartão excluido com sucesso! Linhas afetadas: " + linhasAfetadas);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao excluir";
        } finally {
            finalizarConexao();
        }
    }

    private String pesquisarAuxiliar(EntidadeDominio entidade) {
        if (entidade.getPesquisa().equals("id")) {
            return "select * from cartao where car_id =?";
        }else if(entidade.getPesquisa().equals("cliente")){
            return "select * from cartao where car_cli_id = ?";
        }else if(entidade.getPesquisa().equals("preferencial")){
            return "select * from cartao where car_cli_id = ? and car_preferencial = 1";
        }{
            return "select * from cartao";
        }
    }

    private PreparedStatement executarPesquisa(Cartao cartao, String sql) throws SQLException {
        PreparedStatement st = Db.getConnection().prepareStatement(sql);
        if (cartao.getPesquisa().equals("id")) {
            setaParametrosQuery(st, cartao.getId());
        }else if(cartao.getPesquisa().equals("cliente") || cartao.getPesquisa().equals("preferencial")) {
            setaParametrosQuery(st, cartao.getCliente().getId());
        }
        return st;
    }

    public List<Cartao> consultar(EntidadeDominio entity) {
        Cartao cartao = (Cartao) entity;
        List<Cartao> cartoes = new ArrayList<>();
        System.out.println(cartao);
        inicializarConexao();
        try {
            String sql = pesquisarAuxiliar(cartao);
            st = executarPesquisa(cartao, sql);

            Long inicioExecucao = System.currentTimeMillis();
            ResultSet rs = st.executeQuery();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Tempo de execução da consulta: "
                    + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");

            while (rs.next()) {
                Cartao cartaoAux = new Cartao(rs.getInt("car_id"), rs.getString("car_numero"),
                        Bandeira.getByCodigo(rs.getInt("car_ban_id")), rs.getString("car_nome"),
                        rs.getString("car_cvv"), rs.getBoolean("car_preferencial"), rs.getInt("car_cli_id"));
                cartoes.add(cartaoAux);
            }

            return cartoes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            finalizarConexao();
        }
    }

    public Cartao getCartaoById(Integer idCartao) {
        Cartao cartao = new Cartao();
        cartao.setId(idCartao);
        cartao.setPesquisa("id");

        if(idCartao != null && idCartao > 0) {
            return (Cartao) consultar(cartao).get(0);
        }else {
            return cartao;
        }
    }

    public boolean possuiCartaoPreferencial(EntidadeDominio entidade) {
        Cartao cartao = (Cartao) entidade;
        cartao.setPesquisa("preferencial");

        List<Cartao> resultados = consultar(cartao);
        return resultados != null && !resultados.isEmpty();
    }
}

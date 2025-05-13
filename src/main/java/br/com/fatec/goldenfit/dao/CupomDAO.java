package br.com.fatec.goldenfit.dao;

import br.com.fatec.goldenfit.dao.db.Db;
import br.com.fatec.goldenfit.model.Cupom;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.enums.TipoCupom;
import br.com.fatec.goldenfit.util.Calculadora;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CupomDAO extends AbstractDAO {
    @Override
    public String salvar(EntidadeDominio entidade) {
        Date dataAtual = new Date();
        java.sql.Date dataSql = new java.sql.Date(dataAtual.getTime());

        Cupom cupom = (Cupom) entidade;
        inicializarConexao();
        try {
            conn = Db.getConnection();

            StringBuilder sql = new StringBuilder("INSERT INTO cupom (cup_dtCadastro, cup_nome, cup_codigo, cup_valor, cup_validade, cup_tipo, cup_cli_id, cup_ped_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            st = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            int parameterIndex = 1;

            st.setDate(parameterIndex++, dataSql);

            String cupNome = cupom.getNome();
            int maxLength = 50;
            if (cupNome.length() > maxLength) {
                cupNome = cupNome.substring(0, maxLength);
            }
            st.setString(parameterIndex++, cupNome);

            st.setString(parameterIndex++, cupom.getCodigo());
            st.setDouble(parameterIndex++, cupom.getValor());
            st.setDate(parameterIndex++, new java.sql.Date(cupom.getValidade().getTime()));
            st.setString(parameterIndex++, cupom.getTipo().name());

            if (cupom.getIdCliente() != null && cupom.getIdCliente() > 0) {
                st.setInt(parameterIndex++, cupom.getIdCliente());
            } else {
                st.setNull(parameterIndex++, Types.INTEGER);
            }

            if (cupom.getIdPedido() != null && cupom.getIdPedido() > 0) {
                st.setInt(parameterIndex++, cupom.getIdPedido());
            } else {
                st.setNull(parameterIndex++, Types.INTEGER);
            }

            Long inicioExecucao = System.currentTimeMillis();
            int linhasAfetadas = st.executeUpdate();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println(
                    "Cupom cadastrado com sucesso! \n Linhas afetadas: " + linhasAfetadas + "\nTempo de execução: "
                            + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao salvar";
        }
    }

    @Override
    public String alterar(EntidadeDominio entidade) {
        Cupom cupom = (Cupom) entidade;
        System.out.println(cupom.getId());
        System.out.println(cupom.isStatus());
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("UPDATE cupom SET cup_nome = ? , cup_codigo = ? , cup_valor = ? ,"
                    + "cup_validade = ?, cup_tipo = ?, cup_status = ?  WHERE  (cup_id = ?)");

            setaParametrosQuery(st, cupom.getNome(), cupom.getCodigo(), cupom.getValor(),
                    cupom.getValidade(), cupom.getTipo().name(), cupom.isStatus(), cupom.getId());

            int rowsAffected = st.executeUpdate();
            System.out.println("Done! Rows affected: " + rowsAffected);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao alterar";
        }
    }

    @Override
    public String excluir(EntidadeDominio entidade) {
        Cupom cupom = (Cupom) entidade;
        System.out.println(cupom.getId());
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("DELETE FROM cupom " + "WHERE " + "cup_id = ?");
            setaParametrosQuery(st, cupom.getId());

            int linhasAfetadas = st.executeUpdate();
            System.out.println("Cupom excluido com sucesso! Linhas afetadas: " + linhasAfetadas);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao excluir";
        }
    }

    private String pesquisarAuxiliar(EntidadeDominio entidade) {
        if (entidade.getPesquisa().equals("id")) {
            return "select * from cupom where cup_id =?";
        } else if (entidade.getPesquisa().equals("validadeAtiva")) {
            return "select * from cupom where cup_validade >= ? and (cup_tipo like 'PROMOCIONAL' or cup_cli_id = ?)";
        } else {
            return "select * from cupom";
        }
    }

    private PreparedStatement executarPesquisa(Cupom cupom, String sql) throws SQLException {
        PreparedStatement st = Db.getConnection().prepareStatement(sql);
        if (cupom.getPesquisa().equals("id")) {
            setaParametrosQuery(st, cupom.getId());
        } else if (cupom.getPesquisa().equals("validadeAtiva")) {
            setaParametrosQuery(st, new Date(), cupom.getIdCliente());
        }
        return st;
    }

    public List<Cupom> consultar(EntidadeDominio entity) {
        Cupom cupom = (Cupom) entity;
        List<Cupom> cupons = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println(cupom);
        inicializarConexao();
        try {//
            String sql = pesquisarAuxiliar(cupom);
            st = executarPesquisa(cupom, sql);

            Long inicioExecucao = System.currentTimeMillis();
            ResultSet rs = st.executeQuery();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Tempo de execução da consulta: "
                    + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");
            while (rs.next()) {
                String tipoString = rs.getString("cup_tipo").trim();

                TipoCupom tipo = Arrays.stream(TipoCupom.values())
                        .filter(enumValue -> enumValue.getDescricao().equalsIgnoreCase(tipoString))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Tipo de cupom não reconhecido: " + tipoString));

                Cupom cupomAux = new Cupom(rs.getInt("cup_id"), rs.getString("cup_codigo"), rs.getString("cup_nome"),
                        rs.getDouble("cup_valor"), sdf.parse(rs.getString("cup_validade")), tipo,
                        rs.getInt("cup_cli_id"), rs.getInt("cup_ped_id"), rs.getBoolean("cup_status"));

                if (cupom.getIdCliente() != null) {
                    cupomAux.setAplicado(isCupomUtilizado(rs.getInt("cup_id"), cupom.getIdCliente()));
                }
                cupons.add(cupomAux);
            }

            return cupons;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private boolean isCupomUtilizado(Integer idCupom, Integer idCliente) {
        boolean isCupomAplicado = false;
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("select 1 as aplicado from forma_pagamento where for_cup_id = ? and"
                    + " exists (select 1 from pedido where ped_id = for_ped_id and ped_cli_id = ?)");
            setaParametrosQuery(st, idCupom, idCliente);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                isCupomAplicado = rs.getBoolean("aplicado");
            }
            return isCupomAplicado;
        } catch (Exception e) {
            e.printStackTrace();
            return isCupomAplicado;
        }
    }

    public Cupom getCupomById(Integer idCupom) {
        Cupom cupom = new Cupom();
        cupom.setId(idCupom);
        cupom.setPesquisa("id");

        if (idCupom != null && idCupom > 0) {
            return (Cupom) consultar(cupom).get(0);
        } else {
            return cupom;
        }
    }
}

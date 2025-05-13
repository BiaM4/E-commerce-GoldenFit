package br.com.fatec.goldenfit.dao;

import br.com.fatec.goldenfit.dao.db.Db;
import br.com.fatec.goldenfit.model.Cidade;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.enums.Estado;
import br.com.fatec.goldenfit.util.Calculadora;

import java.sql.ResultSet;
import java.util.List;

public class CidadeDAO extends AbstractDAO {
    @Override
    public String salvar(EntidadeDominio entidade) {
        return null;
    }

    @Override
    public String alterar(EntidadeDominio entidade) {
        return null;
    }

    @Override
    public String excluir(EntidadeDominio entidade) {
        return null;
    }

    public List<Cidade> consultar(EntidadeDominio entidade) {
        return null;
    }

    public Cidade getCidadeById(Integer cidadeId) {
        Cidade cidade = new Cidade();
        System.out.println("Buscando cidade id:" + cidadeId);
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("SELECT * FROM cidade WHERE cid_id = ? LIMIT 1");
            setaParametrosQuery(st, cidadeId);
            Long inicioExecucao = System.currentTimeMillis();
            ResultSet rs = st.executeQuery();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Tempo de execução da consulta: "
                    + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");

            while (rs.next()) {
                cidade = new Cidade(rs.getInt("cid_id"), rs.getString("cid_nome"),
                        Estado.getByCodigo(rs.getInt("cid_est_id")));
            }

            return cidade;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            finalizarConexao();
        }
    }
}

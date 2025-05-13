package br.com.fatec.goldenfit.dao;

import br.com.fatec.goldenfit.dao.db.Db;
import br.com.fatec.goldenfit.model.Endereco;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.enums.TipoEndereco;
import br.com.fatec.goldenfit.model.enums.TipoLogradouro;
import br.com.fatec.goldenfit.model.enums.TipoResidencia;
import br.com.fatec.goldenfit.util.Calculadora;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnderecoDAO extends AbstractDAO {
    @Override
    public String salvar(EntidadeDominio entidade) {
        Date dataAtual = new Date();
        java.sql.Date dataSql = new java.sql.Date(dataAtual.getTime());

        Endereco endereco = (Endereco) entidade;
        inicializarConexao();
        try {
            conn = Db.getConnection();
            StringBuilder sql = new StringBuilder("INSERT INTO endereco (end_dtCadastro, end_descricao,"
                    + " end_tipologradouro, end_logradouro, end_numero");

            sql.append(", end_complemento, end_observacao");

            sql.append(", end_tiporesidencia, end_cep, end_bairro, end_tipo,");
            sql.append(" end_cli_id, end_cid_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "(SELECT cid_id FROM cidade WHERE cid_nome like ? AND cid_est_id = ? LIMIT 1 ))");

            st = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            int parameterIndex = 1;

            st.setDate(parameterIndex++, dataSql);
            st.setString(parameterIndex++, endereco.getDescricao());
            st.setString(parameterIndex++, endereco.getTipoLogradouro().name());
            st.setString(parameterIndex++, endereco.getLogradouro());
            st.setString(parameterIndex++, endereco.getNumero());

            if (endereco.getComplemento() != null) {
                st.setString(parameterIndex++, endereco.getComplemento());
            } else {
                st.setNull(parameterIndex++, Types.VARCHAR);
            }

            st.setString(parameterIndex++, endereco.getObservacao());

            st.setString(parameterIndex++, endereco.getTipoResidencia().name());
            st.setString(parameterIndex++, endereco.getCep());
            st.setString(parameterIndex++, endereco.getBairro());
            st.setString(parameterIndex++, endereco.getTipoEndereco().name());
            st.setInt(parameterIndex++, endereco.getCliente().getId());
            st.setString(parameterIndex++, endereco.getCidade().getNome());
            st.setInt(parameterIndex++, endereco.getCidade().getEstado().getCodigo());

            Long inicioExecucao = System.currentTimeMillis();
            int linhasAfetadas = st.executeUpdate();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Endereço cadastrado com sucesso! \n Linhas afetadas: " + linhasAfetadas
                    + "\nTempo de execução: " + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao)
                    + " segundos");
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
        Endereco endereco = (Endereco) entidade;
        System.out.println(endereco.getId());
        inicializarConexao();
        try {
            conn = Db.getConnection();
            StringBuilder sql = new StringBuilder("UPDATE endereco SET end_descricao = ?, "
                    + "end_tipologradouro = ?, end_logradouro = ?, end_numero = ?,");

            if(endereco.getComplemento() != null && endereco.getComplemento() != "") {
                sql.append(" end_complemento = ?,");
            }
            sql.append(" end_tiporesidencia = ?, end_cep = ?, end_bairro = ?, end_tipo = ?,");

            if(endereco.getObservacao() != null && endereco.getObservacao() != "") {
                sql.append(" end_observacao = ?");
            }

            sql.append(", end_cid_id = (SELECT cid_id FROM cidade WHERE cid_nome like ? AND cid_est_id = ? LIMIT 1 ) WHERE end_id = ?");

            st = conn.prepareStatement(sql.toString());
            System.out.println(sql.toString());

            st.setString(1, endereco.getDescricao());
            st.setString(2, endereco.getTipoLogradouro().name());
            st.setString(3, endereco.getLogradouro());
            st.setString(4, endereco.getNumero());
            st.setString(5, endereco.getComplemento());
            st.setString(6, endereco.getTipoResidencia().name());
            st.setString(7, endereco.getCep());
            st.setString(8, endereco.getBairro());
            st.setString(9, endereco.getTipoEndereco().name());
            st.setString(10, endereco.getObservacao());
            st.setString(11, endereco.getCidade().getNome());
            st.setInt(12, endereco.getCidade().getEstado().getCodigo());
            st.setInt(13, endereco.getId());

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
        Endereco endereco = (Endereco) entidade;
        System.out.println(endereco.getId());
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("DELETE FROM endereco WHERE end_id = ?");
            setaParametrosQuery(st, endereco.getId());

            int linhasAfetadas = st.executeUpdate();
            System.out.println("Endereço excluído com sucesso! Linhas afetadas: " + linhasAfetadas);
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
            return "select * from endereco where end_id =?";
        }else if(entidade.getPesquisa().equals("cliente")){
            return "select * from endereco where end_cli_id = ?";
        }else {
            return "select * from endereco";
        }
    }

    private PreparedStatement executarPesquisa(Endereco endereco, String sql) throws SQLException {
        PreparedStatement st = Db.getConnection().prepareStatement(sql);
        if (endereco.getPesquisa().equals("id")) {
            setaParametrosQuery(st, endereco.getId());
        }else if(endereco.getPesquisa().equals("cliente")) {
            setaParametrosQuery(st, endereco.getCliente().getId());
        }
        return st;
    }

    public List<Endereco> consultar(EntidadeDominio entity) {
        Endereco endereco = (Endereco) entity;
        List<Endereco> enderecos = new ArrayList<>();
        System.out.println(endereco);
        inicializarConexao();
        CidadeDAO cidadeDAO = new CidadeDAO();
        try {
            String sql = pesquisarAuxiliar(endereco);
            st = executarPesquisa(endereco, sql);

            Long inicioExecucao = System.currentTimeMillis();
            ResultSet rs = st.executeQuery();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Tempo de execução da consulta: "
                    + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");

            while (rs.next()) {
                Endereco enderecoAux = new Endereco(rs.getInt("end_id"), rs.getString("end_descricao"),
                        TipoLogradouro.valueOf(rs.getString("end_tipologradouro")),rs.getString("end_logradouro"),
                        rs.getString("end_numero"), rs.getString("end_complemento"), TipoResidencia.valueOf(rs.getString("end_tiporesidencia")),
                        rs.getNString("end_bairro"), rs.getString("end_cep"), cidadeDAO.getCidadeById(rs.getInt("end_cid_id")),
                        rs.getString("end_observacao"), TipoEndereco.valueOf(rs.getString("end_tipo")), rs.getInt("end_cli_id"));
                enderecos.add(enderecoAux);
            }

            return enderecos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            finalizarConexao();
        }
    }

    public Endereco getEnderecoResidencialByCliente(Integer clienteId) {
        Endereco endereco = new Endereco();
        CidadeDAO cidadeDAO = new CidadeDAO();
        inicializarConexao();

        System.out.println("Buscando endereco residencial do cliente:" + clienteId);
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("SELECT * FROM endereco WHERE end_cli_id = ? and end_tipo like 'Residencial' LIMIT 1");
            setaParametrosQuery(st, clienteId);
            Long inicioExecucao = System.currentTimeMillis();
            ResultSet rs = st.executeQuery();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Tempo de execução da consulta: "
                    + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");

            while (rs.next()) {
                endereco = new Endereco(rs.getInt("end_id"), rs.getString("end_descricao"),
                        TipoLogradouro.valueOf(rs.getString("end_tipologradouro")), rs.getString("end_logradouro"),
                        rs.getString("end_numero"), rs.getString("end_complemento"),
                        TipoResidencia.valueOf(rs.getString("end_tipoResidencia")), rs.getString("end_bairro"),
                        rs.getString("end_cep"), cidadeDAO.getCidadeById(rs.getInt("end_cid_id")),
                        rs.getString("end_observacao"), TipoEndereco.valueOf(rs.getString("end_tipo")), rs.getInt("end_cli_id"));
            }
            return endereco;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            finalizarConexao();
        }
    }

    public Endereco getEnderecoById(Integer idEndereco) {
        Endereco endereco = new Endereco();
        endereco.setId(idEndereco);
        endereco.setPesquisa("id");

        return (Endereco) consultar(endereco).get(0);
    }
}

package br.com.fatec.goldenfit.dao;

import br.com.fatec.goldenfit.dao.db.Db;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Telefone;
import br.com.fatec.goldenfit.model.enums.TipoTelefone;
import br.com.fatec.goldenfit.util.Calculadora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class TelefoneDAO extends AbstractDAO {
    @Override
    public String salvar(EntidadeDominio entidade) {
        java.util.Date dataAtual = new java.util.Date();
        java.sql.Date dataSql = new java.sql.Date(dataAtual.getTime());

        Telefone telefone = (Telefone) entidade;
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("INSERT INTO telefone "
                            + "(tel_dtCadastro, tel_ddd, tel_numero, tel_cli_id, tel_tip_id) VALUES (?, ?, ?, (SELECT MAX(cli_id) FROM cliente), ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setDate(1, dataSql);
            st.setString(2, telefone.getDdd());
            st.setString(3, telefone.getNumero());
            st.setInt(4, telefone.getTipoTelefone().getCodigo());

            Long inicioExecucao = System.currentTimeMillis();
            int linhasAfetadas = st.executeUpdate();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println(
                    "Telefone cadastrado com sucesso! \n Linhas afetadas: " + linhasAfetadas + "\nTempo de execução: "
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
        Telefone telefone = (Telefone) entidade;
        System.out.println(telefone.getId());
        inicializarConexao();
        try {
            conn = Db.getConnection();

            st = conn.prepareStatement("UPDATE telefone SET tel_ddd = ?, "
                    + "tel_numero = ? WHERE tel_id = ?");

            st.setString(1, telefone.getDdd());
            st.setString(2, telefone.getNumero());
            st.setInt(3, telefone.getId());

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
        return null;
    }

    @Override
    public <T extends EntidadeDominio> List<T> consultar(EntidadeDominio entidade) {
        return null;
    }

    public Telefone getTelefoneByCliente(Integer clienteId, TipoTelefone tipo) {
        Telefone telefone = null;
        try (Connection conn = Db.getConnection();
             PreparedStatement st = conn.prepareStatement("SELECT * FROM telefone WHERE tel_cli_id = ? and tel_tip_id = ? LIMIT 1")) {
            st.setInt(1, clienteId);
            st.setInt(2, tipo.getCodigo());
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    telefone = new Telefone(rs.getInt("tel_id"), rs.getString("tel_ddd"), rs.getString("tel_numero"),
                            TipoTelefone.getByCodigo(rs.getInt("tel_tip_id")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finalizarConexao();
        }
        return telefone;
    }

}

package br.com.fatec.goldenfit.dao;

import br.com.fatec.goldenfit.dao.db.Db;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.Usuario;
import br.com.fatec.goldenfit.util.Calculadora;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsuarioDAO extends AbstractDAO {
    @Override
    public String salvar(EntidadeDominio entidade) {
        java.util.Date dataAtual = new java.util.Date();
        java.sql.Date dataSql = new java.sql.Date(dataAtual.getTime());

        Usuario usuario = (Usuario) entidade;
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("INSERT INTO usuario (usu_dtCadastro, usu_email, usu_senha, usu_status) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setDate(1, dataSql);
            st.setString(2, usuario.getEmail());
            st.setString(3, usuario.getSenha());
            st.setBoolean(4, true);

            long inicioExecucao = System.currentTimeMillis();
            int linhasAfetadas = st.executeUpdate();
            long terminoExecucao = System.currentTimeMillis();
            System.out.println(
                    "Usuario cadastrado com sucesso! \n Linhas afetadas: " + linhasAfetadas + "\nTempo de execução: "
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
        Usuario usuario = (Usuario) entidade;
        System.out.println(usuario.getId());
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("UPDATE usuario SET usu_senha = ?, usu_email = ? WHERE usu_id = ?");

            setaParametrosQuery(st, usuario.getSenha(), usuario.getEmail(), usuario.getId());

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
        Usuario usuario = (Usuario) entidade;
        System.out.println("Alterando status do usuário: " + usuario.getEmail() + " (ID: " + usuario.getId() + ")");
        inicializarConexao();
        try {
            conn = Db.getConnection();

            // Consulta para obter o status atual do usuário
            PreparedStatement stA = conn.prepareStatement("SELECT usu_status FROM usuario WHERE usu_id = ?");
            setaParametrosQuery(stA, usuario.getId());
            ResultSet rs = stA.executeQuery();
            boolean statusAtual = false;
            if (rs.next()) {
                statusAtual = rs.getBoolean("usu_status");
            }

            // Altera o status do usuário
            boolean novoStatus = !statusAtual; // Inverte o status atual
            PreparedStatement st = conn.prepareStatement("UPDATE usuario SET usu_status = ? WHERE usu_id = ?");
            setaParametrosQuery(st, novoStatus, usuario.getId());
            int linhasAfetadas = st.executeUpdate();
            System.out.println("Status do usuário alterado com sucesso! Novo status: " + novoStatus + ", Linhas afetadas: " + linhasAfetadas);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao alterar o status do usuário";
        } finally {
            finalizarConexao();
        }
    }


    public List<Usuario> consultar(EntidadeDominio entidade) {
        Usuario usuario = (Usuario) entidade;
        List<Usuario> usuarios = new ArrayList<>();

        inicializarConexao();

        try {
            String sql = pesquisarAuxiliar(usuario);
            PreparedStatement st = executarPesquisa(usuario, sql);

            Long inicioExecucao = System.currentTimeMillis();
            ResultSet rs = st.executeQuery();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Tempo de execução da consulta: "
                    + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");

            while (rs.next()) {
                Usuario usuAux = new Usuario(rs.getInt("usu_id"), rs.getString("usu_email"),
                        rs.getString("usu_senha"), rs.getBoolean("usu_admin"));
                usuAux.setStatus(rs.getBoolean("usu_status"));
                usuarios.add(usuAux);
            }

            // Fechar o ResultSet, PreparedStatement e Connection
            rs.close();
            st.close();

            return usuarios;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            finalizarConexao();
        }
    }


    private String pesquisarAuxiliar(EntidadeDominio entidade) {
        if (entidade.getPesquisa().equals("id")) {
            return "select * from usuario  WHERE usu_id =?";
        } else if(entidade.getPesquisa().equals("email")){
            return "SELECT * FROM usuario WHERE usu_email = ?";
        } else if(entidade.getPesquisa().equals("email,senha")) {
            return "select * from usuario  WHERE usu_email = ? and usu_senha = ?";
        } else if(entidade.getPesquisa().equals("senha,usuario")){
            return "select * from usuario  WHERE usu_senha = ? and usu_id = ?";
        }else {
            return "select * from usuario";
        }
    }

    private PreparedStatement executarPesquisa(Usuario usuario, String sql) throws SQLException {
        Connection connection = Db.getConnection();
        PreparedStatement st = connection.prepareStatement(sql);

        if (usuario.getPesquisa().equals("id")) {
            setaParametrosQuery(st, usuario.getId());
        } else if (usuario.getPesquisa().equals("email")){
            setaParametrosQuery(st, usuario.getEmail());
        } else if (usuario.getPesquisa().equals("email,senha")) {
            setaParametrosQuery(st, usuario.getEmail(), usuario.getSenha());
        } else if (usuario.getPesquisa().equals("senha,usuario")) {
            setaParametrosQuery(st, usuario.getSenha(), usuario.getId());
        }

        return st;
    }

    public Usuario getUsuarioById(Integer idUsuario) {
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        usuario.setPesquisa("id");

        if (idUsuario != null && idUsuario > 0) {
            return (Usuario) consultar(usuario).get(0);
        } else {
            return usuario;
        }
    }

    public Usuario getUsuarioByEmail(String email) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPesquisa("email");

        List<Usuario> usuarios = consultar(usuario);

        // Verifica se a lista de usuários retornada não está vazia
        if (!usuarios.isEmpty()) {
            return usuarios.get(0); // Retorna o primeiro usuário encontrado
        } else {
            return null; // Retorna null se nenhum usuário for encontrado
        }
    }
}

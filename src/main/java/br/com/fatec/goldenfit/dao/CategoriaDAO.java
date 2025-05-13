package br.com.fatec.goldenfit.dao;

import br.com.fatec.goldenfit.dao.db.Db;
import br.com.fatec.goldenfit.model.Categoria;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.util.Calculadora;
import br.com.fatec.goldenfit.util.Conversao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoriaDAO extends AbstractDAO{
    @Override
    public String salvar(EntidadeDominio entidade) {
        Date dataAtual = new Date();
        java.sql.Date dataSql = new java.sql.Date(dataAtual.getTime());

        Categoria categoria = (Categoria) entidade;
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("INSERT INTO categoria (cat_dtCadastro, cat_nome) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setDate(1, dataSql);
            st.setString(2, categoria.getNome());

            Long inicioExecucao = System.currentTimeMillis();
            int linhasAfetadas = st.executeUpdate();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println(
                    "Categoria cadastrada com sucesso! \n Linhas afetadas: " + linhasAfetadas + "\nTempo de execução: "
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
        Categoria categoria = (Categoria) entidade;
        System.out.println(categoria.getId());
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("UPDATE categoria SET cat_nome = ? "
                    + "WHERE (cat_id = ?)");

            setaParametrosQuery(st, categoria.getNome(), categoria.getId());

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
        Categoria categoria = (Categoria) entidade;
        System.out.println(categoria.getId());
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("DELETE FROM categoria WHERE cat_id = ?");
            setaParametrosQuery(st, categoria.getId());

            int linhasAfetadas = st.executeUpdate();
            System.out.println("Categoria excluida com sucesso! Linhas afetadas: " + linhasAfetadas);
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
            return "select * from categoria where cat_id =?";
        }else{
            return "select * from categoria";
        }
    }

    private PreparedStatement executarPesquisa(Categoria categoria, String sql) throws SQLException {
        PreparedStatement st = Db.getConnection().prepareStatement(sql);
        if (categoria.getPesquisa().equals("id")) {
            setaParametrosQuery(st, categoria.getId());
        }
        return st;
    }


    public List<Categoria> consultar(EntidadeDominio entidade) {
        Categoria categoria= (Categoria) entidade;
        List<Categoria> categorias = new ArrayList<>();
        inicializarConexao();
        try {
            String sql = pesquisarAuxiliar(categoria);
            st = executarPesquisa(categoria, sql);

            Long inicioExecucao = System.currentTimeMillis();
            ResultSet rs = st.executeQuery();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Tempo de execução da consulta: "
                    + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");

            while (rs.next()) {
                Categoria categoriaAux = new
                        Categoria(rs.getInt("cat_id"), rs.getString("cat_nome"),
                        Conversao.parseStringToDate(rs.getString("cat_dtCadastro"), "yyyy-MM-dd"));
                categorias.add(categoriaAux);
            }

            return categorias;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            finalizarConexao();
        }
    }

    public Categoria getCategoriaById(Integer categoriaId) {
        Categoria categoria = new Categoria();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        inicializarConexao();

        System.out.println("Buscando categoria id:" + categoriaId);
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("SELECT * FROM categoria WHERE cat_id = ? LIMIT 1");
            setaParametrosQuery(st, categoriaId);
            Long inicioExecucao = System.currentTimeMillis();
            ResultSet rs = st.executeQuery();
            Long terminoExecucao = System.currentTimeMillis();

            System.out.println("Tempo de execução da consulta: "
                    + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");

            while (rs.next()) {
                categoria = new Categoria(rs.getInt("cat_id"), rs.getString("cat_nome"),
                        sdf.parse(rs.getString("cat_dtCadastro")));
            }

            return categoria;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            finalizarConexao();
        }
    }
}

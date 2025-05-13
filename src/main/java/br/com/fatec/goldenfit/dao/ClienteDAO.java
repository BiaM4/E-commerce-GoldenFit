package br.com.fatec.goldenfit.dao;

import br.com.fatec.goldenfit.dao.db.Db;
import br.com.fatec.goldenfit.model.Cliente;
import br.com.fatec.goldenfit.model.Endereco;
import br.com.fatec.goldenfit.model.EntidadeDominio;
import br.com.fatec.goldenfit.model.enums.TipoTelefone;
import br.com.fatec.goldenfit.util.Calculadora;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClienteDAO extends AbstractDAO {
    @Override
    public String salvar(EntidadeDominio entidade) {
        Date dataAtual = new Date();
        java.sql.Date dataSql = new java.sql.Date(dataAtual.getTime());

        Cliente cliente = (Cliente) entidade;
        inicializarConexao();
        try {
            conn = Db.getConnection();
            Long inicioExecucao = System.currentTimeMillis();
            Long terminoExecucao = null;
            int linhasAfetadas = 0;

            TelefoneDAO telefoneDAO = new TelefoneDAO();
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            if (cliente != null && cliente.getUsuario() != null) {
                st = conn.prepareStatement("SELECT usu_id FROM usuario WHERE usu_email = ?");
                st.setString(1, cliente.getUsuario().getEmail());
                ResultSet rs = st.executeQuery();

                if (!rs.next()) {
                    st = conn.prepareStatement("SELECT cli_id FROM cliente WHERE cli_cpf = ?");
                    st.setString(1, cliente.getCpf());
                    rs = st.executeQuery();

                    if (!rs.next()) {
                        usuarioDAO.salvar(cliente.getUsuario());
                        linhasAfetadas = 1;
                    } else {
                        throw new Exception("Já existe um cliente com este CPF cadastrado.");
                    }
                } else {
                    throw new Exception("Já existe um usuário com este e-mail cadastrado.");
                }
            }

            if (linhasAfetadas > 0) {
                linhasAfetadas = 0;
                st = conn.prepareStatement("INSERT INTO cliente " +
                                "(cli_dtCadastro, cli_nome, cli_sobrenome, cli_genero, " +
                                "cli_dtNascimento, cli_cpf, cli_score, cli_usu_id) "
                                + "VALUES " + "(?, ?, ?, ?, ?, ?, ?, (SELECT MAX(usu_id) from usuario))",
                        Statement.RETURN_GENERATED_KEYS);

                st.setDate(1, dataSql);
                st.setString(2, cliente.getNome());
                st.setString(3, cliente.getSobrenome());
                st.setString(4, cliente.getGenero());
                st.setDate(5, new java.sql.Date(cliente.getDataNascimento().getTime()));
                st.setString(6, cliente.getCpf());
                st.setInt(7, cliente.getScore());
                linhasAfetadas = st.executeUpdate();
            }

            if (cliente.getTelefoneResidencial() != null && linhasAfetadas > 0) {
                telefoneDAO.salvar(cliente.getTelefoneResidencial());
            }

            if (cliente.getTelefoneCelular() != null && linhasAfetadas > 0) {
                telefoneDAO.salvar(cliente.getTelefoneCelular());
            }

            if (cliente.getEnderecoResidencial() != null && linhasAfetadas > 0) {
                Endereco end = cliente.getEnderecoResidencial();

                StringBuilder sql = new StringBuilder("INSERT INTO endereco (end_dtCadastro, end_descricao,"
                        + " end_tipologradouro, end_logradouro, end_numero");

                sql.append(", end_complemento, end_observacao");

                sql.append(", end_tiporesidencia, end_cep, end_bairro, end_tipo,");
                sql.append(" end_cli_id, end_cid_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                        + "(SELECT MAX(cli_id) from cliente), "
                        + "(SELECT cid_id FROM cidade WHERE cid_nome like ? AND cid_est_id = ? LIMIT 1 ))");

                st = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

                int parameterIndex = 1;

                st.setDate(parameterIndex++, dataSql);
                st.setString(parameterIndex++, end.getDescricao());
                st.setString(parameterIndex++, end.getTipoLogradouro().name());
                st.setString(parameterIndex++, end.getLogradouro());
                st.setString(parameterIndex++, end.getNumero());

                if (end.getComplemento() != null) {
                    st.setString(parameterIndex++, end.getComplemento());
                } else {
                    st.setNull(parameterIndex++, Types.VARCHAR);
                }

                st.setString(parameterIndex++, end.getObservacao());
                st.setString(parameterIndex++, end.getTipoResidencia().name());
                st.setString(parameterIndex++, end.getCep());
                st.setString(parameterIndex++, end.getBairro());
                st.setString(parameterIndex++, end.getTipoEndereco().name());
                st.setString(parameterIndex++, end.getCidade().getNome());
                st.setInt(parameterIndex++, end.getCidade().getEstado().getCodigo());

                linhasAfetadas += st.executeUpdate();
                terminoExecucao = System.currentTimeMillis();
            }

            System.out.println("Cliente cadastrado com sucesso! \n Linhas afetadas: "
                    + linhasAfetadas + "\nTempo de execução: " +
                    Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");
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
        Cliente cliente = (Cliente) entidade;
        TelefoneDAO telefoneDAO = new TelefoneDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        System.out.println(cliente.getId());
        inicializarConexao();
        try {
            conn = Db.getConnection();

            st = conn.prepareStatement("UPDATE cliente SET cli_nome = ?, "
                    + "cli_sobrenome = ?, cli_genero = ?, cli_dtNascimento = ?, cli_cpf = ? WHERE cli_id = ? ");

            st.setString(1, cliente.getNome());
            st.setString(2, cliente.getSobrenome());
            st.setString(3, cliente.getGenero());
            st.setDate(4, new java.sql.Date(cliente.getDataNascimento().getTime()));
            st.setString(5, cliente.getCpf());
            st.setInt(6, cliente.getId());

            int rowsAffected = st.executeUpdate();

            telefoneDAO.alterar(cliente.getTelefoneResidencial());
            telefoneDAO.alterar(cliente.getTelefoneCelular());
            usuarioDAO.alterar(cliente.getUsuario());

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
        Cliente cliente = (Cliente) entidade;
        System.out.println(cliente.getId());
        inicializarConexao();
        try {
            conn = Db.getConnection();
            st = conn.prepareStatement("SELECT cli_usu_id FROM cliente WHERE cli_id = ?");
            st.setInt(1, cliente.getId());
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("cli_usu_id");
                st.close();

                st = conn.prepareStatement("DELETE FROM cliente WHERE cli_id = ?");
                st.setInt(1, cliente.getId());
                st.executeUpdate();
                st.close();

                st = conn.prepareStatement("DELETE FROM usuario WHERE usu_id = ?");
                st.setInt(1, userId);
                int linhasAfetadas = st.executeUpdate();

                System.out.println("Cliente excluído com sucesso! Linhas afetadas: " + linhasAfetadas);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao excluir";
        } finally {
            finalizarConexao();
        }
    }

    @Override
    public List<Cliente> consultar(EntidadeDominio entidade) {
        Cliente cliente = (Cliente) entidade;
        List<Cliente> clientes = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TelefoneDAO telDAO = new TelefoneDAO();
        EnderecoDAO enderecoDAO = new EnderecoDAO();

        try {
            String sql = pesquisarAuxiliar(cliente);
            try (PreparedStatement st = executarPesquisa(cliente, sql)) {
                ResultSet rs = st.executeQuery();

                Long inicioExecucao = System.currentTimeMillis();
                while (rs.next()) {
                    Integer clienteId = rs.getInt("cli_id");
                    Cliente clienteAux = new Cliente(clienteId, rs.getString("cli_nome"),
                            rs.getString("cli_sobrenome"), rs.getString("cli_genero"),
                            sdf.parse(rs.getString("cli_dtNascimento")), rs.getString("cli_cpf"), rs.getInt("cli_score"),
                            telDAO.getTelefoneByCliente(clienteId, TipoTelefone.RESIDENCIAL),
                            telDAO.getTelefoneByCliente(clienteId, TipoTelefone.CELULAR),
                            enderecoDAO.getEnderecoResidencialByCliente(clienteId), cliente.getUsuario());
                    if (cliente.getUsuario() != null && cliente.getUsuario().getId() > 0) {
                        clienteAux.getUsuario().setId(rs.getInt("cli_usu_id"));
                    } else {
                        UsuarioDAO usuarioDAO = new UsuarioDAO();
                        clienteAux.setUsuario(usuarioDAO.getUsuarioById(rs.getInt("cli_usu_id")));
                    }
                    clientes.add(clienteAux);
                }
                Long terminoExecucao = System.currentTimeMillis();
                System.out.println("Tempo de execução da consulta: "
                        + Calculadora.calculaIntervaloTempo(inicioExecucao, terminoExecucao) + " segundos");
            }
            return clientes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            finalizarConexao();
        }
    }

    private String pesquisarAuxiliar(EntidadeDominio entidade) {
        if (entidade.getPesquisa() != null && entidade.getPesquisa().equals("id")) {
            return "select * from cliente  WHERE cli_id = ?";
        } else if (entidade.getPesquisa() != null && entidade.getPesquisa().equals("usuario")) {
            return "select * from cliente  WHERE cli_usu_id = ?";
        } else if (entidade.getPesquisa() != null && entidade.getPesquisa().equals("filtros")) {
            Cliente cliente = (Cliente) entidade;
            StringBuilder sql = new StringBuilder();

            sql.append("select * from cliente ");

            if (cliente.getUsuario() != null) {
                sql.append("join usuario on cli_usu_id = usu_id ");
                if (cliente.getUsuario().getEmail() != null) {
                    sql.append("and usu_email like ? ");
                }
                if (cliente.getUsuario().getStatus() != null) {
                    sql.append("and usu_status = ? ");
                }
            }

            if (cliente.getId() > 0 || cliente.getCpf() != null || cliente.getNome() != null || cliente.getSobrenome() != null
                    || cliente.getDataNascimento() != null) {
                sql.append("where cli_usu_id is not null ");
            }

            if (cliente.getId() > 0) {
                sql.append("and cli_id = ? ");
            }

            if (cliente.getCpf() != null) {
                sql.append("and cli_cpf like ? ");
            }

            if (cliente.getNome() != null) {
                sql.append("and cli_nome like ? ");
            }

            if (cliente.getSobrenome() != null) {
                sql.append("and cli_sobrenome like ? ");
            }

            if (cliente.getDataNascimento() != null) {
                sql.append("and cli_dtNascimento = ? ");
            }

            return sql.toString();
        } else {
            return "select * from cliente";
        }
    }

    private PreparedStatement executarPesquisa(Cliente cliente, String sql) throws SQLException {
        PreparedStatement st = Db.getConnection().prepareStatement(sql);
        if (cliente.getPesquisa() != null && cliente.getPesquisa().equals("id")) {
            setaParametrosQuery(st, cliente.getId());
        } else if (cliente.getPesquisa() != null && cliente.getPesquisa().equals("usuario")) {
            setaParametrosQuery(st, cliente.getUsuario().getId());
        } else if (cliente.getPesquisa() != null && cliente.getPesquisa().equals("filtros")) {
            Boolean filtroStatus = cliente.getUsuario() != null ? cliente.getUsuario().getStatus() : null;
            String filtroEmail = cliente.getUsuario() != null && cliente.getUsuario().getEmail() != null ? "%" + cliente.getUsuario().getEmail() + "%" : null;
            Integer filtroId = cliente.getId() > 0 ? cliente.getId() : null;
            String filtroCpf = cliente.getCpf() != null ? "%" + cliente.getCpf() + "%" : null;
            String filtroNome = cliente.getNome() != null ? "%" + cliente.getNome() + "%" : null;
            String filtroSobrenome = cliente.getSobrenome() != null ? "%" + cliente.getSobrenome() + "%" : null;

            setaParametrosQuery(st, filtroEmail, filtroStatus, filtroId,
                    filtroCpf, filtroNome, filtroSobrenome, cliente.getDataNascimento());
        }
        return st;
    }

    public Cliente getClienteById(Integer idCliente) {
        Cliente cliente = new Cliente();
        cliente.setId(idCliente);
        cliente.setPesquisa("id");

        if (idCliente != null && idCliente > 0) {
            return (Cliente) consultar(cliente).get(0);
        } else {
            return cliente;
        }
    }
}

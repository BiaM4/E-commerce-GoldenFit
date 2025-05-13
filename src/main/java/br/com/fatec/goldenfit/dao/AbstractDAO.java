package br.com.fatec.goldenfit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public abstract class AbstractDAO implements IDAO {
    protected Connection conn = null;
    protected PreparedStatement st = null;

    public void inicializarConexao() {
        conn = null;
        st = null;
    }

    public void setaParametrosQuery(PreparedStatement st, Object... elementos) throws SQLException {
        int posicaoParametro = 1;

        for (Object parametro : elementos) {
            if (parametro != null && !parametro.equals("")) {
                if (parametro instanceof String) {
                    st.setString(posicaoParametro, (String) parametro);
                } else if (parametro instanceof Integer) {
                    st.setInt(posicaoParametro, (Integer) parametro);
                } else if (parametro instanceof Double) {
                    st.setDouble(posicaoParametro, (Double) parametro);
                } else if (parametro instanceof Boolean) {
                    st.setBoolean(posicaoParametro, (Boolean) parametro);
                } else if (parametro instanceof Date) {
                    Date data = (Date) parametro;
                    st.setDate(posicaoParametro, new java.sql.Date(data.getTime()));
                }
                posicaoParametro++;
            }
        }
    }

    public void finalizarConexao() {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

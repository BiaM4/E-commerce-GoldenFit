package br.com.fatec.goldenfit.dao.db;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {

        public static Connection getConnection() throws SQLException {
            Connection conexao = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/ecommerce";
                String user = "root";
                String password = "";

                conexao = DriverManager.getConnection(url, user, password);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return conexao;
        }
}

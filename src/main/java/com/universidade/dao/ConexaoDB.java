package com.universidade.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    public static Connection getConexao() throws SQLException {
        String url = System.getenv("DATABASE_URL");

        if (url != null && url.startsWith("postgresql://")) {
            url = url.replace("postgresql://", "");
            String[] partes = url.split("@");
            String[] credenciais = partes[0].split(":");
            String usuario = credenciais[0];
            String senha = credenciais[1];
            String[] hostDb = partes[1].split("/");
            String host = hostDb[0];
            String banco = hostDb[1];

            String jdbcUrl = "jdbc:postgresql://" + host + "/" + banco;
            return DriverManager.getConnection(jdbcUrl, usuario, senha);
        }

        // fallback local
        return DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/universidade",
            "postgres",
            "postgres123"
        );
    }
}

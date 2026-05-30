package com.universidade.dao;

import java.sql.Connection;
import java.sql.Statement;

public class TabelasDAO {

    public void criarTabelas() {
        String sqlAluno = """
                CREATE TABLE IF NOT EXISTS tb_aluno (
                    id SERIAL PRIMARY KEY,
                    nome VARCHAR(150) NOT NULL,
                    endereco VARCHAR(200) NOT NULL,
                    matricula VARCHAR(20) NOT NULL UNIQUE,
                    data_ingresso DATE NOT NULL
                );
                """;

        String sqlMateria = """
                CREATE TABLE IF NOT EXISTS tb_materia (
                    id SERIAL PRIMARY KEY,
                    codigo VARCHAR(20) NOT NULL UNIQUE,
                    nome VARCHAR(150) NOT NULL,
                    ementa TEXT NOT NULL
                );
                """;

        String sqlTurma = """
                CREATE TABLE IF NOT EXISTS tb_turma (
                    id SERIAL PRIMARY KEY,
                    codigo VARCHAR(20) NOT NULL UNIQUE,
                    descricao VARCHAR(200),
                    materia_id INTEGER REFERENCES tb_materia(id)
                );
                """;

        String sqlMatricula = """
                CREATE TABLE IF NOT EXISTS tb_matricula (
                    id SERIAL PRIMARY KEY,
                    aluno_id INTEGER REFERENCES tb_aluno(id),
                    turma_id INTEGER REFERENCES tb_turma(id),
                    nota1 NUMERIC(4,2),
                    nota2 NUMERIC(4,2),
                    nota3 NUMERIC(4,2),
                    UNIQUE(aluno_id, turma_id)
                );
                """;

        try (Connection conn = ConexaoDB.getConexao(); Statement stmt = conn.createStatement()) {
            stmt.execute(sqlAluno);
            stmt.execute(sqlMateria);
            stmt.execute(sqlTurma);
            stmt.execute(sqlMatricula);
            System.out.println("✅ Tabelas criadas com sucesso!");
        } catch (Exception e) {
            System.err.println("❌ Erro ao criar tabelas: " + e.getMessage());
        }
    }
}

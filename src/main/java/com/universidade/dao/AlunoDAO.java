package com.universidade.dao;

import com.universidade.model.Aluno;
import com.universidade.model.Matricula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    public void inserir(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO tb_aluno (nome, endereco, matricula, data_ingresso) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEndereco());
            stmt.setString(3, aluno.getMatricula());
            stmt.setDate(4, Date.valueOf(aluno.getDataIngresso()));
            stmt.executeUpdate();
        }
    }

    public List<Aluno> listarTodos() throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM tb_aluno ORDER BY nome";
        try (Connection conn = ConexaoDB.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Aluno a = new Aluno();
                a.setId(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setEndereco(rs.getString("endereco"));
                a.setMatricula(rs.getString("matricula"));
                a.setDataIngresso(rs.getDate("data_ingresso").toLocalDate());
                alunos.add(a);
            }
        }
        return alunos;
    }

    public Aluno buscarPorMatricula(String matricula) throws SQLException {
        String sql = "SELECT * FROM tb_aluno WHERE matricula = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Aluno a = new Aluno();
                a.setId(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setEndereco(rs.getString("endereco"));
                a.setMatricula(rs.getString("matricula"));
                a.setDataIngresso(rs.getDate("data_ingresso").toLocalDate());
                return a;
            }
        }
        return null;
    }

    public void deletar(Integer id) throws SQLException {
        String sqlMatricula = "DELETE FROM tb_matricula WHERE aluno_id = ?";
        String sqlAluno = "DELETE FROM tb_aluno WHERE id = ?";
        try (Connection conn = ConexaoDB.getConexao()) {
            conn.setAutoCommit(false);
            try {
                PreparedStatement s1 = conn.prepareStatement(sqlMatricula);
                s1.setInt(1, id);
                s1.executeUpdate();

                PreparedStatement s2 = conn.prepareStatement(sqlAluno);
                s2.setInt(1, id);
                s2.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public List<Matricula> buscarMateriasPorAluno(Integer alunoId) throws SQLException {
        List<Matricula> lista = new ArrayList<>();
        String sql = """
                SELECT m.id, m.aluno_id, m.turma_id,
                       mt.nome as materia_nome,
                       m.nota1, m.nota2, m.nota3
                FROM tb_matricula m
                JOIN tb_turma t ON t.id = m.turma_id
                JOIN tb_materia mt ON mt.id = t.materia_id
                WHERE m.aluno_id = ?
                """;
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, alunoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Matricula mc = new Matricula();
                mc.setId(rs.getInt("id"));
                mc.setAlunoId(rs.getInt("aluno_id"));
                mc.setTurmaId(rs.getInt("turma_id"));
                mc.setAlunoNome(rs.getString("materia_nome"));
                mc.setNota1(rs.getObject("nota1") != null ? rs.getDouble("nota1") : null);
                mc.setNota2(rs.getObject("nota2") != null ? rs.getDouble("nota2") : null);
                mc.setNota3(rs.getObject("nota3") != null ? rs.getDouble("nota3") : null);
                lista.add(mc);
            }
        }
        return lista;
    }
}

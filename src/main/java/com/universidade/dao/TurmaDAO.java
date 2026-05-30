package com.universidade.dao;

import com.universidade.model.Matricula;
import com.universidade.model.Turma;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaDAO {

    public void inserir(Turma turma) throws SQLException {
        String sql = "INSERT INTO tb_turma (codigo, descricao, materia_id) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, turma.getCodigo());
            stmt.setString(2, turma.getDescricao());
            stmt.setInt(3, turma.getMateriaId());
            stmt.executeUpdate();
        }
    }

    public List<Turma> listarTodos() throws SQLException {
        List<Turma> turmas = new ArrayList<>();
        String sql = """
                SELECT t.*, m.nome as materia_nome
                FROM tb_turma t
                LEFT JOIN tb_materia m ON m.id = t.materia_id
                ORDER BY t.codigo
                """;
        try (Connection conn = ConexaoDB.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Turma t = new Turma();
                t.setId(rs.getInt("id"));
                t.setCodigo(rs.getString("codigo"));
                t.setDescricao(rs.getString("descricao"));
                t.setMateriaId(rs.getInt("materia_id"));
                t.setMateriaNome(rs.getString("materia_nome"));
                turmas.add(t);
            }
        }
        return turmas;
    }

    public Turma buscarPorCodigo(String codigo) throws SQLException {
        String sql = """
                SELECT t.*, m.nome as materia_nome
                FROM tb_turma t
                LEFT JOIN tb_materia m ON m.id = t.materia_id
                WHERE t.codigo = ?
                """;
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Turma t = new Turma();
                t.setId(rs.getInt("id"));
                t.setCodigo(rs.getString("codigo"));
                t.setDescricao(rs.getString("descricao"));
                t.setMateriaId(rs.getInt("materia_id"));
                t.setMateriaNome(rs.getString("materia_nome"));
                return t;
            }
        }
        return null;
    }

    public List<Matricula> buscarAlunosPorTurma(Integer turmaId) throws SQLException {
        List<Matricula> lista = new ArrayList<>();
        String sql = """
                SELECT m.id, m.aluno_id, m.turma_id,
                       a.nome as aluno_nome, a.matricula as aluno_matricula,
                       m.nota1, m.nota2, m.nota3
                FROM tb_matricula m
                JOIN tb_aluno a ON a.id = m.aluno_id
                WHERE m.turma_id = ?
                ORDER BY a.nome
                """;
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, turmaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Matricula mc = new Matricula();
                mc.setId(rs.getInt("id"));
                mc.setAlunoId(rs.getInt("aluno_id"));
                mc.setTurmaId(rs.getInt("turma_id"));
                mc.setAlunoNome(rs.getString("aluno_nome"));
                mc.setAlunoMatricula(rs.getString("aluno_matricula"));
                mc.setNota1(rs.getObject("nota1") != null ? rs.getDouble("nota1") : null);
                mc.setNota2(rs.getObject("nota2") != null ? rs.getDouble("nota2") : null);
                mc.setNota3(rs.getObject("nota3") != null ? rs.getDouble("nota3") : null);
                lista.add(mc);
            }
        }
        return lista;
    }

    public void matricularAluno(Integer alunoId, Integer turmaId) throws SQLException {
        String sql = "INSERT INTO tb_matricula (aluno_id, turma_id) VALUES (?, ?)";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, alunoId);
            stmt.setInt(2, turmaId);
            stmt.executeUpdate();
        }
    }

    public void lancarNotas(Integer matriculaId, Double nota1, Double nota2, Double nota3) throws SQLException {
        String sql = "UPDATE tb_matricula SET nota1 = ?, nota2 = ?, nota3 = ? WHERE id = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, nota1);
            stmt.setDouble(2, nota2);
            stmt.setDouble(3, nota3);
            stmt.setInt(4, matriculaId);
            stmt.executeUpdate();
        }
    }

    public void deletar(Integer id) throws SQLException {
        String sqlMatricula = "DELETE FROM tb_matricula WHERE turma_id = ?";
        String sqlTurma = "DELETE FROM tb_turma WHERE id = ?";
        try (Connection conn = ConexaoDB.getConexao()) {
            conn.setAutoCommit(false);
            try {
                PreparedStatement s1 = conn.prepareStatement(sqlMatricula);
                s1.setInt(1, id);
                s1.executeUpdate();

                PreparedStatement s2 = conn.prepareStatement(sqlTurma);
                s2.setInt(1, id);
                s2.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}

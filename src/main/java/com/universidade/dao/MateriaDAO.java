package com.universidade.dao;

import com.universidade.model.Materia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriaDAO {

    public void inserir(Materia materia) throws SQLException {
        String sql = "INSERT INTO tb_materia (codigo, nome, ementa) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, materia.getCodigo());
            stmt.setString(2, materia.getNome());
            stmt.setString(3, materia.getEmenta());
            stmt.executeUpdate();
        }
    }

    public List<Materia> listarTodos() throws SQLException {
        List<Materia> materias = new ArrayList<>();
        String sql = "SELECT * FROM tb_materia ORDER BY nome";
        try (Connection conn = ConexaoDB.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Materia m = new Materia();
                m.setId(rs.getInt("id"));
                m.setCodigo(rs.getString("codigo"));
                m.setNome(rs.getString("nome"));
                m.setEmenta(rs.getString("ementa"));
                materias.add(m);
            }
        }
        return materias;
    }

    public Materia buscarPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT * FROM tb_materia WHERE codigo = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Materia m = new Materia();
                m.setId(rs.getInt("id"));
                m.setCodigo(rs.getString("codigo"));
                m.setNome(rs.getString("nome"));
                m.setEmenta(rs.getString("ementa"));
                return m;
            }
        }
        return null;
    }

    public void deletar(Integer id) throws SQLException {
        String sql = "DELETE FROM tb_materia WHERE id = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

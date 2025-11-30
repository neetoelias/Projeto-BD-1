package br.com.avaliacao.dal;

import br.com.avaliacao.model.Disciplina;
import br.com.avaliacao.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DisciplinaDAO {

    public Disciplina cadastrar(Disciplina disciplina) throws SQLException {

        String sql = "INSERT INTO DISCIPLINA (nome, codigo) " +
                "VALUES (?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, disciplina.getNome());
            stmt.setString(2, disciplina.getCodigo());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    disciplina.setIdDisciplina(rs.getInt(1));
                }
            } else {
                throw new SQLException("Falha ao cadastrar disciplina, nenhuma linha afetada.");
            }
            conn.commit();
            return disciplina;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Erro ao reverter transação da Disciplina: " + ex.getMessage());
                }
            }
            throw e;

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
    public java.util.List<Disciplina> listarTodas() throws SQLException {
        String sql = "SELECT * FROM DISCIPLINA";
        java.util.List<Disciplina> lista = new java.util.ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Disciplina(rs.getInt("id_disciplina"), rs.getString("nome"), rs.getString("codigo")));
            }
        }
        return lista;
    }
}
package br.com.avaliacao.dal;

import br.com.avaliacao.model.RealizacaoAvaliacao;
import br.com.avaliacao.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RealizacaoAvaliacaoDAO {

    public RealizacaoAvaliacao cadastrar(RealizacaoAvaliacao realizacao) throws SQLException {
        String sql = "INSERT INTO REALIZACAO_AVALIACAO (id_avaliacao, id_aluno, data_inicio) VALUES (?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, realizacao.getIdAvaliacao());
            ps.setInt(2, realizacao.getIdAluno());
            ps.setObject(3, realizacao.getDataInicio());

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                realizacao.setIdRealizacao(rs.getInt(1));
            } else {
                throw new SQLException("Falha ao cadastrar realização, nenhum ID retornado.");
            }

            conn.commit();

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar realização: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Erro ao reverter transação da Realização: " + ex.getMessage());
                }
            }
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return realizacao;
    }
}
package br.com.avaliacao.dal;

import br.com.avaliacao.model.Questao;
import br.com.avaliacao.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QuestaoDAO {

    public Questao cadastrar(Questao questao) throws SQLException {

        String sql = "INSERT INTO QUESTAO (descricao, tipo_questao, id_professor, gabarito_texto) " +
                "VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, questao.getDescricao());
            stmt.setString(2, questao.getTipoQuestao());
            stmt.setInt(3, questao.getIdProfessor());

            stmt.setString(4, questao.getGabaritoTexto());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    questao.setIdQuestao(rs.getInt(1));
                }
            } else {
                throw new SQLException("Falha ao cadastrar questão, nenhuma linha afetada.");
            }
            conn.commit();

            return questao;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Erro ao reverter transação da Questao: " + ex.getMessage());
                }
            }
            throw e;

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
}
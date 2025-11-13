package br.com.avaliacao.dal;

import br.com.avaliacao.model.Avaliacao;
import br.com.avaliacao.model.ItemAvaliacao;
import br.com.avaliacao.model.Questao;
import br.com.avaliacao.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class AvaliacaoDAO {

    public Avaliacao cadastrar(Avaliacao avaliacao) throws SQLException {
        String sqlAvaliacao = "INSERT INTO AVALIACAO (descricao, data_abertura, data_fechamento, valor_total, id_professor, id_disciplina) " +
                "VALUES (?, ?, ?, ?, ?, ?)";


        String sqlItem = "INSERT INTO ITEM_AVALIACAO (id_avaliacao, id_questao, valor_pontuacao) VALUES (?, ?, ?)";

        Connection conn = null;
        PreparedStatement psAvaliacao = null;
        PreparedStatement psItem = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            psAvaliacao = conn.prepareStatement(sqlAvaliacao, PreparedStatement.RETURN_GENERATED_KEYS);
            psAvaliacao.setString(1, avaliacao.getTitulo());
            psAvaliacao.setObject(2, avaliacao.getDataCriacao().toLocalDate());
            psAvaliacao.setObject(3, avaliacao.getDataLimite().toLocalDate());
            psAvaliacao.setDouble(4, avaliacao.getValorTotal());
            psAvaliacao.setInt(5, avaliacao.getIdProfessor());
            psAvaliacao.setInt(6, avaliacao.getIdDisciplina());

            psAvaliacao.executeUpdate();
            rs = psAvaliacao.getGeneratedKeys();

            if (rs.next()) {
                avaliacao.setIdAvaliacao(rs.getInt(1)); // Pega o ID da avaliação recém-criada
            } else {
                throw new SQLException("Falha ao cadastrar avaliação, nenhum ID retornado.");
            }

            if (avaliacao.getItens() != null && !avaliacao.getItens().isEmpty()) {
                psItem = conn.prepareStatement(sqlItem);

                for (ItemAvaliacao item : avaliacao.getItens()) {
                    psItem.setInt(1, avaliacao.getIdAvaliacao());
                    psItem.setInt(2, item.getIdQuestao());
                    psItem.setDouble(3, item.getValorPontuacao());

                    psItem.addBatch();
                }
                psItem.executeBatch();
            }

            conn.commit();
            System.out.println("Conexão PostgreSQL estabelecida com sucesso!");

        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (psItem != null) psItem.close();
            if (rs != null) rs.close();
            if (psAvaliacao != null) psAvaliacao.close();
            if (conn != null) conn.close();
        }

        return avaliacao;
    }
}
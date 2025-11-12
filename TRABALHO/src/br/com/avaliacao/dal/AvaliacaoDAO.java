package br.com.avaliacao.dal;

import br.com.avaliacao.model.Avaliacao;
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
                              "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_avaliacao";
        
        String sqlQuestaoVinculo = "INSERT INTO AVALIACAO_QUESTAO (id_avaliacao, id_questao) VALUES (?, ?)";

        Connection conn = null;
        PreparedStatement stmtAvaliacao = null;
        PreparedStatement stmtQuestao = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); 

           
            stmtAvaliacao = conn.prepareStatement(sqlAvaliacao, Statement.RETURN_GENERATED_KEYS);
            
            
            stmtAvaliacao.setString(1, avaliacao.getTitulo()); 
           
            stmtAvaliacao.setTimestamp(2, Timestamp.valueOf(avaliacao.getDataCriacao()));
          
            stmtAvaliacao.setTimestamp(3, Timestamp.valueOf(avaliacao.getDataLimite()));
          
            stmtAvaliacao.setDouble(4, avaliacao.getValorTotal());
            
            stmtAvaliacao.setInt(5, avaliacao.getIdProfessor());
           
            stmtAvaliacao.setInt(6, avaliacao.getIdDisciplina());

            int rowsAffected = stmtAvaliacao.executeUpdate();

             if (rowsAffected > 0) {
                rs = stmtAvaliacao.getGeneratedKeys(); 
                if (rs.next()) {
                    avaliacao.setIdAvaliacao(rs.getInt(1)); 
                }
            } else {
                conn.rollback();
                throw new SQLException("Falha ao cadastrar a Avaliação principal.");
            }
            
            // B) Inserir o vínculo com as Questões
            if (avaliacao.getQuestoes() != null && !avaliacao.getQuestoes().isEmpty()) {
                stmtQuestao = conn.prepareStatement(sqlQuestaoVinculo);
                
                for (Questao questao : avaliacao.getQuestoes()) {
                    stmtQuestao.setInt(1, avaliacao.getIdAvaliacao());
                    if (questao.getIdQuestao() != null) { 
                        stmtQuestao.setInt(2, questao.getIdQuestao());
                        stmtQuestao.addBatch();
                    }
                }
                
                stmtQuestao.executeBatch();
            }

            conn.commit(); 
            return avaliacao;

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); 
            }
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (stmtAvaliacao != null) stmtAvaliacao.close();
            if (stmtQuestao != null) stmtQuestao.close();
            if (conn != null) conn.setAutoCommit(true); 
        }
    }
}
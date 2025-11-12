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
        
        String sql = "INSERT INTO QUESTAO (descricao, tipo_questao, id_professor) " +
                     "VALUES (?, ?, ?) RETURNING id_questao"; 

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            
            stmt.setString(1, questao.getDescricao());
            stmt.setString(2, questao.getTipoQuestao());
            stmt.setInt(3, questao.getIdProfessor()); 

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                rs = stmt.getGeneratedKeys(); 
                if (rs.next()) {
                    questao.setIdQuestao(rs.getInt(1)); 
                }
            }
            return questao;

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }
}
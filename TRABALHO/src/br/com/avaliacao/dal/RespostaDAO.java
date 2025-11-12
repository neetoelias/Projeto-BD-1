package br.com.avaliacao.dal;

import br.com.avaliacao.model.Resposta;
import br.com.avaliacao.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RespostaDAO {

   
    public Resposta cadastrar(Resposta resposta) throws SQLException {
        
        String sql = "INSERT INTO RESPOSTA (id_avaliacao, id_questao, id_usuario, conteudo_resposta) " +
                     "VALUES (?, ?, ?, ?) RETURNING id_resposta"; 

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            
            stmt.setInt(1, resposta.getIdAvaliacao());
            stmt.setInt(2, resposta.getIdQuestao());
            stmt.setInt(3, resposta.getIdUsuario());
            stmt.setString(4, resposta.getConteudoResposta());

            int rowsAffected = stmt.executeUpdate();

            
            if (rowsAffected > 0) {
                rs = stmt.getGeneratedKeys(); 
                if (rs.next()) {
                    resposta.setIdResposta(rs.getInt(1)); 
                }
            }
            return resposta;

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            
        }
    }
}
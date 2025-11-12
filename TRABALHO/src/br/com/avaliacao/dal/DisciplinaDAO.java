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
                     "VALUES (?, ?) RETURNING id_disciplina"; 

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            
            stmt.setString(1, disciplina.getNome());
            stmt.setString(2, disciplina.getCodigo());

            int rowsAffected = stmt.executeUpdate();

           
            if (rowsAffected > 0) {
                rs = stmt.getGeneratedKeys(); 
                if (rs.next()) {
                    disciplina.setIdDisciplina(rs.getInt(1)); 
                }
            }
            return disciplina;

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();

        }
    }
}
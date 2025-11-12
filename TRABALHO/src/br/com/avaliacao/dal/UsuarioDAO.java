package br.com.avaliacao.dal;

import br.com.avaliacao.model.Usuario;
import br.com.avaliacao.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAO {

   
    public Usuario cadastrar(Usuario usuario) throws SQLException {
        
        String sql = "INSERT INTO USUARIO (nome, email, senha_hash, tipo_usuario) " +
                     "VALUES (?, ?, ?, ?) RETURNING id_usuario"; 

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenhaHash());
            stmt.setString(4, usuario.getTipoUsuario());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                
                rs = stmt.getGeneratedKeys(); 
                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt(1)); 
                }
            }
            return usuario;

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }
    
    
    public Usuario buscarPorEmail(String email) throws SQLException {
        
        String sql = "SELECT id_usuario, nome, email, senha_hash, tipo_usuario " +
                     "FROM USUARIO WHERE email = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null; 

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, email);

            rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha_hash"),
                    rs.getString("tipo_usuario")
                );
            }
            return usuario;

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }
}
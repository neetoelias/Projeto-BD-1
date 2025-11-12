package br.com.avaliacao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

    
    private static final String URL = "jdbc:postgresql://localhost:5432/avaliacao_bd1?sslmode=disable&options=-c%20password_encryption%3Dmd5"; 
    private static final String USER = "postgres"; 
    private static final String PASSWORD = "pistoia1902";
    private static Connection connection = null;

    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                
                Class.forName("org.postgresql.Driver"); 
                
               
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexão PostgreSQL estabelecida com sucesso!");
                
            } catch (ClassNotFoundException e) {
                System.err.println("Erro: Driver JDBC do PostgreSQL não encontrado. Adicione o 'postgresql.jar' ao seu classpath.");
                throw new SQLException("Driver PostgreSQL não encontrado: " + e.getMessage());
            }
        }
        return connection;
    }
    
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null; 
                System.out.println("Conexão PostgreSQL fechada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão PostgreSQL: " + e.getMessage());
            }
        }
    }
}
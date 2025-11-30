package br.com.avaliacao.dal;

import br.com.avaliacao.model.Questao;
import br.com.avaliacao.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuestaoDAO {

    public Questao cadastrar(Questao questao) throws SQLException {

        
        String sql = "INSERT INTO QUESTAO (descricao, tipo_questao, id_professor, gabarito_texto, id_disciplina) " +
                "VALUES (?, ?, ?, ?, ?)";

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
           
            stmt.setInt(5, questao.getIdDisciplina());

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

    
    public List<Questao> listarPorDisciplina(Integer idDisciplina) throws SQLException {
        String sql = "SELECT * FROM QUESTAO WHERE id_disciplina = ? ORDER BY id_questao DESC";
        List<Questao> lista = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDisciplina);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Questao q = new Questao(
                            rs.getInt("id_questao"),
                            rs.getString("descricao"),
                            rs.getString("tipo_questao"),
                            rs.getInt("id_professor"),
                            rs.getInt("id_disciplina") // <--- Preenche a disciplina
                    );
                    q.setGabaritoTexto(rs.getString("gabarito_texto"));
                    lista.add(q);
                }
            }
        }
        return lista;
    }

    
    public List<Questao> listarPorProfessor(Integer idProfessor) throws SQLException {
        String sql = "SELECT * FROM QUESTAO WHERE id_professor = ? ORDER BY id_questao DESC";
        List<Questao> lista = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProfessor);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Questao q = new Questao(
                            rs.getInt("id_questao"),
                            rs.getString("descricao"),
                            rs.getString("tipo_questao"),
                            rs.getInt("id_professor"),
                            rs.getInt("id_disciplina") // <--- Atualizado
                    );
                    q.setGabaritoTexto(rs.getString("gabarito_texto"));
                    lista.add(q);
                }
            }
        }
        return lista;
    }

    public List<Questao> listarPorAvaliacao(Integer idAvaliacao) throws SQLException {
        List<Questao> lista = new ArrayList<>();
        String sql = "SELECT q.* FROM QUESTAO q " +
                "JOIN ITEM_AVALIACAO i ON q.id_questao = i.id_questao " +
                "WHERE i.id_avaliacao = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAvaliacao);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    Questao q = new Questao(
                            rs.getInt("id_questao"),
                            rs.getString("descricao"),
                            rs.getString("tipo_questao"),
                            rs.getInt("id_professor"),
                            rs.getInt("id_disciplina") 
                    );

                   
                    if ("MULTIPLA_ESCOLHA".equals(q.getTipoQuestao())) {
                        br.com.avaliacao.dal.OpcaoDAO opcaoDAO = new br.com.avaliacao.dal.OpcaoDAO();
                        q.setOpcoes(opcaoDAO.listarPorQuestao(q.getIdQuestao()));
                    }
                    lista.add(q);
                }
            }
        }
        return lista;
    }

   
    public java.util.List<Questao> listarTodas() throws SQLException {
        String sql = "SELECT * FROM QUESTAO ORDER BY id_questao DESC";
        java.util.List<Questao> lista = new java.util.ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Questao q = new Questao(
                        rs.getInt("id_questao"),
                        rs.getString("descricao"),
                        rs.getString("tipo_questao"),
                        rs.getInt("id_professor"),
                        rs.getInt("id_disciplina") 
                );
                q.setGabaritoTexto(rs.getString("gabarito_texto"));
                lista.add(q);
            }
        }
        return lista;
    }
}
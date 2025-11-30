package br.com.avaliacao.dal;

import br.com.avaliacao.model.Avaliacao;
import br.com.avaliacao.model.ItemAvaliacao;
import br.com.avaliacao.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

            
            psAvaliacao = conn.prepareStatement(sqlAvaliacao, Statement.RETURN_GENERATED_KEYS);
            psAvaliacao.setString(1, avaliacao.getTitulo());
            psAvaliacao.setObject(2, avaliacao.getDataCriacao().toLocalDate());
            psAvaliacao.setObject(3, avaliacao.getDataLimite().toLocalDate());
            psAvaliacao.setDouble(4, avaliacao.getValorTotal());
            psAvaliacao.setInt(5, avaliacao.getIdProfessor());
            psAvaliacao.setInt(6, avaliacao.getIdDisciplina());

            psAvaliacao.executeUpdate();
            rs = psAvaliacao.getGeneratedKeys();

            if (rs.next()) {
                avaliacao.setIdAvaliacao(rs.getInt(1)); 
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
            System.out.println("Avaliação cadastrada com sucesso!");

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

    
    public List<br.com.avaliacao.model.dto.AvaliacaoDisponivelDTO> listarParaAluno(Integer idAluno) throws SQLException {
        List<br.com.avaliacao.model.dto.AvaliacaoDisponivelDTO> lista = new ArrayList<>();

      
        String sql = "SELECT a.id_avaliacao, a.descricao, d.nome AS disciplina, a.data_fechamento, " +
                "(SELECT COUNT(*) FROM REALIZACAO_AVALIACAO r WHERE r.id_avaliacao = a.id_avaliacao AND r.id_aluno = ?) > 0 AS realizada " +
                "FROM AVALIACAO a " +
                "JOIN DISCIPLINA d ON a.id_disciplina = d.id_disciplina " +
                "JOIN MATRICULA m ON d.id_disciplina = m.id_disciplina " + 
                "WHERE m.id_aluno = ? " + 
                "ORDER BY a.data_fechamento DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAluno); 
            ps.setInt(2, idAluno); 

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new br.com.avaliacao.model.dto.AvaliacaoDisponivelDTO(
                        rs.getInt("id_avaliacao"),
                        rs.getString("descricao"),
                        rs.getString("disciplina"),
                        rs.getDate("data_fechamento") != null ? rs.getDate("data_fechamento").toLocalDate().atStartOfDay() : null,
                        rs.getBoolean("realizada")
                ));
            }
        }
        return lista;
    }
}
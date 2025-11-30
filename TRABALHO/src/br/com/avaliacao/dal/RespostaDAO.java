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

        String sql = "INSERT INTO RESPOSTA_QUESTAO (id_realizacao, id_questao, resposta_texto, id_opcao_escolhida) " +
                "VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, resposta.getIdRealizacao());
            ps.setInt(2, resposta.getIdQuestao());

            ps.setString(3, resposta.getRespostaTexto());

            ps.setObject(4, resposta.getIdOpcaoEscolhida());

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                resposta.setIdResposta(rs.getInt(1));
            } else {
                throw new SQLException("Falha ao cadastrar resposta, nenhum ID retornado.");
            }

            conn.commit();

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar resposta: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Erro ao reverter transação da Resposta: " + ex.getMessage());
                }
            }
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return resposta;
    }

    

    public java.util.List<br.com.avaliacao.model.dto.ItemCorrecao> buscarParaCorrecao(Integer idRealizacao) throws SQLException {
        java.util.List<br.com.avaliacao.model.dto.ItemCorrecao> lista = new java.util.ArrayList<>();

        String sql = "SELECT " +
                "  r.id_resposta, q.descricao AS enunciado, q.tipo_questao, " +
                "  r.resposta_texto, r.nota_obtida, " +
                "  q.gabarito_texto, " +
                "  op_aluno.descricao_opcao AS opcao_aluno, " +
                "  op_gabarito.descricao_opcao AS opcao_gabarito, " +
                "  item.valor_pontuacao " +
                "FROM RESPOSTA_QUESTAO r " +
                "JOIN REALIZACAO_AVALIACAO rea ON r.id_realizacao = rea.id_realizacao " +
                "JOIN QUESTAO q ON r.id_questao = q.id_questao " +
                "JOIN ITEM_AVALIACAO item ON (item.id_questao = q.id_questao AND item.id_avaliacao = rea.id_avaliacao) " +
                "LEFT JOIN OPCAO op_aluno ON r.id_opcao_escolhida = op_aluno.id_opcao " +
                "LEFT JOIN OPCAO op_gabarito ON (q.id_questao = op_gabarito.id_questao AND op_gabarito.esta_correta = true)";

        sql += " WHERE r.id_realizacao = ? ORDER BY r.id_resposta";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idRealizacao);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String tipo = rs.getString("tipo_questao");
                String respAluno = "";
                String gabarito = "";

                if ("MULTIPLA_ESCOLHA".equals(tipo)) {
                    respAluno = rs.getString("opcao_aluno");
                    gabarito = rs.getString("opcao_gabarito");
                } else {
                    respAluno = rs.getString("resposta_texto");
                    gabarito = rs.getString("gabarito_texto");
                }

               
                Double valorPontuacao = rs.getBigDecimal("valor_pontuacao") != null ?
                        rs.getBigDecimal("valor_pontuacao").doubleValue() : 0.0;

                Double notaObtida = rs.getBigDecimal("nota_obtida") != null ?
                        rs.getBigDecimal("nota_obtida").doubleValue() : null;

                lista.add(new br.com.avaliacao.model.dto.ItemCorrecao(
                        rs.getInt("id_resposta"),
                        rs.getString("enunciado"),
                        tipo,
                        respAluno,
                        gabarito,
                        valorPontuacao, 
                        notaObtida      
                ));
            }
        }
        return lista;
    }

    public void atualizarNota(Integer idResposta, Double nota) throws SQLException {
        String sql = "UPDATE RESPOSTA_QUESTAO SET nota_obtida = ? WHERE id_resposta = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, nota);
            ps.setInt(2, idResposta);
            ps.executeUpdate();
        }
    }
}
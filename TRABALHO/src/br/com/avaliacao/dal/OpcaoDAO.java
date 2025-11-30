package br.com.avaliacao.dal;

import br.com.avaliacao.model.Opcao;
import br.com.avaliacao.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OpcaoDAO {

    
    public Opcao cadastrar(Opcao opcao) throws SQLException {
        String sql = "INSERT INTO OPCAO (id_questao, descricao_opcao, esta_correta) VALUES (?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, opcao.getIdQuestao());
            ps.setString(2, opcao.getDescricaoOpcao());
            ps.setBoolean(3, opcao.getEstaCorreta());

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                opcao.setIdOpcao(rs.getInt(1));
            } else {
                throw new SQLException("Falha ao cadastrar opção, nenhum ID retornado.");
            }

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return opcao;
    }

    
    public List<Opcao> listarPorQuestao(Integer idQuestao) throws SQLException {
        List<Opcao> lista = new ArrayList<>();
        String sql = "SELECT * FROM OPCAO WHERE id_questao = ? ORDER BY id_opcao ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idQuestao);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Opcao op = new Opcao(
                            rs.getInt("id_questao"),
                            rs.getString("descricao_opcao"),
                            rs.getBoolean("esta_correta")
                    );
                    op.setIdOpcao(rs.getInt("id_opcao")); 
                    lista.add(op);
                }
            }
        }
        return lista;
    }
}
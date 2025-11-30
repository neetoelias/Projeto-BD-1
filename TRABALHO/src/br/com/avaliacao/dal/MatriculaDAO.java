package br.com.avaliacao.dal;

import br.com.avaliacao.model.Disciplina;
import br.com.avaliacao.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO {

   
    public void matricular(Integer idAluno, Integer idDisciplina) throws SQLException {
        String sql = "INSERT INTO MATRICULA (id_aluno, id_disciplina) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAluno);
            ps.setInt(2, idDisciplina);
            ps.executeUpdate();
        }
    }

   
    public void cancelarMatricula(Integer idAluno, Integer idDisciplina) throws SQLException {
        String sql = "DELETE FROM MATRICULA WHERE id_aluno = ? AND id_disciplina = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAluno);
            ps.setInt(2, idDisciplina);
            ps.executeUpdate();
        }
    }

    
    public List<Disciplina> listarMatriculadas(Integer idAluno) throws SQLException {
        List<Disciplina> lista = new ArrayList<>();
        String sql = "SELECT d.* FROM DISCIPLINA d " +
                "JOIN MATRICULA m ON d.id_disciplina = m.id_disciplina " +
                "WHERE m.id_aluno = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAluno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Disciplina(rs.getInt("id_disciplina"), rs.getString("nome"), rs.getString("codigo")));
            }
        }
        return lista;
    }

    
    public List<Disciplina> listarDisponiveis(Integer idAluno) throws SQLException {
        List<Disciplina> lista = new ArrayList<>();
        String sql = "SELECT * FROM DISCIPLINA WHERE id_disciplina NOT IN " +
                "(SELECT id_disciplina FROM MATRICULA WHERE id_aluno = ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAluno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Disciplina(rs.getInt("id_disciplina"), rs.getString("nome"), rs.getString("codigo")));
            }
        }
        return lista;
    }
}
package br.com.avaliacao.dal;

import br.com.avaliacao.model.dto.RelatorioAlunoDTO;
import br.com.avaliacao.model.dto.RelatorioProfessorDTO;
import br.com.avaliacao.model.dto.RelatorioRealizacao;
import br.com.avaliacao.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RelatorioDAO {

    
    public List<RelatorioRealizacao> buscarRealizacoesPorProfessor(Integer idProfessor) throws SQLException {
        List<RelatorioRealizacao> lista = new ArrayList<>();

       
        String sql = "SELECT r.id_realizacao, a.id_avaliacao, u.nome AS aluno, a.descricao AS prova, r.data_inicio " +
                "FROM REALIZACAO_AVALIACAO r " +
                "JOIN USUARIO u ON r.id_aluno = u.id_usuario " +
                "JOIN AVALIACAO a ON r.id_avaliacao = a.id_avaliacao " +
                "WHERE a.id_professor = ? " +
                "ORDER BY r.data_inicio DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProfessor);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new RelatorioRealizacao(
                        rs.getInt("id_realizacao"),
                        rs.getInt("id_avaliacao"), // <--- PEGA O ID DO BANCO
                        rs.getString("aluno"),
                        rs.getString("prova"),
                        rs.getTimestamp("data_inicio").toLocalDateTime()
                ));
            }
        }
        return lista;
    }

    
    public RelatorioProfessorDTO gerarEstatisticasProva(Integer idAvaliacao) throws SQLException {
        List<Double> notas = new ArrayList<>();
        String disciplina = "";

        
        String sql = "SELECT d.nome AS disciplina, SUM(COALESCE(rq.nota_obtida, 0)) as nota_final " +
                "FROM REALIZACAO_AVALIACAO ra " +
                "JOIN AVALIACAO a ON ra.id_avaliacao = a.id_avaliacao " +
                "JOIN DISCIPLINA d ON a.id_disciplina = d.id_disciplina " +
                "LEFT JOIN RESPOSTA_QUESTAO rq ON ra.id_realizacao = rq.id_realizacao " +
                "WHERE ra.id_avaliacao = ? " +
                "GROUP BY ra.id_realizacao, d.nome";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAvaliacao);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                disciplina = rs.getString("disciplina");
                notas.add(rs.getDouble("nota_final"));
            }
        }

       
        if (notas.isEmpty()) {
            return new RelatorioProfessorDTO("Sem dados", 0.0, 0.0, 0.0, 0.0);
        }

        
        Collections.sort(notas); 

        double soma = 0;
        for (Double n : notas) soma += n;

        Double media = soma / notas.size();
        Double menor = notas.get(0);
        Double maior = notas.get(notas.size() - 1);

        
        Double mediana;
        int tamanho = notas.size();
        if (tamanho % 2 == 0) {
            mediana = (notas.get(tamanho/2 - 1) + notas.get(tamanho/2)) / 2.0;
        } else {
            mediana = notas.get(tamanho/2);
        }

        return new RelatorioProfessorDTO(disciplina, media, mediana, maior, menor);
    }

    
    public List<RelatorioAlunoDTO> gerarBoletimAluno(Integer idAluno) throws SQLException {
        List<RelatorioAlunoDTO> boletim = new ArrayList<>();

        String sql =
                "WITH NotasAlunos AS ( " +
                        "    SELECT " +
                        "        a.id_avaliacao, " +
                        "        a.descricao AS nome_prova, " +
                        "        ra.id_aluno, " +
                        "        SUM(COALESCE(rq.nota_obtida, 0)) as nota_total " +
                        "    FROM REALIZACAO_AVALIACAO ra " +
                        "    JOIN AVALIACAO a ON ra.id_avaliacao = a.id_avaliacao " +
                        "    LEFT JOIN RESPOSTA_QUESTAO rq ON ra.id_realizacao = rq.id_realizacao " +
                        "    GROUP BY a.id_avaliacao, a.descricao, ra.id_aluno " +
                        "), " +
                        "Estatisticas AS ( " +
                        "    SELECT " +
                        "        id_avaliacao, " +
                        "        nome_prova, " +
                        "        id_aluno, " +
                        "        nota_total, " +
                        "        AVG(nota_total) OVER (PARTITION BY id_avaliacao) as media_turma, " +
                        "        RANK() OVER (PARTITION BY id_avaliacao ORDER BY nota_total DESC) as posicao " +
                        "    FROM NotasAlunos " +
                        ") " +
                        "SELECT * FROM Estatisticas WHERE id_aluno = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAluno);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                boletim.add(new RelatorioAlunoDTO(
                        rs.getString("nome_prova"),
                        rs.getDouble("nota_total"),
                        rs.getDouble("media_turma"),
                        rs.getInt("posicao")
                ));
            }
        }
        return boletim;
    }
}
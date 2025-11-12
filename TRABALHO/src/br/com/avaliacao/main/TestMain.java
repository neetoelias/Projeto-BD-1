package br.com.avaliacao.main;

import br.com.avaliacao.model.Usuario;
import br.com.avaliacao.dal.UsuarioDAO;
import br.com.avaliacao.model.Disciplina;
import br.com.avaliacao.dal.DisciplinaDAO;
import br.com.avaliacao.model.Questao;
import br.com.avaliacao.dal.QuestaoDAO;
import br.com.avaliacao.model.Avaliacao;
import br.com.avaliacao.dal.AvaliacaoDAO;
import br.com.avaliacao.model.Resposta;
import br.com.avaliacao.dal.RespostaDAO;
import br.com.avaliacao.util.DBConnection;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class TestMain {

    public static void main(String[] args) {
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        QuestaoDAO questaoDAO = new QuestaoDAO();
        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
        RespostaDAO respostaDAO = new RespostaDAO();
        
        Usuario usuarioCadastrado = null;
        Integer idProfessorTeste = null;
        Integer idQuestaoTeste = null;
        Integer idAvaliacaoTeste = null; 
        Integer idAlunoTeste = null; 
        Integer idDisciplinaTeste = null;
        
        
        String uniqueId = String.valueOf(System.currentTimeMillis());

        try {
         
            Usuario novoProfessor = new Usuario(
                "Prof. Completo", 
                "prof.completo" + uniqueId + "@uni.com", 
                "hash_professor_123", 
                "PROFESSOR"
            );

            System.out.println("--- Teste 1: Cadastro Professor ---");
            Usuario professorCadastrado = usuarioDAO.cadastrar(novoProfessor);
            
            if (professorCadastrado.getIdUsuario() != null) {
                System.out.println("✅ SUCESSO! Professor cadastrado com ID: " + professorCadastrado.getIdUsuario());
                idProfessorTeste = professorCadastrado.getIdUsuario(); 
            } else {
                System.out.println("❌ FALHA no Cadastro do Professor.");
            }

            
            System.out.println("\n--- Teste 2: Busca (Login) ---");
            Usuario usuarioEncontrado = usuarioDAO.buscarPorEmail(novoProfessor.getEmail());
            
            if (usuarioEncontrado != null) {
                System.out.println("✅ SUCESSO na Busca! Professor encontrado: " + usuarioEncontrado.getNome() + " - ID: " + usuarioEncontrado.getIdUsuario());
            } else {
                System.out.println("❌ FALHA na Busca.");
            }

            
            Disciplina novaDisciplina = new Disciplina("Projeto Final BD", "CCPF" + uniqueId); 

            System.out.println("\n--- Teste 3: Cadastro de Disciplina ---");
            Disciplina disciplinaCadastrada = disciplinaDAO.cadastrar(novaDisciplina);

            if (disciplinaCadastrada.getIdDisciplina() != null) {
                System.out.println("✅ SUCESSO! Disciplina cadastrada com ID: " + disciplinaCadastrada.getIdDisciplina());
                idDisciplinaTeste = disciplinaCadastrada.getIdDisciplina(); // <--- CAPTURA DO ID
            } else {
                System.out.println("❌ FALHA no Cadastro da Disciplina.");
            }
            
           
            if (idProfessorTeste != null) {
                Questao novaQuestao = new Questao(
                    "O que é uma transação atômica?", 
                    "TEXTO_LIVRE", 
                    idProfessorTeste 
                );

                System.out.println("\n--- Teste 4: Cadastro de Questão ---");
                Questao questaoCadastrada = questaoDAO.cadastrar(novaQuestao);

                if (questaoCadastrada.getIdQuestao() != null) {
                    System.out.println("✅ SUCESSO! Questão cadastrada com ID: " + questaoCadastrada.getIdQuestao());
                    idQuestaoTeste = questaoCadastrada.getIdQuestao(); 
                } else {
                    System.out.println("❌ FALHA no Cadastro da Questão.");
                }
            } else {
                System.out.println("\n--- Teste 4: Cadastro de Questão ---");
                System.out.println("Skipped: Professor não cadastrado.");
            }

            
            Usuario novoAluno = new Usuario(
                "Aluno Teste Avaliacao", 
                "aluno.completo" + uniqueId + "@uni.com", 
                "hash_aluno_456", 
                "ALUNO"
            );
            System.out.println("\n--- Teste 5: Cadastro de Aluno ---");
            Usuario alunoCadastrado = usuarioDAO.cadastrar(novoAluno);
            if (alunoCadastrado.getIdUsuario() != null) {
                System.out.println("✅ SUCESSO! Aluno cadastrado com ID: " + alunoCadastrado.getIdUsuario());
                idAlunoTeste = alunoCadastrado.getIdUsuario();
            } else {
                System.out.println("❌ FALHA no Cadastro do Aluno.");
            }

            
            
            if (idProfessorTeste != null && idQuestaoTeste != null && idDisciplinaTeste != null) { 
                 
                List<Questao> questoesParaAvaliacao = Arrays.asList(
                    new Questao(idQuestaoTeste, null, null, null) 
                );
                
                
                Avaliacao novaAvaliacao = new Avaliacao(
                    "Avaliação Completa", 
                    LocalDateTime.now().plusDays(7), 
                    10.0, 
                    idProfessorTeste,
                    idDisciplinaTeste 
                );
                novaAvaliacao.setQuestoes(questoesParaAvaliacao);

                System.out.println("\n--- Teste 6: Cadastro de Avaliação e Vínculo ---");
                Avaliacao avaliacaoCadastrada = avaliacaoDAO.cadastrar(novaAvaliacao);
                if (avaliacaoCadastrada.getIdAvaliacao() != null) {
                    System.out.println("✅ SUCESSO! Avaliação cadastrada com ID: " + avaliacaoCadastrada.getIdAvaliacao());
                    idAvaliacaoTeste = avaliacaoCadastrada.getIdAvaliacao();
                } else {
                    System.out.println("❌ FALHA no Cadastro da Avaliação.");
                }
            } else {
                System.out.println("\n--- Teste 6: Cadastro de Avaliação ---");
                System.out.println("Skipped: Dependência (Professor, Questão ou Disciplina) não cadastrada.");
            }


           
            if (idAvaliacaoTeste != null && idQuestaoTeste != null && idAlunoTeste != null) {
                Resposta novaResposta = new Resposta(
                    idAvaliacaoTeste, 
                    idQuestaoTeste, 
                    idAlunoTeste, 
                    "É aquela que garante que todas as operações serão concluídas ou nenhuma delas será."
                );
                
                System.out.println("\n--- Teste 7: Registro de Resposta ---");
                Resposta respostaCadastrada = respostaDAO.cadastrar(novaResposta);
                
                if (respostaCadastrada.getIdResposta() != null) {
                    System.out.println("✅ SUCESSO! Resposta registrada com ID: " + respostaCadastrada.getIdResposta());
                } else {
                    System.out.println("❌ FALHA no Registro da Resposta.");
                }
            } else {
                 System.out.println("\n--- Teste 7: Registro de Resposta ---");
                 System.out.println("Skipped: Dependência (Avaliação, Questão ou Aluno) não cadastrada.");
            }

        } catch (SQLException e) {
            System.err.println("\n❌ ERRO FATAL de Banco de Dados: " + e.getMessage());
        }


        { 
           
             DBConnection.closeConnection(); 
        }
    }
}
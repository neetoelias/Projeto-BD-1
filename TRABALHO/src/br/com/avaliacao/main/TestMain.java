package br.com.avaliacao.main;

import br.com.avaliacao.model.Opcao;
import br.com.avaliacao.dal.OpcaoDAO;

import br.com.avaliacao.model.Usuario;
import br.com.avaliacao.dal.UsuarioDAO;
import br.com.avaliacao.model.Disciplina;
import br.com.avaliacao.dal.DisciplinaDAO;
import br.com.avaliacao.model.Questao;
import br.com.avaliacao.dal.QuestaoDAO;
import br.com.avaliacao.model.Avaliacao;
import br.com.avaliacao.dal.AvaliacaoDAO;
import br.com.avaliacao.model.RealizacaoAvaliacao;
import br.com.avaliacao.dal.RealizacaoAvaliacaoDAO;
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
        RealizacaoAvaliacaoDAO realizacaoDAO = new RealizacaoAvaliacaoDAO();
        OpcaoDAO opcaoDAO = new OpcaoDAO();

        Integer idProfessorTeste = null;
        Integer idDisciplinaTeste = null;
        Integer idAlunoTeste = null;
        Integer idAvaliacaoTeste = null;
        Integer idRealizacaoTeste = null;

        Integer idQuestaoDiscursiva = null;
        Integer idQuestaoMultiplaEscolha = null;
        Integer idOpcaoCorretaME = null;
        Integer idOpcaoIncorretaME = null;

        Integer idRealizacaoAluno2 = null;

        String uniqueId = String.valueOf(System.currentTimeMillis());

        try {

            // ----------------------------------------------------
            // TESTE 1: CADASTRO DE NOVO USUÁRIO (PROFESSOR)
            // ----------------------------------------------------
            Usuario novoProfessor = new Usuario("Prof. Anderson", "prof.anderson" + uniqueId + "@uel.com", "senha_professor_123", "PROFESSOR");
            System.out.println("--- Teste 1: Cadastro Professor ---");
            Usuario professorCadastrado = usuarioDAO.cadastrar(novoProfessor);
            idProfessorTeste = professorCadastrado.getIdUsuario();
            System.out.println("✅ SUCESSO! Professor cadastrado com ID: " + idProfessorTeste);

            // ----------------------------------------------------
            // TESTE 2: BUSCAR POR EMAIL (SIMULAR LOGIN)
            // ----------------------------------------------------
            System.out.println("\n--- Teste 2: Busca (Login) ---");
            Usuario usuarioEncontrado = usuarioDAO.buscarPorEmail(novoProfessor.getEmail());
            System.out.println("✅ SUCESSO na Busca! Professor encontrado: " + usuarioEncontrado.getNome());

            // ----------------------------------------------------
            // TESTE 3: CADASTRO DE DISCIPLINA
            // ----------------------------------------------------
            Disciplina novaDisciplina = new Disciplina("Banco de Dados", "COP" + uniqueId);
            System.out.println("\n--- Teste 3: Cadastro de Disciplina ---");
            Disciplina disciplinaCadastrada = disciplinaDAO.cadastrar(novaDisciplina);
            idDisciplinaTeste = disciplinaCadastrada.getIdDisciplina();
            System.out.println("✅ SUCESSO! Disciplina cadastrada com ID: " + idDisciplinaTeste);

            // ----------------------------------------------------
            // TESTE 4: CADASTRO DE QUESTÃO (TEXTO_LIVRE)
            // ----------------------------------------------------

            System.out.println("\n--- Teste 4: Cadastro de Questão Discursiva ---");
            Questao qDiscursiva = new Questao(
                    "O que é um DER",
                    "TEXTO_LIVRE",
                    idProfessorTeste
            );
            qDiscursiva.setGabaritoTexto("Diagrama de Entidade-Relacionamento, usado para modelar bancos de dados.");

            qDiscursiva = questaoDAO.cadastrar(qDiscursiva);
            idQuestaoDiscursiva = qDiscursiva.getIdQuestao();
            System.out.println("✅ SUCESSO! Questão Discursiva cadastrada com ID: " + idQuestaoDiscursiva);

            // ----------------------------------------------------
            // TESTE 4.5: CADASTRO DE QUESTÃO (MULTIPLA_ESCOLHA + OPÇÕES)
            // ----------------------------------------------------
            System.out.println("\n--- Teste 4.5: Cadastro de Questão Múltipla Escolha ---");
            Questao qME = new Questao(
                    "Qual programa podemos usar para administrar bancos de dados",
                    "MULTIPLA_ESCOLHA",
                    idProfessorTeste
            );

            qME = questaoDAO.cadastrar(qME);
            idQuestaoMultiplaEscolha = qME.getIdQuestao();
            System.out.println("✅ SUCESSO! Questão M.E. cadastrada com ID: " + idQuestaoMultiplaEscolha);

            Opcao op1 = new Opcao(idQuestaoMultiplaEscolha, "Steam", false);
            Opcao op2 = new Opcao(idQuestaoMultiplaEscolha, "Whatsapp", false);
            Opcao op3 = new Opcao(idQuestaoMultiplaEscolha, "pgAdmin", true);
            Opcao op4 = new Opcao(idQuestaoMultiplaEscolha, "Photoshop", false);

            op1 = opcaoDAO.cadastrar(op1);
            op2 = opcaoDAO.cadastrar(op2);
            op3 = opcaoDAO.cadastrar(op3);
            op4 = opcaoDAO.cadastrar(op4);

            idOpcaoCorretaME = op3.getIdOpcao();
            idOpcaoIncorretaME = op2.getIdOpcao();

            System.out.println("✅ SUCESSO! Opções e Gabarito cadastrados para a Questão M.E.");

            // ----------------------------------------------------
            // TESTE 5: CADASTRO DE NOVO USUÁRIO (ALUNO)
            // ----------------------------------------------------
            Usuario novoAluno = new Usuario("Rafael", "aluno.rafael" + uniqueId + "@uel.com", "senha_aluno_456", "ALUNO");
            System.out.println("\n--- Teste 5: Cadastro de Aluno ---");
            Usuario alunoCadastrado = usuarioDAO.cadastrar(novoAluno);
            idAlunoTeste = alunoCadastrado.getIdUsuario();
            System.out.println("✅ SUCESSO! Aluno cadastrado com ID: " + idAlunoTeste);

            // ----------------------------------------------------
            // TESTE 6: CADASTRO DE AVALIAÇÃO E VÍNCULO DE QUESTÕES
            // ----------------------------------------------------

            System.out.println("\n--- Teste 6: Cadastro de Avaliação e Vínculo ---");

            br.com.avaliacao.model.ItemAvaliacao item1 = new br.com.avaliacao.model.ItemAvaliacao(idQuestaoDiscursiva, 6.0);
            br.com.avaliacao.model.ItemAvaliacao item2 = new br.com.avaliacao.model.ItemAvaliacao(idQuestaoMultiplaEscolha, 4.0);

            List<br.com.avaliacao.model.ItemAvaliacao> itensParaAvaliacao = Arrays.asList(item1, item2);

            Avaliacao novaAvaliacao = new Avaliacao(
                    "Prova Final - Discursiva e M.E.",
                    LocalDateTime.now().plusDays(7),
                    10.0,
                    idProfessorTeste,
                    idDisciplinaTeste
            );
            novaAvaliacao.setItens(itensParaAvaliacao);

            Avaliacao avaliacaoCadastrada = avaliacaoDAO.cadastrar(novaAvaliacao);
            idAvaliacaoTeste = avaliacaoCadastrada.getIdAvaliacao();
            System.out.println("✅ SUCESSO! Avaliação cadastrada com ID: " + idAvaliacaoTeste);

            // ----------------------------------------------------
            // TESTE 6.5: REGISTRO DE REALIZAÇÃO - ALUNO 1
            // ----------------------------------------------------
            System.out.println("\n--- Teste 6.5: Registro de Realização (Aluno 1) ---");
            RealizacaoAvaliacao novaRealizacao = new RealizacaoAvaliacao(idAvaliacaoTeste, idAlunoTeste);
            RealizacaoAvaliacao realizacaoCadastrada = realizacaoDAO.cadastrar(novaRealizacao);
            idRealizacaoTeste = realizacaoCadastrada.getIdRealizacao();
            System.out.println("✅ SUCESSO! Realização (tentativa) registrada com ID: " + idRealizacaoTeste);

            // ----------------------------------------------------
            // TESTE 7: REGISTRO DE RESPOSTAS DO ALUNO 1
            // ----------------------------------------------------
            System.out.println("\n--- Teste 7: Registro de Respostas (Aluno 1) ---");

            Resposta respDiscursiva = new Resposta();
            respDiscursiva.setIdRealizacao(idRealizacaoTeste);
            respDiscursiva.setIdQuestao(idQuestaoDiscursiva);
            respDiscursiva.setRespostaTexto("É aquela que garante que todas as operações serão concluídas ou nenhuma delas será.");

            respostaDAO.cadastrar(respDiscursiva);
            System.out.println("✅ SUCESSO! Resposta Discursiva registrada.");

            Resposta respME = new Resposta();
            respME.setIdRealizacao(idRealizacaoTeste);
            respME.setIdQuestao(idQuestaoMultiplaEscolha);
            respME.setIdOpcaoEscolhida(idOpcaoCorretaME);

            respostaDAO.cadastrar(respME);
            System.out.println("✅ SUCESSO! Resposta Múltipla Escolha (Correta) registrada.");

            // =================================================================================
            // CENÁRIO EXTRA: POVOANDO O BANCO (Aluno 2)
            // =================================================================================
            System.out.println("\n=== 🚀 CENÁRIO EXTRA: Segundo Aluno realizando a prova ===");

            Usuario aluno2 = new Usuario("Elias", "aluno.elias" + uniqueId + "@uel.com", "senha_aluno_789", "ALUNO");
            Usuario aluno2Cadastrado = usuarioDAO.cadastrar(aluno2);
            Integer idAluno2 = aluno2Cadastrado.getIdUsuario();
            System.out.println("✅ [Extra] Aluno 2 cadastrado com ID: " + idAluno2);

            RealizacaoAvaliacao realizacao2 = new RealizacaoAvaliacao(idAvaliacaoTeste, idAluno2);
            realizacao2 = realizacaoDAO.cadastrar(realizacao2);
            idRealizacaoAluno2 = realizacao2.getIdRealizacao();
            System.out.println("✅ [Extra] Realização 2 registrada com ID: " + idRealizacaoAluno2);


            Resposta respDiscursiva2 = new Resposta();
            respDiscursiva2.setIdRealizacao(idRealizacaoAluno2);
            respDiscursiva2.setIdQuestao(idQuestaoDiscursiva);
            respDiscursiva2.setRespostaTexto("Diagrama entidade relacionamento");
            respostaDAO.cadastrar(respDiscursiva2);
            System.out.println("✅ [Extra] Resposta Discursiva 2 registrada.");

            Resposta respME2 = new Resposta();
            respME2.setIdRealizacao(idRealizacaoAluno2);
            respME2.setIdQuestao(idQuestaoMultiplaEscolha);
            respME2.setIdOpcaoEscolhida(idOpcaoIncorretaME);
            respostaDAO.cadastrar(respME2);
            System.out.println("✅ [Extra] Resposta Múltipla Escolha 2 registrada.");


        } catch (SQLException e) {
            System.err.println("\n❌ ERRO FATAL de Banco de Dados: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("\nConexão PostgreSQL fechada.");
            DBConnection.closeConnection();
        }
    }
}
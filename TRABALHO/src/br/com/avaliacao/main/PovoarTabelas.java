package br.com.avaliacao.main;

import br.com.avaliacao.dal.*;
import br.com.avaliacao.model.*;
import br.com.avaliacao.util.DBConnection;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class PovoarTabelas {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    private QuestaoDAO questaoDAO = new QuestaoDAO();
    private OpcaoDAO opcaoDAO = new OpcaoDAO();
    private AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
    private RealizacaoAvaliacaoDAO realizacaoDAO = new RealizacaoAvaliacaoDAO();
    private RespostaDAO respostaDAO = new RespostaDAO();

    private Usuario profSilva, profaAna;
    private Usuario alunoCarlos, alunaBeatriz, alunoDaniel;
    private Disciplina discBD, discEngSoft;
    private Questao q1_der, q2_admin, q3_scrum, q4_agil;
    private Avaliacao aval1_bd, aval2_engSoft;

    private Integer q2_opCorretaId, q2_opIncorretaId;
    private Integer q4_opCorretaId, q4_opIncorretaId;


    public static void main(String[] args) {
        System.out.println("--- 🚀 Iniciando povoamento do banco de dados ---");
        try {
            System.out.println("Conexão com o banco estabelecida.");

            PovoarTabelas script = new PovoarTabelas();
            script.criarUsuarios();
            script.criarDisciplinasEQuestoes();
            script.criarAvaliacoes();
            script.realizarProvas();

            System.out.println("\n--- ✅ POVOAMENTO CONCLUÍDO COM SUCESSO! ---");

        } catch (SQLException e) {
            System.err.println("\n--- ❌ ERRO FATAL DURANTE O POVOAMENTO ---");
            e.printStackTrace();
        }
    }

    public void criarUsuarios() throws SQLException {
        System.out.println("\n--- Cadastrando Usuários ---");

        profSilva = usuarioDAO.cadastrar(new Usuario("Dr. Silva", "silva@uni.com", "silva123", "PROFESSOR"));
        profaAna = usuarioDAO.cadastrar(new Usuario("Profa. Ana", "ana@uni.com", "ana123", "PROFESSOR"));
        System.out.println(" Professores cadastrados: " + profSilva.getIdUsuario() + ", " + profaAna.getIdUsuario());

        alunoCarlos = usuarioDAO.cadastrar(new Usuario("Carlos Souza", "carlos@aluno.com", "carlos123", "ALUNO"));
        alunaBeatriz = usuarioDAO.cadastrar(new Usuario("Beatriz Lima", "bia@aluno.com", "bia123", "ALUNO"));
        alunoDaniel = usuarioDAO.cadastrar(new Usuario("Daniel Alves", "daniel@aluno.com", "daniel123", "ALUNO"));
        System.out.println(" Alunos cadastrados: " + alunoCarlos.getIdUsuario() + ", " + alunaBeatriz.getIdUsuario() + ", " + alunoDaniel.getIdUsuario());
    }

    public void criarDisciplinasEQuestoes() throws SQLException {
        System.out.println("\n--- Cadastrando Disciplinas e Questões ---");

        discBD = disciplinaDAO.cadastrar(new Disciplina("Arquitetura de Computadores", "COP123"));
        discEngSoft = disciplinaDAO.cadastrar(new Disciplina("Engenharia de Software", "COP456"));

        q1_der = new Questao("O que é MIPS", "TEXTO_LIVRE", profSilva.getIdUsuario());
        q1_der.setGabaritoTexto("Microprocessor without Interlocked Pipelined Stages, é um tipo de arquitetura de processador do tipo RISC, que usa um conjunto de instruções simples e otimizadas");
        q1_der = questaoDAO.cadastrar(q1_der);
        System.out.println(" Questão Discursiva de BD cadastrada: " + q1_der.getIdQuestao());

        q2_admin = new Questao("Qual programa podemos usar para trabalhar com  programaçao MIPS", "MULTIPLA_ESCOLHA", profSilva.getIdUsuario());
        q2_admin = questaoDAO.cadastrar(q2_admin);

        Opcao q2_op1 = opcaoDAO.cadastrar(new Opcao(q2_admin.getIdQuestao(), "Spotify", false));
        Opcao q2_op2 = opcaoDAO.cadastrar(new Opcao(q2_admin.getIdQuestao(), "MARS", true));
        Opcao q2_op3 = opcaoDAO.cadastrar(new Opcao(q2_admin.getIdQuestao(), "League of Legends", false));
        q2_opCorretaId = q2_op2.getIdOpcao();
        q2_opIncorretaId = q2_op1.getIdOpcao();
        System.out.println(" Questão M.E. de BD cadastrada: " + q2_admin.getIdQuestao());


        q3_scrum = new Questao("O que é Scrum?", "TEXTO_LIVRE", profaAna.getIdUsuario());
        q3_scrum.setGabaritoTexto("Um framework ágil para gerenciamento de projetos.");
        q3_scrum = questaoDAO.cadastrar(q3_scrum);
        System.out.println(" Questão Discursiva de EngSoft cadastrada: " + q3_scrum.getIdQuestao());

        q4_agil = new Questao("Qual destes NÃO é um princípio do Manifesto Ágil?", "MULTIPLA_ESCOLHA", profaAna.getIdUsuario());
        q4_agil = questaoDAO.cadastrar(q4_agil);

        Opcao q4_op1 = opcaoDAO.cadastrar(new Opcao(q4_agil.getIdQuestao(), "Indivíduos e interações mais que processos", false));
        Opcao q4_op2 = opcaoDAO.cadastrar(new Opcao(q4_agil.getIdQuestao(), "Software em funcionamento mais que documentação", false));
        Opcao q4_op3 = opcaoDAO.cadastrar(new Opcao(q4_agil.getIdQuestao(), "Seguir o plano rigorosamente mais que responder a mudanças", true));

        q4_opCorretaId = q4_op3.getIdOpcao();
        q4_opIncorretaId = q4_op1.getIdOpcao();
        System.out.println(" Questão M.E. de EngSoft cadastrada: " + q4_agil.getIdQuestao());
    }

    public void criarAvaliacoes() throws SQLException {
        System.out.println("\n--- Cadastrando Avaliações ---");

        ItemAvaliacao item1_1 = new ItemAvaliacao(q1_der.getIdQuestao(), 6.0);
        ItemAvaliacao item1_2 = new ItemAvaliacao(q2_admin.getIdQuestao(), 4.0);
        List<ItemAvaliacao> itensAv1 = Arrays.asList(item1_1, item1_2);

        aval1_bd = new Avaliacao("Prova 1 de Banco de Dados", LocalDateTime.now().plusDays(5), 10.0, profSilva.getIdUsuario(), discBD.getIdDisciplina());
        aval1_bd.setItens(itensAv1);
        aval1_bd = avaliacaoDAO.cadastrar(aval1_bd);
        System.out.println(" Avaliação de BD cadastrada (ID: " + aval1_bd.getIdAvaliacao() + ") com 2 questões.");

        ItemAvaliacao item2_1 = new ItemAvaliacao(q3_scrum.getIdQuestao(), 5.0);
        ItemAvaliacao item2_2 = new ItemAvaliacao(q4_agil.getIdQuestao(), 5.0);
        List<ItemAvaliacao> itensAv2 = Arrays.asList(item2_1, item2_2);

        aval2_engSoft = new Avaliacao("Prova 1 de Eng. de Software", LocalDateTime.now().plusDays(7), 10.0, profaAna.getIdUsuario(), discEngSoft.getIdDisciplina());
        aval2_engSoft.setItens(itensAv2);
        aval2_engSoft = avaliacaoDAO.cadastrar(aval2_engSoft);
        System.out.println(" Avaliação de EngSoft cadastrada (ID: " + aval2_engSoft.getIdAvaliacao() + ") com 2 questões.");
    }

    public void realizarProvas() throws SQLException {
        System.out.println("\n--- Simulando Realização de Provas ---");

        System.out.println(" Aluno Carlos (ID: "+alunoCarlos.getIdUsuario()+") está fazendo a Prova de BD...");
        RealizacaoAvaliacao r1 = realizacaoDAO.cadastrar(new RealizacaoAvaliacao(aval1_bd.getIdAvaliacao(), alunoCarlos.getIdUsuario()));

        Resposta r1_q1 = new Resposta();
        r1_q1.setIdRealizacao(r1.getIdRealizacao());
        r1_q1.setIdQuestao(q1_der.getIdQuestao());
        r1_q1.setRespostaTexto("É um tipo de código");
        respostaDAO.cadastrar(r1_q1);

        Resposta r1_q2 = new Resposta();
        r1_q2.setIdRealizacao(r1.getIdRealizacao());
        r1_q2.setIdQuestao(q2_admin.getIdQuestao());
        r1_q2.setIdOpcaoEscolhida(q2_opCorretaId);
        respostaDAO.cadastrar(r1_q2);
        System.out.println("  -> Carlos terminou. (Realização ID: " + r1.getIdRealizacao() + ")");

        System.out.println(" Aluna Beatriz (ID: "+alunaBeatriz.getIdUsuario()+") está fazendo a Prova de BD...");
        RealizacaoAvaliacao r2 = realizacaoDAO.cadastrar(new RealizacaoAvaliacao(aval1_bd.getIdAvaliacao(), alunaBeatriz.getIdUsuario()));

        Resposta r2_q1 = new Resposta();
        r2_q1.setIdRealizacao(r2.getIdRealizacao());
        r2_q1.setIdQuestao(q1_der.getIdQuestao());
        r2_q1.setRespostaTexto("Microprocessor without Interlocked Pipelined Stages");
        respostaDAO.cadastrar(r2_q1);

        Resposta r2_q2 = new Resposta();
        r2_q2.setIdRealizacao(r2.getIdRealizacao());
        r2_q2.setIdQuestao(q2_admin.getIdQuestao());
        r2_q2.setIdOpcaoEscolhida(q2_opIncorretaId);
        respostaDAO.cadastrar(r2_q2);
        System.out.println("  -> Beatriz terminou. (Realização ID: " + r2.getIdRealizacao() + ")");

        System.out.println(" Aluna Beatriz (ID: "+alunaBeatriz.getIdUsuario()+") está fazendo a Prova de EngSoft...");
        RealizacaoAvaliacao r3 = realizacaoDAO.cadastrar(new RealizacaoAvaliacao(aval2_engSoft.getIdAvaliacao(), alunaBeatriz.getIdUsuario()));

        Resposta r3_q1 = new Resposta();
        r3_q1.setIdRealizacao(r3.getIdRealizacao());
        r3_q1.setIdQuestao(q3_scrum.getIdQuestao());
        r3_q1.setRespostaTexto("Um tipo de metodologia ágil.");
        respostaDAO.cadastrar(r3_q1);

        Resposta r3_q2 = new Resposta();
        r3_q2.setIdRealizacao(r3.getIdRealizacao());
        r3_q2.setIdQuestao(q4_agil.getIdQuestao());
        r3_q2.setIdOpcaoEscolhida(q4_opCorretaId);
        respostaDAO.cadastrar(r3_q2);
        System.out.println("  -> Beatriz terminou. (Realização ID: " + r3.getIdRealizacao() + ")");

        System.out.println(" Aluno Daniel (ID: "+alunoDaniel.getIdUsuario()+") está fazendo a Prova de EngSoft...");
        RealizacaoAvaliacao r4 = realizacaoDAO.cadastrar(new RealizacaoAvaliacao(aval2_engSoft.getIdAvaliacao(), alunoDaniel.getIdUsuario()));

        Resposta r4_q1 = new Resposta();
        r4_q1.setIdRealizacao(r4.getIdRealizacao());
        r4_q1.setIdQuestao(q3_scrum.getIdQuestao());
        r4_q1.setRespostaTexto("Não sei, acho que é de comer.");
        respostaDAO.cadastrar(r4_q1);

        Resposta r4_q2 = new Resposta();
        r4_q2.setIdRealizacao(r4.getIdRealizacao());
        r4_q2.setIdQuestao(q4_agil.getIdQuestao());
        r4_q2.setIdOpcaoEscolhida(q4_opIncorretaId);
        respostaDAO.cadastrar(r4_q2);
        System.out.println("  -> Daniel terminou. (Realização ID: " + r4.getIdRealizacao() + ")");
    }
}
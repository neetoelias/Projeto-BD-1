package br.com.avaliacao.controller;

import br.com.avaliacao.dal.OpcaoDAO;
import br.com.avaliacao.dal.QuestaoDAO;
import br.com.avaliacao.model.Opcao;
import br.com.avaliacao.model.Questao;
import br.com.avaliacao.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/criar-questao")
public class QuestaoServlet extends HttpServlet {

    private QuestaoDAO questaoDAO = new QuestaoDAO();
    private OpcaoDAO opcaoDAO = new OpcaoDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Usuario professor = (Usuario) req.getSession().getAttribute("usuarioLogado");

        try {
            
            String enunciado = req.getParameter("enunciado");
            String tipo = req.getParameter("tipo");

           
            Integer idDisciplina = Integer.parseInt(req.getParameter("idDisciplina"));

            
            Questao questao = new Questao(enunciado, tipo, professor.getIdUsuario(), idDisciplina);

            if ("TEXTO_LIVRE".equals(tipo)) {
               
                questao.setGabaritoTexto(req.getParameter("gabaritoTexto"));
                questaoDAO.cadastrar(questao);

            } else {
            
                questao = questaoDAO.cadastrar(questao);

                
                String[] opcoesTexto = req.getParameterValues("opcaoTexto");
                String indexCorreta = req.getParameter("opcaoCorreta");

                if (opcoesTexto != null) {
                    for (int i = 0; i < opcoesTexto.length; i++) {
                        boolean ehCorreta = String.valueOf(i).equals(indexCorreta);
                        Opcao op = new Opcao(questao.getIdQuestao(), opcoesTexto[i], ehCorreta);
                        opcaoDAO.cadastrar(op);
                    }
                }
            }
            resp.sendRedirect("dashboard-professor.jsp?msg=Questao+criada+com+sucesso");

        } catch (SQLException e) {
            throw new ServletException("Erro de banco de dados ao criar questão", e);
        } catch (NumberFormatException e) {
            throw new ServletException("ID da disciplina inválido ou não selecionado", e);
        }
    }
}
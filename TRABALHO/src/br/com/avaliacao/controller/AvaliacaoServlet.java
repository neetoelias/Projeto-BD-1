package br.com.avaliacao.controller;

import br.com.avaliacao.dal.AvaliacaoDAO;
import br.com.avaliacao.model.Avaliacao;
import br.com.avaliacao.model.ItemAvaliacao;
import br.com.avaliacao.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/criar-avaliacao")
public class AvaliacaoServlet extends HttpServlet {

    private AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Usuario professor = (Usuario) req.getSession().getAttribute("usuarioLogado");

        try {
            
            String titulo = req.getParameter("titulo");
            Integer idDisciplina = Integer.parseInt(req.getParameter("idDisciplina"));
            
            LocalDate dataFim = LocalDate.parse(req.getParameter("dataLimite"));

            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setTitulo(titulo);
            avaliacao.setIdDisciplina(idDisciplina);
            avaliacao.setIdProfessor(professor.getIdUsuario());
            avaliacao.setDataLimite(dataFim.atStartOfDay()); 

            
            String[] idsQuestoesSelecionadas = req.getParameterValues("idsQuestoes");
            List<ItemAvaliacao> itens = new ArrayList<>();
            double valorTotal = 0;

            if (idsQuestoesSelecionadas != null) {
                for (String idString : idsQuestoesSelecionadas) {
                    Integer idQuestao = Integer.parseInt(idString);
                    
                    String pesoString = req.getParameter("peso_" + idQuestao);
                    Double peso = Double.parseDouble(pesoString);

                    ItemAvaliacao item = new ItemAvaliacao(idQuestao, peso);
                    itens.add(item);
                    valorTotal += peso;
                }
            }

            avaliacao.setValorTotal(valorTotal);
            avaliacao.setItens(itens);

            
            avaliacaoDAO.cadastrar(avaliacao);
            resp.sendRedirect("dashboard-professor.jsp?msg=Prova+criada+com+sucesso");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
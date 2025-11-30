package br.com.avaliacao.controller;

import br.com.avaliacao.dal.RespostaDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/salvar-correcao")
public class CorrecaoServlet extends HttpServlet {

    private RespostaDAO respostaDAO = new RespostaDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Enumeration<String> params = req.getParameterNames();

            while (params.hasMoreElements()) {
                String paramName = params.nextElement();

                if (paramName.startsWith("nota_")) {
                    String idString = paramName.substring(5);
                    Integer idResposta = Integer.parseInt(idString);
                    String notaString = req.getParameter(paramName);

                    if (notaString != null && !notaString.isEmpty()) {
                        Double nota = Double.parseDouble(notaString);
                        respostaDAO.atualizarNota(idResposta, nota);
                    }
                }
            }
            resp.sendRedirect("dashboard-professor.jsp?msg=Notas+atualizadas+com+sucesso!");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
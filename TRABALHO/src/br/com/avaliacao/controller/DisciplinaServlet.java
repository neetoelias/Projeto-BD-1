package br.com.avaliacao.controller;

import br.com.avaliacao.dal.DisciplinaDAO;
import br.com.avaliacao.model.Disciplina;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/criar-disciplina")
public class DisciplinaServlet extends HttpServlet {

    private DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       
        req.setCharacterEncoding("UTF-8");

        try {
            String nome = req.getParameter("nome");
            String codigo = req.getParameter("codigo");

            
            Disciplina disciplina = new Disciplina(nome, codigo);

            
            disciplinaDAO.cadastrar(disciplina);

            
            resp.sendRedirect("dashboard-professor.jsp?msg=Disciplina+cadastrada+com+sucesso!");

        } catch (SQLException e) {
            throw new ServletException("Erro ao cadastrar disciplina", e);
        }
    }
}
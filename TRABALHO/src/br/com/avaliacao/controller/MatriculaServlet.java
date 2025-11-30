package br.com.avaliacao.controller;

import br.com.avaliacao.dal.MatriculaDAO;
import br.com.avaliacao.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/gerenciar-matricula")
public class MatriculaServlet extends HttpServlet {

    private MatriculaDAO matriculaDAO = new MatriculaDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario aluno = (Usuario) req.getSession().getAttribute("usuarioLogado");

        String acao = req.getParameter("acao"); 
        Integer idDisciplina = Integer.parseInt(req.getParameter("idDisciplina"));

        try {
            if ("inscrever".equals(acao)) {
                matriculaDAO.matricular(aluno.getIdUsuario(), idDisciplina);
                resp.sendRedirect("painel-matricula.jsp?msg=Matricula+realizada!");
            } else if ("remover".equals(acao)) {
                matriculaDAO.cancelarMatricula(aluno.getIdUsuario(), idDisciplina);
                resp.sendRedirect("painel-matricula.jsp?msg=Matricula+cancelada.");
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao processar matrícula", e);
        }
    }
}
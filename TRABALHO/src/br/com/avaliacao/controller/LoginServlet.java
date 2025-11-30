package br.com.avaliacao.controller;

import br.com.avaliacao.dal.UsuarioDAO;
import br.com.avaliacao.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/auth")
public class LoginServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String senha = req.getParameter("senha"); 

        try {
            Usuario usuario = usuarioDAO.buscarPorEmail(email);

          
            if (usuario != null && usuario.getSenhaHash().equals(senha)) {

                HttpSession session = req.getSession();
                session.setAttribute("usuarioLogado", usuario);

                if ("PROFESSOR".equalsIgnoreCase(usuario.getTipoUsuario()) || "ADMIN".equalsIgnoreCase(usuario.getTipoUsuario())) {
                    resp.sendRedirect("dashboard-professor.jsp");
                } else {
                    resp.sendRedirect("dashboard-aluno.jsp");
                }
            } else {
               
                req.setAttribute("erro", "Email ou senha incorretos!");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException("Erro de banco de dados", e);
        }
    }
}
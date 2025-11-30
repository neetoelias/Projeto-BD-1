package br.com.avaliacao.controller;

import br.com.avaliacao.dal.UsuarioDAO;
import br.com.avaliacao.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/criar-usuario")
public class CadastroServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String nome = req.getParameter("nome");
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");
        String tipo = req.getParameter("tipoUsuario");

       
        Usuario novoUsuario = new Usuario(nome, email, senha, tipo);

        try {
            
            if (usuarioDAO.buscarPorEmail(email) != null) {
            
                resp.sendRedirect("cadastro.jsp?erro=Email+ja+existe");
                return;
            }

            usuarioDAO.cadastrar(novoUsuario);

            
            resp.sendRedirect("login.jsp?msg=Cadastro+realizado+com+sucesso");

        } catch (SQLException e) {
            throw new ServletException("Erro ao cadastrar usuário", e);
        }
    }
}
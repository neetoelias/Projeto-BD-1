package br.com.avaliacao.controller;

import br.com.avaliacao.dal.RealizacaoAvaliacaoDAO;
import br.com.avaliacao.dal.RespostaDAO;
import br.com.avaliacao.model.RealizacaoAvaliacao;
import br.com.avaliacao.model.Resposta;
import br.com.avaliacao.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/finalizar-prova")
public class FinalizarProvaServlet extends HttpServlet {

    private RealizacaoAvaliacaoDAO realizacaoDAO = new RealizacaoAvaliacaoDAO();
    private RespostaDAO respostaDAO = new RespostaDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Usuario aluno = (Usuario) req.getSession().getAttribute("usuarioLogado");
        Integer idAvaliacao = Integer.parseInt(req.getParameter("idAvaliacao"));

        try {
            
            RealizacaoAvaliacao realizacao = new RealizacaoAvaliacao(idAvaliacao, aluno.getIdUsuario());
            realizacao = realizacaoDAO.cadastrar(realizacao); 

            
            Enumeration<String> params = req.getParameterNames();
            while (params.hasMoreElements()) {
                String paramName = params.nextElement();

               
                if (paramName.startsWith("resposta_")) {
                    Integer idQuestao = Integer.parseInt(paramName.substring(9));
                    String valor = req.getParameter(paramName);

                    Resposta resposta = new Resposta();
                    resposta.setIdRealizacao(realizacao.getIdRealizacao());
                    resposta.setIdQuestao(idQuestao);

                    
                    if (valor.matches("\\d+")) {
                        
                        resposta.setIdOpcaoEscolhida(Integer.parseInt(valor));
                    } else {
                        
                        resposta.setRespostaTexto(valor);
                    }

                    respostaDAO.cadastrar(resposta);
                }
            }

            resp.sendRedirect("dashboard-aluno.jsp?msg=Prova+entregue+com+sucesso!");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
<%@ page import="br.com.avaliacao.model.Usuario" %>
<%@ page import="br.com.avaliacao.dal.QuestaoDAO" %>
<%@ page import="br.com.avaliacao.model.Questao" %>
<%@ page import="br.com.avaliacao.model.Opcao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Usuario aluno = (Usuario) session.getAttribute("usuarioLogado");
    String idAvaliacaoStr = request.getParameter("idAvaliacao");

    if (aluno == null || idAvaliacaoStr == null) {
        response.sendRedirect("dashboard-aluno.jsp");
        return;
    }

    QuestaoDAO questaoDAO = new QuestaoDAO();
    List<Questao> questoes = questaoDAO.listarPorAvaliacao(Integer.parseInt(idAvaliacaoStr));
%>
<html>
<head>
    <title>Realizando Prova</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5 mb-5">
    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <h4>📝 Respondendo Avaliação</h4>
        </div>
        <div class="card-body">
            <form action="finalizar-prova" method="post">
                <input type="hidden" name="idAvaliacao" value="<%= idAvaliacaoStr %>">

                <% for (int i = 0; i < questoes.size(); i++) {
                    Questao q = questoes.get(i);
                %>
                <div class="card mb-4 border-0">
                    <div class="card-body">
                        <h5 class="card-title">Questão <%= i + 1 %></h5>
                        <p class="card-text fs-5"><%= q.getDescricao() %></p>

                        <% if ("TEXTO_LIVRE".equals(q.getTipoQuestao())) { %>
                        <textarea name="resposta_<%= q.getIdQuestao() %>" class="form-control" rows="3" placeholder="Digite sua resposta aqui..." required></textarea>
                        <% } else { %>
                        <div class="list-group">
                            <% for (Opcao op : q.getOpcoes()) { %>
                            <label class="list-group-item">
                                <input class="form-check-input me-1" type="radio" name="resposta_<%= q.getIdQuestao() %>" value="<%= op.getIdOpcao() %>" required>
                                <%= op.getDescricaoOpcao() %>
                            </label>
                            <% } %>
                        </div>
                        <% } %>
                    </div>
                </div>
                <hr>
                <% } %>

                <div class="d-grid">
                    <button type="submit" class="btn btn-success btn-lg">Finalizar e Entregar Prova</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
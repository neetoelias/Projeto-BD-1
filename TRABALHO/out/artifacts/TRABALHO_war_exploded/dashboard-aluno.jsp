<%@ page import="br.com.avaliacao.model.Usuario" %>
<%@ page import="br.com.avaliacao.dal.AvaliacaoDAO" %>
<%@ page import="br.com.avaliacao.model.dto.AvaliacaoDisponivelDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Usuario aluno = (Usuario) session.getAttribute("usuarioLogado");
    if (aluno == null || !"ALUNO".equals(aluno.getTipoUsuario())) {
        response.sendRedirect("login.jsp");
        return;
    }

    AvaliacaoDAO dao = new AvaliacaoDAO();
    List<AvaliacaoDisponivelDTO> provas = dao.listarParaAluno(aluno.getIdUsuario());
%>
<html>
<head>
    <title>Área do Aluno</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-dark bg-primary p-3">
    <span class="navbar-brand mb-0 h1">🎓 Área do Aluno: <%= aluno.getNome() %></span>
    <a href="login.jsp" class="btn btn-light btn-sm">Sair</a>
</nav>

<div class="container mt-5">
    <div class="row">
        <div class="col-md-12">
            <div class="card shadow-sm">

                <div class="card-header bg-white d-flex justify-content-between align-items-center">
                    <h4 class="mb-0">Minhas Provas</h4>
                    <div>
                        <a href="painel-matricula.jsp" class="btn btn-primary me-2">📚 Matrículas</a>
                        <a href="boletim-aluno.jsp" class="btn btn-success">📊 Ver Meu Boletim</a>
                    </div>
                </div>

                <div class="card-body">
                    <div class="list-group">
                        <% for (AvaliacaoDisponivelDTO prova : provas) { %>
                        <div class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                            <div>
                                <h5 class="mb-1"><%= prova.getTitulo() %></h5>
                                <small class="text-muted">
                                    Disciplina: <strong><%= prova.getNomeDisciplina() %></strong> |
                                    Data Limite: <%= prova.getDataLimite().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) %>
                                </small>
                            </div>

                            <% if (prova.isRealizada()) { %>
                            <button class="btn btn-secondary btn-sm" disabled>Concluída ✅</button>
                            <% } else { %>
                            <a href="fazer-prova.jsp?idAvaliacao=<%= prova.getIdAvaliacao() %>" class="btn btn-primary btn-sm">Iniciar Prova 📝</a>
                            <% } %>
                        </div>
                        <% } %>

                        <% if (provas.isEmpty()) { %>
                        <p class="text-center mt-3">Nenhuma prova disponível. Verifique suas <a href="painel-matricula.jsp">matrículas</a>.</p>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<%@ page import="br.com.avaliacao.model.Usuario" %>
<%@ page import="br.com.avaliacao.dal.MatriculaDAO" %>
<%@ page import="br.com.avaliacao.model.Disciplina" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Usuario aluno = (Usuario) session.getAttribute("usuarioLogado");
    if (aluno == null || !"ALUNO".equals(aluno.getTipoUsuario())) {
        response.sendRedirect("login.jsp");
        return;
    }

    MatriculaDAO dao = new MatriculaDAO();
    // Carrega as duas listas
    List<Disciplina> disponiveis = dao.listarDisponiveis(aluno.getIdUsuario());
    List<Disciplina> minhas = dao.listarMatriculadas(aluno.getIdUsuario());
%>
<html>
<head>
    <title>Gerenciar Matrículas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-dark bg-primary p-3">
    <span class="navbar-brand mb-0 h1">Gerenciar Matrículas</span>
    <a href="dashboard-aluno.jsp" class="btn btn-light btn-sm">Voltar ao Dashboard</a>
</nav>

<div class="container mt-5">
    <div class="row">

        <div class="col-md-6">
            <div class="card shadow border-primary">
                <div class="card-header bg-primary text-white">
                    <h5>📚 Matérias Disponíveis</h5>
                </div>
                <div class="card-body">
                    <% if (disponiveis.isEmpty()) { %>
                    <p class="text-muted">Nenhuma nova matéria disponível.</p>
                    <% } else { %>
                    <ul class="list-group">
                        <% for (Disciplina d : disponiveis) { %>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <div>
                                <strong><%= d.getNome() %></strong><br>
                                <small class="text-muted"><%= d.getCodigo() %></small>
                            </div>
                            <form action="gerenciar-matricula" method="post" class="m-0">
                                <input type="hidden" name="idDisciplina" value="<%= d.getIdDisciplina() %>">
                                <input type="hidden" name="acao" value="inscrever">
                                <button type="submit" class="btn btn-sm btn-outline-primary">Inscrever</button>
                            </form>
                        </li>
                        <% } %>
                    </ul>
                    <% } %>
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="card shadow border-success">
                <div class="card-header bg-success text-white">
                    <h5>✅ Minhas Matrículas</h5>
                </div>
                <div class="card-body">
                    <% if (minhas.isEmpty()) { %>
                    <p class="text-muted">Você não está matriculado em nada.</p>
                    <% } else { %>
                    <ul class="list-group">
                        <% for (Disciplina d : minhas) { %>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <div>
                                <strong><%= d.getNome() %></strong><br>
                                <small class="text-muted"><%= d.getCodigo() %></small>
                            </div>
                            <form action="gerenciar-matricula" method="post" class="m-0">
                                <input type="hidden" name="idDisciplina" value="<%= d.getIdDisciplina() %>">
                                <input type="hidden" name="acao" value="remover">
                                <button type="submit" class="btn btn-sm btn-outline-danger">Sair</button>
                            </form>
                        </li>
                        <% } %>
                    </ul>
                    <% } %>
                </div>
            </div>
        </div>

    </div>
</div>
</body>
</html>
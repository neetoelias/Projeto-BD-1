<%@ page import="br.com.avaliacao.model.Usuario" %>
<%@ page import="br.com.avaliacao.dal.RelatorioDAO" %>
<%@ page import="br.com.avaliacao.model.dto.RelatorioRealizacao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if (usuario == null || !"PROFESSOR".equals(usuario.getTipoUsuario())) {
        response.sendRedirect("login.jsp");
        return;
    }

    
    RelatorioDAO relatorioDAO = new RelatorioDAO();
    List<RelatorioRealizacao> relatorio = relatorioDAO.buscarRealizacoesPorProfessor(usuario.getIdUsuario());
%>

<html>
<head>
    <title>Painel do Professor</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-dark bg-dark p-3">
    <span class="navbar-brand mb-0 h1">Área do Professor: <%= usuario.getNome() %></span>
    <a href="login.jsp" class="btn btn-outline-light btn-sm">Sair</a>
</nav>

<div class="container mt-5">
    <div class="row">
        <div class="col-md-4">
            <div class="card text-white bg-primary mb-3">
                <div class="card-body">
                    <h5 class="card-title">Criar Questão</h5>
                    <p class="card-text">Cadastre novas questões no banco.</p>
                    <a href="#" class="btn btn-light">Acessar (Em breve)</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card text-white bg-success mb-3">
                <div class="card-body">
                    <h5 class="card-title">Criar Avaliação</h5>
                    <p class="card-text">Monte uma prova com questões existentes.</p>
                    <a href="#" class="btn btn-light">Acessar (Em breve)</a>
                </div>
            </div>
        </div>
    </div>

    <h3 class="mt-4 border-bottom pb-2">📊 Relatório: Provas Realizadas</h3>

    <table class="table table-striped mt-3">
        <thead class="table-dark">
        <tr>
            <th>Aluno</th>
            <th>Prova</th>
            <th>Data de Início</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <% for(RelatorioRealizacao item : relatorio) { %>
        <tr>
            <td><%= item.getNomeAluno() %></td>
            <td><%= item.getTituloAvaliacao() %></td>
            <td><%= item.getDataInicio() %></td>
            <td><span class="badge bg-warning text-dark">Aguardando Correção</span></td>
        </tr>
        <% } %>

        <% if(relatorio.isEmpty()) { %>
        <tr><td colspan="4" class="text-center">Nenhuma prova realizada ainda.</td></tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
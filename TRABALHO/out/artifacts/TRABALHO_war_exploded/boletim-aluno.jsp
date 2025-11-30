<%@ page import="br.com.avaliacao.model.Usuario" %>
<%@ page import="br.com.avaliacao.dal.RelatorioDAO" %>
<%@ page import="br.com.avaliacao.model.dto.RelatorioAlunoDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Usuario aluno = (Usuario) session.getAttribute("usuarioLogado");
    if (aluno == null || !"ALUNO".equals(aluno.getTipoUsuario())) {
        response.sendRedirect("login.jsp");
        return;
    }

    RelatorioDAO dao = new RelatorioDAO();
    List<RelatorioAlunoDTO> boletim = dao.gerarBoletimAluno(aluno.getIdUsuario());
    DecimalFormat df = new DecimalFormat("#.0");
%>
<html>
<head>
    <title>Meu Boletim</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Estilo para imitar a imagem 2 */
        .tabela-boletim {
            width: 100%;
            border-collapse: collapse;
            border: 2px solid #000;
            text-align: center;
        }
        .tabela-boletim th {
            background-color: #cccccc; /* Cinza mais escuro no header */
            border: 1px solid #000;
            padding: 15px;
            text-transform: uppercase;
            font-weight: bold;
        }
        .tabela-boletim td {
            background-color: #e6e6e6; /* Cinza claro nas celulas */
            border: 1px solid #000;
            padding: 20px;
            font-size: 1.1rem;
        }
    </style>
</head>
<body class="bg-light p-5">
<div class="container bg-white p-4 shadow rounded">

    <h3 class="text-center mb-4">BOLETIM DE NOTAS - <%= aluno.getNome().toUpperCase() %></h3>

    <% if (boletim.isEmpty()) { %>
    <div class="alert alert-info text-center">Você ainda não possui notas lançadas.</div>
    <% } else { %>
    <table class="tabela-boletim">
        <thead>
        <tr>
            <th>AVALIAÇÃO</th>
            <th>SUA NOTA</th>
            <th>MÉDIA DA TURMA</th>
            <th>POSIÇÃO NA TURMA</th>
        </tr>
        </thead>
        <tbody>
        <% for (RelatorioAlunoDTO item : boletim) { %>
        <tr>
            <td><%= item.getNomeAvaliacao() %></td>
            <td><%= df.format(item.getSuaNota()) %></td>
            <td><%= df.format(item.getMediaTurma()) %></td>
            <td><%= item.getPosicaoTurma() %>º</td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>

    <div class="text-center mt-4">
        <a href="dashboard-aluno.jsp" class="btn btn-dark">Voltar</a>
    </div>
</div>
</body>
</html>
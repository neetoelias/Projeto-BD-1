<%@ page import="br.com.avaliacao.dal.RelatorioDAO" %>
<%@ page import="br.com.avaliacao.model.dto.RelatorioProfessorDTO" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String idStr = request.getParameter("idAvaliacao");
    if(idStr == null) { response.sendRedirect("dashboard-professor.jsp"); return; }

    RelatorioDAO dao = new RelatorioDAO();
    RelatorioProfessorDTO dados = dao.gerarEstatisticasProva(Integer.parseInt(idStr));
    DecimalFormat df = new DecimalFormat("#.0");
%>
<html>
<head>
    <title>Estatísticas da Prova</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        
        .tabela-ficha {
            width: 100%;
            border: 2px solid #000;
            text-align: center;
            font-family: Arial, sans-serif;
        }
        .tabela-ficha td {
            border: 1px solid #000;
            padding: 15px;
            font-size: 1.1rem;
            font-weight: bold;
            background-color: #e0e0e0; 
        }
        .coluna-label {
            width: 40%;
            text-transform: uppercase;
        }
    </style>
</head>
<body class="bg-light p-5">
<div class="container bg-white p-4 shadow rounded" style="max-width: 600px;">

    <h3 class="text-center mb-4">RELATÓRIO DA AVALIAÇÃO</h3>

    <table class="tabela-ficha">
        <tr>
            <td class="coluna-label">MATÉRIA</td>
            <td><%= dados.getNomeDisciplina().toUpperCase() %></td>
        </tr>
        <tr>
            <td class="coluna-label">MÉDIA GERAL</td>
            <td><%= df.format(dados.getMediaGeral()) %></td>
        </tr>
        <tr>
            <td class="coluna-label">MEDIANA</td>
            <td><%= df.format(dados.getMediana()) %></td>
        </tr>
        <tr>
            <td class="coluna-label">MAIOR NOTA</td>
            <td><%= df.format(dados.getMaiorNota()) %></td>
        </tr>
        <tr>
            <td class="coluna-label">MENOR NOTA</td>
            <td><%= df.format(dados.getMenorNota()) %></td>
        </tr>
    </table>

    <div class="text-center mt-4">
        <a href="dashboard-professor.jsp" class="btn btn-dark">Voltar</a>
    </div>
</div>
</body>
</html>
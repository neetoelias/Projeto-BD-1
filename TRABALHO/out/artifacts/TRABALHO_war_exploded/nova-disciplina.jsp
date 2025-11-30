<%@ page import="br.com.avaliacao.model.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Segurança: Apenas professor acessa
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if (usuario == null || !"PROFESSOR".equals(usuario.getTipoUsuario())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
<head>
    <title>Nova Disciplina</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="card shadow" style="max-width: 600px; margin: auto;">
        <div class="card-header bg-dark text-white">
            <h4>📚 Nova Disciplina</h4>
        </div>
        <div class="card-body">
            <form action="criar-disciplina" method="post">

                <div class="mb-3">
                    <label class="form-label">Nome da Disciplina</label>
                    <input type="text" name="nome" class="form-control" placeholder="Ex: Estrutura de Dados" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Código da Disciplina</label>
                    <input type="text" name="codigo" class="form-control" placeholder="Ex: CCED123" required>
                </div>

                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-success">Salvar Disciplina</button>
                    <a href="dashboard-professor.jsp" class="btn btn-secondary">Cancelar</a>
                </div>

            </form>
        </div>
    </div>
</div>
</body>
</html>
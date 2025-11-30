<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login - Sistema de Avaliação</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center justify-content-center" style="height: 100vh;">

<div class="card p-4 shadow" style="width: 350px;">
    <h3 class="text-center mb-4">Login</h3>

    <% if (request.getAttribute("erro") != null) { %>
    <div class="alert alert-danger text-center" role="alert">
        <%= request.getAttribute("erro") %>
    </div>
    <% } %>

    <form action="auth" method="post">
        <div class="mb-3">
            <label>Email</label>
            <input type="email" name="email" class="form-control" placeholder="ex: aluno@uel.br" required>
        </div>
        <div class="mb-3">
            <label>Senha</label>
            <input type="password" name="senha" class="form-control" placeholder="******" required>
        </div>

        <button type="submit" class="btn btn-primary w-100 mb-3">Entrar</button>

        <div class="text-center">
            <a href="cadastro.jsp" class="text-decoration-none">Não tem conta? Cadastre-se</a>
        </div>
    </form>
</div>

</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cadastro - Sistema de Avaliação</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center justify-content-center" style="height: 100vh;">

<div class="card p-4 shadow" style="width: 400px;">
    <h3 class="text-center mb-4">Criar Conta</h3>

    <form action="criar-usuario" method="post">
        <div class="mb-3">
            <label>Nome Completo</label>
            <input type="text" name="nome" class="form-control" required>
        </div>

        <div class="mb-3">
            <label>Email</label>
            <input type="email" name="email" class="form-control" required>
        </div>

        <div class="mb-3">
            <label>Senha</label>
            <input type="password" name="senha" class="form-control" required>
        </div>

        <div class="mb-3">
            <label>Sou:</label>
            <select name="tipoUsuario" class="form-select">
                <option value="ALUNO">Aluno</option>
                <option value="PROFESSOR">Professor</option>
            </select>
        </div>

        <button type="submit" class="btn btn-success w-100 mb-3">Cadastrar</button>

        <div class="text-center">
            <a href="login.jsp" class="text-decoration-none">Já tenho conta</a>
        </div>
    </form>
</div>

</body>
</html>
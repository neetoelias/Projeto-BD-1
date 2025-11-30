<%@ page import="br.com.avaliacao.dal.DisciplinaDAO" %>
<%@ page import="br.com.avaliacao.dal.QuestaoDAO" %>
<%@ page import="br.com.avaliacao.model.Usuario" %>
<%@ page import="br.com.avaliacao.model.Disciplina" %>
<%@ page import="br.com.avaliacao.model.Questao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Usuario professor = (Usuario) session.getAttribute("usuarioLogado");

    DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    List<Disciplina> disciplinas = disciplinaDAO.listarTodas();

    QuestaoDAO questaoDAO = new QuestaoDAO();
    List<Questao> questoesBanco = questaoDAO.listarTodas();
%>
<html>
<head>
    <title>Nova Avaliação</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <script>
        function filtrarQuestoes() {
            // 1. Pega o ID da disciplina selecionada no dropdown
            var select = document.getElementById("selectDisciplina");
            var idSelecionado = select.value;

            // 2. Pega todas as linhas da tabela de questões
            var linhas = document.querySelectorAll(".linha-questao");

            var temQuestaoVisivel = false;

            // 3. Percorre cada linha e decide se mostra ou esconde
            linhas.forEach(function(linha) {
                // Pega o ID que guardamos no atributo 'data-disciplina' da linha
                var idDaQuestao = linha.getAttribute("data-disciplina");

                if (idDaQuestao === idSelecionado) {
                    linha.style.display = ""; // Mostra
                    temQuestaoVisivel = true;
                } else {
                    linha.style.display = "none"; // Esconde
                    // Desmarca o checkbox para não enviar questão escondida sem querer
                    var checkbox = linha.querySelector("input[type='checkbox']");
                    if(checkbox) checkbox.checked = false;
                }
            });

            // 4. Mostra aviso se não houver questões para essa matéria
            var aviso = document.getElementById("avisoVazio");
            if (!temQuestaoVisivel) {
                aviso.style.display = "block";
            } else {
                aviso.style.display = "none";
            }
        }
    </script>
</head>
<body class="bg-light" onload="filtrarQuestoes()"> <div class="container mt-5 mb-5">
    <div class="card shadow">
        <div class="card-header bg-success text-white">
            <h4>📅 Nova Avaliação</h4>
        </div>
        <div class="card-body">
            <form action="criar-avaliacao" method="post">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label>Título da Prova</label>
                        <input type="text" name="titulo" class="form-control" required>
                    </div>
                    <div class="col-md-3 mb-3">
                        <label>Disciplina</label>
                        <select name="idDisciplina" id="selectDisciplina" class="form-select" required onchange="filtrarQuestoes()">
                            <% for(Disciplina d : disciplinas) { %>
                            <option value="<%=d.getIdDisciplina()%>"><%=d.getNome()%></option>
                            <% } %>
                        </select>
                    </div>
                    <div class="col-md-3 mb-3">
                        <label>Data Limite</label>
                        <input type="date" name="dataLimite" class="form-control" required>
                    </div>
                </div>

                <h5 class="mt-4">Selecione as Questões</h5>
                <p class="text-muted small">Mostrando apenas questões da disciplina selecionada acima.</p>

                <div id="avisoVazio" class="alert alert-warning" style="display: none;">
                    Não há questões cadastradas para esta disciplina. Vá em "Criar Questão" primeiro.
                </div>

                <table class="table table-hover align-middle">
                    <thead class="table-light">
                    <tr>
                        <th style="width: 50px;">#</th>
                        <th>Questão</th>
                        <th>Matéria (ID)</th>
                        <th>Tipo</th>
                        <th style="width: 150px;">Peso (Pontos)</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for(Questao q : questoesBanco) { %>
                    <tr class="linha-questao" data-disciplina="<%= q.getIdDisciplina() %>">
                        <td>
                            <input class="form-check-input" type="checkbox" name="idsQuestoes" value="<%=q.getIdQuestao()%>">
                        </td>
                        <td><%= q.getDescricao() %></td>

                        <td>
                            <span class="badge bg-info text-dark">ID: <%= q.getIdDisciplina() %></span>
                        </td>

                        <td><span class="badge bg-secondary"><%= q.getTipoQuestao() %></span></td>
                        <td>
                            <input type="number" name="peso_<%=q.getIdQuestao()%>" class="form-control form-control-sm" value="1.0" step="0.5" min="0">
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>

                <div class="d-flex justify-content-between mt-4">
                    <a href="dashboard-professor.jsp" class="btn btn-secondary">Voltar</a>
                    <button type="submit" class="btn btn-success">Salvar Avaliação</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
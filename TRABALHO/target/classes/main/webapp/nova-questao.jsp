<%@ page import="br.com.avaliacao.dal.DisciplinaDAO" %>
<%@ page import="br.com.avaliacao.model.Disciplina" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // --- LÓGICA JAVA: Carregar as disciplinas do banco ---
    DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    List<Disciplina> listaDeDisciplinas = disciplinaDAO.listarTodas();
%>

<html>
<head>
    <title>Nova Questão</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function alternarTipo() {
            var tipo = document.getElementById("tipoSelect").value;
            if (tipo === "MULTIPLA_ESCOLHA") {
                document.getElementById("divMultipla").style.display = "block";
                document.getElementById("divDiscursiva").style.display = "none";
            } else {
                document.getElementById("divMultipla").style.display = "none";
                document.getElementById("divDiscursiva").style.display = "block";
            }
        }
    </script>
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <h4>📝 Nova Questão</h4>
        </div>
        <div class="card-body">
            <form action="criar-questao" method="post">

                <div class="mb-3">
                    <label>Disciplina</label>
                    <select name="idDisciplina" class="form-select" required>
                        <option value="" disabled selected>Selecione uma matéria...</option>
                        <%
                            // Agora a variável 'listaDeDisciplinas' existe e foi carregada lá em cima!
                            for(Disciplina d : listaDeDisciplinas) {
                        %>
                        <option value="<%=d.getIdDisciplina()%>"><%=d.getNome()%></option>
                        <% } %>
                    </select>
                </div>

                <div class="mb-3">
                    <label>Enunciado da Questão</label>
                    <textarea name="enunciado" class="form-control" rows="3" required></textarea>
                </div>

                <div class="mb-3">
                    <label>Tipo de Questão</label>
                    <select name="tipo" id="tipoSelect" class="form-select" onchange="alternarTipo()">
                        <option value="TEXTO_LIVRE">Discursiva (Texto Livre)</option>
                        <option value="MULTIPLA_ESCOLHA">Múltipla Escolha</option>
                    </select>
                </div>

                <div id="divDiscursiva">
                    <div class="mb-3">
                        <label>Gabarito (Resposta Esperada)</label>
                        <textarea name="gabaritoTexto" class="form-control" rows="2"></textarea>
                    </div>
                </div>

                <div id="divMultipla" style="display: none;" class="border p-3 rounded bg-white">
                    <h5>Opções</h5>
                    <p class="text-muted small">Preencha as opções e marque a correta no botão redondo.</p>

                    <% for(int i=0; i<4; i++) { %>
                    <div class="input-group mb-2">
                        <div class="input-group-text">
                            <input class="form-check-input mt-0" type="radio" name="opcaoCorreta" value="<%=i%>" <%= i==0?"checked":"" %>>
                        </div>
                        <input type="text" name="opcaoTexto" class="form-control" placeholder="Opção <%=i+1%>">
                    </div>
                    <% } %>
                </div>

                <button type="submit" class="btn btn-success mt-3">Salvar Questão</button>
                <a href="dashboard-professor.jsp" class="btn btn-secondary mt-3">Voltar</a>
            </form>
        </div>
    </div>
</div>
</body>
</html>
<%@ page import="br.com.avaliacao.dal.RespostaDAO" %>
<%@ page import="br.com.avaliacao.model.dto.ItemCorrecao" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.avaliacao.model.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if (usuario == null || !"PROFESSOR".equals(usuario.getTipoUsuario())) {
        response.sendRedirect("login.jsp");
        return;
    }

   
    String idRealizacaoStr = request.getParameter("idRealizacao");
    if(idRealizacaoStr == null) {
        response.sendRedirect("dashboard-professor.jsp");
        return;
    }

    
    RespostaDAO dao = new RespostaDAO();
    List<ItemCorrecao> itens = dao.buscarParaCorrecao(Integer.parseInt(idRealizacaoStr));
%>
<html>
<head>
    <title>Corrigir Prova</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5 mb-5">
    <div class="card shadow">
        <div class="card-header bg-warning text-dark d-flex justify-content-between align-items-center">
            <h4 class="mb-0">📝 Corrigir Prova</h4>
            <a href="dashboard-professor.jsp" class="btn btn-sm btn-outline-dark">Voltar</a>
        </div>
        <div class="card-body">
            <form action="salvar-correcao" method="post">

                <% for (ItemCorrecao item : itens) {
                    boolean acertouME = false;
                    Double notaSugerida = item.getNotaObtida();

                    
                    if ("MULTIPLA_ESCOLHA".equals(item.getTipoQuestao())) {
                        if (item.getRespostaAlunoTexto() != null && item.getRespostaAlunoTexto().equals(item.getGabarito())) {
                            acertouME = true;
                            if (notaSugerida == null) notaSugerida = item.getValorQuestao(); // Sugere nota cheia
                        } else {
                            if (notaSugerida == null) notaSugerida = 0.0; // Sugere zero
                        }
                    }
                    
                    String valorInput = (notaSugerida != null) ? String.valueOf(notaSugerida) : "";
                %>

                <div class="card mb-3 <%= acertouME ? "border-success" : "" %>">
                    <div class="card-body">
                        <h5 class="card-title">Questão: <%= item.getEnunciado() %></h5>
                        <div class="row">
                            <div class="col-md-8">
                                <p><strong>Resposta do Aluno:</strong><br>
                                    <span class="text-primary"><%= item.getRespostaAlunoTexto() %></span>
                                </p>
                                <p class="small text-muted"><strong>Gabarito:</strong> <%= item.getGabarito() %></p>
                            </div>
                            <div class="col-md-4 border-start">
                                <label>Nota (Máx: <%= item.getValorQuestao() %>)</label>
                                <input type="number"
                                       name="nota_<%= item.getIdResposta() %>"
                                       class="form-control"
                                       step="0.1" min="0" max="<%= item.getValorQuestao() %>"
                                       value="<%= valorInput %>" required>

                                <% if("MULTIPLA_ESCOLHA".equals(item.getTipoQuestao())) { %>
                                <small class="d-block mt-2">
                                    <% if(acertouME) { %>
                                    <span class="badge bg-success">Sistema: Acertou!</span>
                                    <% } else { %>
                                    <span class="badge bg-danger">Sistema: Errou</span>
                                    <% } %>
                                </small>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>
                <% } %>

                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-success btn-lg">💾 Salvar Correção</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
package br.com.avaliacao.model;

public class Resposta {

    private Integer idResposta;
    private Integer idRealizacao;
    private Integer idQuestao;
    private String respostaTexto;

    private Integer idOpcaoEscolhida;

    private Double notaObtida;

    public Resposta() {
    }

    public Integer getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(Integer idResposta) {
        this.idResposta = idResposta;
    }

    public Integer getIdRealizacao() {
        return idRealizacao;
    }

    public void setIdRealizacao(Integer idRealizacao) {
        this.idRealizacao = idRealizacao;
    }

    public Integer getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(Integer idQuestao) {
        this.idQuestao = idQuestao;
    }

    public String getRespostaTexto() {
        return respostaTexto;
    }

    public void setRespostaTexto(String respostaTexto) {
        this.respostaTexto = respostaTexto;
    }

    public Double getNotaObtida() {
        return notaObtida;
    }

    public void setNotaObtida(Double notaObtida) {
        this.notaObtida = notaObtida;
    }

    public Integer getIdOpcaoEscolhida() {
        return idOpcaoEscolhida;
    }

    public void setIdOpcaoEscolhida(Integer idOpcaoEscolhida) {
        this.idOpcaoEscolhida = idOpcaoEscolhida;
    }
}
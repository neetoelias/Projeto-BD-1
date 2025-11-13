package br.com.avaliacao.model;

public class ItemAvaliacao {

    private Integer idAvaliacao;
    private Integer idQuestao;

    private Double valorPontuacao;

    public ItemAvaliacao() {
    }

    public ItemAvaliacao(Integer idQuestao, Double valorPontuacao) {
        this.idQuestao = idQuestao;
        this.valorPontuacao = valorPontuacao;
    }

    public Integer getIdAvaliacao() {
        return idAvaliacao;
    }
    public void setIdAvaliacao(Integer idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public Integer getIdQuestao() {
        return idQuestao;
    }
    public void setIdQuestao(Integer idQuestao) {
        this.idQuestao = idQuestao;
    }

    public Double getValorPontuacao() {
        return valorPontuacao;
    }
    public void setValorPontuacao(Double valorPontuacao) {
        this.valorPontuacao = valorPontuacao;
    }
}
package br.com.avaliacao.model.dto;

public class ItemCorrecao {
    private Integer idResposta;
    private String enunciado;
    private String tipoQuestao;
    private String respostaAlunoTexto; 
    private String gabarito; 
    private Double valorQuestao;
    private Double notaObtida;

   
    public ItemCorrecao(Integer idResposta, String enunciado, String tipoQuestao,
                        String respostaAlunoTexto, String gabarito,
                        Double valorQuestao, Double notaObtida) {
        this.idResposta = idResposta;
        this.enunciado = enunciado;
        this.tipoQuestao = tipoQuestao;
        this.respostaAlunoTexto = respostaAlunoTexto;
        this.gabarito = gabarito;
        this.valorQuestao = valorQuestao;
        this.notaObtida = notaObtida;
    }

    
    public Integer getIdResposta() { return idResposta; }
    public String getEnunciado() { return enunciado; }
    public String getTipoQuestao() { return tipoQuestao; }
    public String getRespostaAlunoTexto() { return respostaAlunoTexto; }
    public String getGabarito() { return gabarito; }
    public Double getValorQuestao() { return valorQuestao; }
    public Double getNotaObtida() { return notaObtida; }
}
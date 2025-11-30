package br.com.avaliacao.model.dto;

public class RelatorioAlunoDTO {
    private String nomeAvaliacao;
    private Double suaNota;
    private Double mediaTurma;
    private Integer posicaoTurma;

    public RelatorioAlunoDTO(String nomeAvaliacao, Double suaNota, Double mediaTurma, Integer posicaoTurma) {
        this.nomeAvaliacao = nomeAvaliacao;
        this.suaNota = suaNota;
        this.mediaTurma = mediaTurma;
        this.posicaoTurma = posicaoTurma;
    }

    
    public String getNomeAvaliacao() { return nomeAvaliacao; }
    public Double getSuaNota() { return suaNota; }
    public Double getMediaTurma() { return mediaTurma; }
    public Integer getPosicaoTurma() { return posicaoTurma; }
}
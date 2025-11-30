package br.com.avaliacao.model.dto;

public class RelatorioProfessorDTO {
    private String nomeDisciplina;
    private Double mediaGeral;
    private Double mediana;
    private Double maiorNota;
    private Double menorNota;

    public RelatorioProfessorDTO(String nomeDisciplina, Double mediaGeral, Double mediana, Double maiorNota, Double menorNota) {
        this.nomeDisciplina = nomeDisciplina;
        this.mediaGeral = mediaGeral;
        this.mediana = mediana;
        this.maiorNota = maiorNota;
        this.menorNota = menorNota;
    }

    
    public String getNomeDisciplina() { return nomeDisciplina; }
    public Double getMediaGeral() { return mediaGeral; }
    public Double getMediana() { return mediana; }
    public Double getMaiorNota() { return maiorNota; }
    public Double getMenorNota() { return menorNota; }
}
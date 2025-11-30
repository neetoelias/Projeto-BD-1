package br.com.avaliacao.model.dto;

import java.time.LocalDateTime;

public class AvaliacaoDisponivelDTO {
    private Integer idAvaliacao;
    private String titulo;
    private String nomeDisciplina;
    private LocalDateTime dataLimite;
    private boolean realizada; 

    public AvaliacaoDisponivelDTO(Integer idAvaliacao, String titulo, String nomeDisciplina, LocalDateTime dataLimite, boolean realizada) {
        this.idAvaliacao = idAvaliacao;
        this.titulo = titulo;
        this.nomeDisciplina = nomeDisciplina;
        this.dataLimite = dataLimite;
        this.realizada = realizada;
    }

    // Getters
    public Integer getIdAvaliacao() { return idAvaliacao; }
    public String getTitulo() { return titulo; }
    public String getNomeDisciplina() { return nomeDisciplina; }
    public LocalDateTime getDataLimite() { return dataLimite; }
    public boolean isRealizada() { return realizada; }
}
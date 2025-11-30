package br.com.avaliacao.model.dto;

import java.time.LocalDateTime;

public class RelatorioRealizacao {

    private Integer idRealizacao;
    private Integer idAvaliacao; 
    private String nomeAluno;
    private String tituloAvaliacao;
    private LocalDateTime dataInicio;

   
    public RelatorioRealizacao(Integer idRealizacao, Integer idAvaliacao, String nomeAluno, String tituloAvaliacao, LocalDateTime dataInicio) {
        this.idRealizacao = idRealizacao;
        this.idAvaliacao = idAvaliacao; 
        this.nomeAluno = nomeAluno;
        this.tituloAvaliacao = tituloAvaliacao;
        this.dataInicio = dataInicio;
    }

    public Integer getIdRealizacao() { return idRealizacao; }
    public Integer getIdAvaliacao() { return idAvaliacao; } 
    public String getNomeAluno() { return nomeAluno; }
    public String getTituloAvaliacao() { return tituloAvaliacao; }
    public LocalDateTime getDataInicio() { return dataInicio; }
}
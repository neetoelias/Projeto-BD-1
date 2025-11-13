package br.com.avaliacao.model;

import java.time.LocalDateTime;

/**
 * Representa a tabela REALIZACAO_AVALIACAO, que é a "tentativa"
 * de um aluno em uma avaliação.
 */
public class RealizacaoAvaliacao {

    private Integer idRealizacao;
    private Integer idAvaliacao;
    private Integer idAluno;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    public RealizacaoAvaliacao() {
    }

    public RealizacaoAvaliacao(Integer idAvaliacao, Integer idAluno) {
        this.idAvaliacao = idAvaliacao;
        this.idAluno = idAluno;
        this.dataInicio = LocalDateTime.now();
    }

    public Integer getIdRealizacao() {
        return idRealizacao;
    }

    public void setIdRealizacao(Integer idRealizacao) {
        this.idRealizacao = idRealizacao;
    }

    public Integer getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(Integer idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }
}
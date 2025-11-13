package br.com.avaliacao.model;

import java.time.LocalDateTime;
import java.util.List;

public class Avaliacao {

    private Integer idAvaliacao;
    private String titulo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataLimite;
    private Double valorTotal;
    private Integer idProfessor;
    private Integer idDisciplina;

    private List<ItemAvaliacao> itens;

    public Avaliacao() {
        this.dataCriacao = LocalDateTime.now();
    }

    public Avaliacao(String titulo, LocalDateTime dataLimite, Double valorTotal, Integer idProfessor, Integer idDisciplina) {
        this();
        this.titulo = titulo;
        this.dataLimite = dataLimite;
        this.valorTotal = valorTotal;
        this.idProfessor = idProfessor;
        this.idDisciplina = idDisciplina;
    }

    public Integer getIdAvaliacao() { return idAvaliacao; }
    public void setIdAvaliacao(Integer idAvaliacao) { this.idAvaliacao = idAvaliacao; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataLimite() { return dataLimite; }
    public void setDataLimite(LocalDateTime dataLimite) { this.dataLimite = dataLimite; }

    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }

    public Integer getIdProfessor() { return idProfessor; }
    public void setIdProfessor(Integer idProfessor) { this.idProfessor = idProfessor; }

    public Integer getIdDisciplina() { return idDisciplina; }
    public void setIdDisciplina(Integer idDisciplina) { this.idDisciplina = idDisciplina; }

    public List<ItemAvaliacao> getItens() { return itens; }
    public void setItens(List<ItemAvaliacao> itens) { this.itens = itens; }

    @Override
    public String toString() {
        return "Avaliacao [id=" + idAvaliacao + ", titulo=" + titulo + ", valor=" + valorTotal + ", disciplina=" + idDisciplina + "]";
    }
}
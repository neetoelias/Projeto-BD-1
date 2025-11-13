package br.com.avaliacao.model;

public class Disciplina {
    
    private Integer idDisciplina;
    private String nome;
    private String codigo;

    public Disciplina() {}

    public Disciplina(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
    }

    public Disciplina(Integer idDisciplina, String nome, String codigo) {
        this.idDisciplina = idDisciplina;
        this.nome = nome;
        this.codigo = codigo;
    }

    public Integer getIdDisciplina() { return idDisciplina; }
    public void setIdDisciplina(Integer idDisciplina) { this.idDisciplina = idDisciplina; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    @Override
    public String toString() {
        return "Disciplina [id=" + idDisciplina + ", nome=" + nome + ", codigo=" + codigo + "]";
    }
}
package br.com.avaliacao.model;

public class Questao {
    
    private Integer idQuestao;
    private String descricao;
    private String tipoQuestao; 
    private Integer idProfessor; 

    public Questao() {}

  
    public Questao(String descricao, String tipoQuestao, Integer idProfessor) {
        this.descricao = descricao;
        this.tipoQuestao = tipoQuestao;
        this.idProfessor = idProfessor;
    }

   
    public Questao(Integer idQuestao, String descricao, String tipoQuestao, Integer idProfessor) {
        this.idQuestao = idQuestao;
        this.descricao = descricao;
        this.tipoQuestao = tipoQuestao;
        this.idProfessor = idProfessor;
    }

    
    public Integer getIdQuestao() { return idQuestao; }
    public void setIdQuestao(Integer idQuestao) { this.idQuestao = idQuestao; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public String getTipoQuestao() { return tipoQuestao; }
    public void setTipoQuestao(String tipoQuestao) { this.tipoQuestao = tipoQuestao; }
    
    public Integer getIdProfessor() { return idProfessor; }
    public void setIdProfessor(Integer idProfessor) { this.idProfessor = idProfessor; }

    @Override
    public String toString() {
        return "Questao [id=" + idQuestao + ", tipo=" + tipoQuestao + ", professorId=" + idProfessor + "]";
    }
}
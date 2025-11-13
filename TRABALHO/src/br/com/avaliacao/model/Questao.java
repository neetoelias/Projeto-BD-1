package br.com.avaliacao.model;

import java.util.List;

public class Questao {

    private Integer idQuestao;
    private String descricao;
    private String tipoQuestao;
    private Integer idProfessor;

    private String gabaritoTexto;

    private List<Opcao> opcoes;


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

    public String getGabaritoTexto() {
        return gabaritoTexto;
    }

    public void setGabaritoTexto(String gabaritoTexto) {
        this.gabaritoTexto = gabaritoTexto;
    }

    public List<Opcao> getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(List<Opcao> opcoes) {
        this.opcoes = opcoes;
    }

    @Override
    public String toString() {
        return "Questao [id=" + idQuestao +
                ", tipo=" + tipoQuestao +
                ", professorId=" + idProfessor +
                ", gabaritoTexto=" + (gabaritoTexto != null ? "Sim" : "Nao") +
                ", numOpcoes=" + (opcoes != null ? opcoes.size() : 0) + "]";
    }
}
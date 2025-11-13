package br.com.avaliacao.model;

public class Opcao {

    private Integer idOpcao;
    private Integer idQuestao;
    private String descricaoOpcao;
    private Boolean estaCorreta;

    public Opcao() {
    }

    public Opcao(Integer idQuestao, String descricaoOpcao, Boolean estaCorreta) {
        this.idQuestao = idQuestao;
        this.descricaoOpcao = descricaoOpcao;
        this.estaCorreta = estaCorreta;
    }

    public Integer getIdOpcao() {
        return idOpcao;
    }

    public void setIdOpcao(Integer idOpcao) {
        this.idOpcao = idOpcao;
    }

    public Integer getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(Integer idQuestao) {
        this.idQuestao = idQuestao;
    }

    public String getDescricaoOpcao() {
        return descricaoOpcao;
    }

    public void setDescricaoOpcao(String descricaoOpcao) {
        this.descricaoOpcao = descricaoOpcao;
    }

    public Boolean getEstaCorreta() {
        return estaCorreta;
    }

    public void setEstaCorreta(Boolean estaCorreta) {
        this.estaCorreta = estaCorreta;
    }
}
package br.com.avaliacao.model;

public class Resposta {
    
    private Integer idResposta;
    private Integer idAvaliacao; 
    private Integer idQuestao;  
    private Integer idUsuario;   
    private String conteudoResposta;
    private Double notaObtida; 

    public Resposta() {}

   
    public Resposta(Integer idAvaliacao, Integer idQuestao, Integer idUsuario, String conteudoResposta) {
        this.idAvaliacao = idAvaliacao;
        this.idQuestao = idQuestao;
        this.idUsuario = idUsuario;
        this.conteudoResposta = conteudoResposta;
    }

   
    public Integer getIdResposta() { return idResposta; }
    public void setIdResposta(Integer idResposta) { this.idResposta = idResposta; }
    
    public Integer getIdAvaliacao() { return idAvaliacao; }
    public void setIdAvaliacao(Integer idAvaliacao) { this.idAvaliacao = idAvaliacao; }
    
    public Integer getIdQuestao() { return idQuestao; }
    public void setIdQuestao(Integer idQuestao) { this.idQuestao = idQuestao; }
    
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    
    public String getConteudoResposta() { return conteudoResposta; }
    public void setConteudoResposta(String conteudoResposta) { this.conteudoResposta = conteudoResposta; }

    public Double getNotaObtida() { return notaObtida; }
    public void setNotaObtida(Double notaObtida) { this.notaObtida = notaObtida; }

    @Override
    public String toString() {
        return "Resposta [id=" + idResposta + ", avaliacao=" + idAvaliacao + ", questao=" + idQuestao + ", usuario=" + idUsuario + "]";
    }
}
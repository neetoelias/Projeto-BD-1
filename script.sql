DROP SCHEMA IF EXISTS sada CASCADE;
CREATE SCHEMA sada;
SET search_path TO sada;

CREATE TABLE USUARIO (
    id_usuario SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('aluno', 'professor'))
);

CREATE TABLE QUESTAO (
    id_questao SERIAL PRIMARY KEY,
    enunciado TEXT NOT NULL,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('multipla_escolha', 'dissertativa', 'verdadeiro_falso')),
    id_criador INT NOT NULL,
    CONSTRAINT fk_criador FOREIGN KEY (id_criador) REFERENCES USUARIO(id_usuario) ON DELETE RESTRICT
);


CREATE TABLE ALTERNATIVA (
    id_alternativa SERIAL PRIMARY KEY,
    id_questao INT NOT NULL,
    texto_alternativa TEXT NOT NULL,
    is_correta BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_questao FOREIGN KEY (id_questao) REFERENCES QUESTAO(id_questao) ON DELETE CASCADE
);


CREATE TABLE AVALIACAO (
    id_avaliacao SERIAL PRIMARY KEY,
    id_professor INT NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_inicio TIMESTAMP NOT NULL,
    data_fim TIMESTAMP NOT NULL,
    CONSTRAINT fk_professor FOREIGN KEY (id_professor) REFERENCES USUARIO(id_usuario) ON DELETE RESTRICT,
    CONSTRAINT chk_datas CHECK (data_fim > data_inicio)
);

CREATE TABLE AVALIACAO_QUESTAO (
    id_avaliacao INT NOT NULL,
    id_questao INT NOT NULL,
    valor_pontos DECIMAL(5, 2) NOT NULL CHECK (valor_pontos > 0),
    PRIMARY KEY (id_avaliacao, id_questao),
    CONSTRAINT fk_avaliacao FOREIGN KEY (id_avaliacao) REFERENCES AVALIACAO(id_avaliacao) ON DELETE CASCADE,
    CONSTRAINT fk_questao FOREIGN KEY (id_questao) REFERENCES QUESTAO(id_questao) ON DELETE CASCADE
);


CREATE TABLE INSCRICAO_AVALIACAO (
    id_inscricao SERIAL PRIMARY KEY,
    id_aluno INT NOT NULL,
    id_avaliacao INT NOT NULL,
    data_submissao TIMESTAMP,
    nota_final DECIMAL(5, 2),
    CONSTRAINT fk_aluno FOREIGN KEY (id_aluno) REFERENCES USUARIO(id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_avaliacao FOREIGN KEY (id_avaliacao) REFERENCES AVALIACAO(id_avaliacao) ON DELETE CASCADE,
    UNIQUE (id_aluno, id_avaliacao) 
);


CREATE TABLE RESPOSTA (
    id_resposta SERIAL PRIMARY KEY,
    id_inscricao INT NOT NULL,
    id_questao INT NOT NULL,
    id_alternativa_escolhida INT, 
    texto_resposta TEXT, 
    nota_obtida DECIMAL(5, 2), 
    CONSTRAINT fk_inscricao FOREIGN KEY (id_inscricao) REFERENCES INSCRICAO_AVALIACAO(id_inscricao) ON DELETE CASCADE,
    CONSTRAINT fk_questao FOREIGN KEY (id_questao) REFERENCES QUESTAO(id_questao) ON DELETE RESTRICT,
    CONSTRAINT fk_alternativa FOREIGN KEY (id_alternativa_escolhida) REFERENCES ALTERNATIVA(id_alternativa) ON DELETE SET NULL,
    UNIQUE (id_inscricao, id_questao) 
);
-- Garante que o usuário só possa fazer uma avaliação uma vez.
-- Garante que id_avaliacao e id_aluno sejam NOT NULL.

-- Tabela de Usuários
CREATE TABLE USUARIO (
    id_usuario SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(20) NOT NULL,
    CHECK (tipo_usuario IN ('ALUNO', 'PROFESSOR', 'ADMIN'))
);

-- Tabela de Disciplinas
CREATE TABLE DISCIPLINA (
    id_disciplina SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    codigo VARCHAR(20) UNIQUE NOT NULL
);

-- Tabela de Questões
CREATE TABLE QUESTAO (
    id_questao SERIAL PRIMARY KEY,
    descricao TEXT NOT NULL,
    tipo_questao VARCHAR(30) NOT NULL,
    id_professor INTEGER NOT NULL,
    data_criacao TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CHECK (tipo_questao IN ('MULTIPLA_ESCOLHA', 'TEXTO_LIVRE')),
    FOREIGN KEY (id_professor) REFERENCES USUARIO(id_usuario)
);

-- Tabela de Avaliações
CREATE TABLE AVALIACAO (
    id_avaliacao SERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    data_abertura DATE NOT NULL,
    data_fechamento DATE NOT NULL,
    id_professor INTEGER NOT NULL,
    id_disciplina INTEGER NOT NULL,
    FOREIGN KEY (id_professor) REFERENCES USUARIO(id_usuario),
    FOREIGN KEY (id_disciplina) REFERENCES DISCIPLINA(id_disciplina)
);

-- Tabela de Itens da Avaliação (N:N entre Avaliacao e Questao, com valor)
CREATE TABLE ITEM_AVALIACAO (
    id_avaliacao INTEGER NOT NULL,
    id_questao INTEGER NOT NULL,
    valor_pontuacao NUMERIC(5, 2) NOT NULL,
    PRIMARY KEY (id_avaliacao, id_questao), -- Chave Primária Composta
    FOREIGN KEY (id_avaliacao) REFERENCES AVALIACAO(id_avaliacao) ON DELETE CASCADE,
    FOREIGN KEY (id_questao) REFERENCES QUESTAO(id_questao) ON DELETE RESTRICT,
    CHECK (valor_pontuacao > 0)
);

-- Tabela de Realização da Avaliação (a tentativa do aluno)
CREATE TABLE REALIZACAO_AVALIACAO (
    id_realizacao SERIAL PRIMARY KEY,
    id_avaliacao INTEGER NOT NULL,
    id_aluno INTEGER NOT NULL,
    data_inicio TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_fim TIMESTAMP WITHOUT TIME ZONE,
    UNIQUE (id_avaliacao, id_aluno), -- Um aluno só pode fazer uma avaliação uma vez
    FOREIGN KEY (id_avaliacao) REFERENCES AVALIACAO(id_avaliacao) ON DELETE CASCADE,
    FOREIGN KEY (id_aluno) REFERENCES USUARIO(id_usuario)
);

-- Tabela de Respostas para cada Questão na Realização
CREATE TABLE RESPOSTA_QUESTAO (
    id_resposta SERIAL PRIMARY KEY,
    id_realizacao INTEGER NOT NULL,
    id_questao INTEGER NOT NULL,
    resposta_texto TEXT, -- Para questões de texto livre
    -- Para questões de múltipla escolha, podemos ter uma FK para uma futura tabela OPCAO
    nota_obtida NUMERIC(5, 2),
    UNIQUE (id_realizacao, id_questao), -- Um aluno só responde a questão uma vez por realização
    FOREIGN KEY (id_realizacao) REFERENCES REALIZACAO_AVALIACAO(id_realizacao) ON DELETE CASCADE,
    FOREIGN KEY (id_questao) REFERENCES QUESTAO(id_questao) ON DELETE RESTRICT,
    CHECK (nota_obtida >= 0)
);
CREATE TABLE USUARIO (
                         id_usuario SERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         email VARCHAR(100) UNIQUE NOT NULL,
                         senha_hash VARCHAR(255) NOT NULL,
                         tipo_usuario VARCHAR(20) NOT NULL,
                         CHECK (tipo_usuario IN ('ALUNO', 'PROFESSOR', 'ADMIN'))
);

CREATE TABLE DISCIPLINA (
                            id_disciplina SERIAL PRIMARY KEY,
                            nome VARCHAR(100) NOT NULL,
                            codigo VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE QUESTAO (
                         id_questao SERIAL PRIMARY KEY,
                         descricao TEXT NOT NULL,
                         tipo_questao VARCHAR(30) NOT NULL,
                         id_professor INTEGER NOT NULL,
                         data_criacao TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

                         gabarito_texto TEXT NULL,

                         CHECK (tipo_questao IN ('MULTIPLA_ESCOLHA', 'TEXTO_LIVRE')),
                         FOREIGN KEY (id_professor) REFERENCES USUARIO(id_usuario)
);

CREATE TABLE OPCAO (
                       id_opcao SERIAL PRIMARY KEY,
                       id_questao INTEGER NOT NULL,
                       descricao_opcao TEXT NOT NULL,

                       esta_correta BOOLEAN NOT NULL DEFAULT FALSE,

                       FOREIGN KEY (id_questao) REFERENCES QUESTAO(id_questao) ON DELETE CASCADE
);

CREATE TABLE AVALIACAO (
                           id_avaliacao SERIAL PRIMARY KEY,
                           descricao VARCHAR(255) NOT NULL,
                           data_abertura DATE NOT NULL,
                           data_fechamento DATE NOT NULL,
                           valor_total NUMERIC(5, 2) NOT NULL,
                           id_professor INTEGER NOT NULL,
                           id_disciplina INTEGER NOT NULL,
                           FOREIGN KEY (id_professor) REFERENCES USUARIO(id_usuario),
                           FOREIGN KEY (id_disciplina) REFERENCES DISCIPLINA(id_disciplina),
                           CHECK (valor_total >= 0)
);

CREATE TABLE ITEM_AVALIACAO (
                                id_avaliacao INTEGER NOT NULL,
                                id_questao INTEGER NOT NULL,
                                valor_pontuacao NUMERIC(5, 2) NOT NULL,
                                PRIMARY KEY (id_avaliacao, id_questao),
                                FOREIGN KEY (id_avaliacao) REFERENCES AVALIACAO(id_avaliacao) ON DELETE CASCADE,
                                FOREIGN KEY (id_questao) REFERENCES QUESTAO(id_questao) ON DELETE RESTRICT,
                                CHECK (valor_pontuacao > 0)
);

CREATE TABLE REALIZACAO_AVALIACAO (
                                      id_realizacao SERIAL PRIMARY KEY,
                                      id_avaliacao INTEGER NOT NULL,
                                      id_aluno INTEGER NOT NULL,
                                      data_inicio TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                      data_fim TIMESTAMP WITHOUT TIME ZONE,
                                      UNIQUE (id_avaliacao, id_aluno),
                                      FOREIGN KEY (id_avaliacao) REFERENCES AVALIACAO(id_avaliacao) ON DELETE CASCADE,
                                      FOREIGN KEY (id_aluno) REFERENCES USUARIO(id_usuario)
);

CREATE TABLE RESPOSTA_QUESTAO (
                                  id_resposta SERIAL PRIMARY KEY,
                                  id_realizacao INTEGER NOT NULL,
                                  id_questao INTEGER NOT NULL,

                                  resposta_texto TEXT NULL,

                                  id_opcao_escolhida INTEGER NULL,

                                  nota_obtida NUMERIC(5, 2) NULL,

                                  UNIQUE (id_realizacao, id_questao),
                                  FOREIGN KEY (id_realizacao) REFERENCES REALIZACAO_AVALIACAO(id_realizacao) ON DELETE CASCADE,
                                  FOREIGN KEY (id_questao) REFERENCES QUESTAO(id_questao) ON DELETE RESTRICT,

                                  FOREIGN KEY (id_opcao_escolhida) REFERENCES OPCAO(id_opcao),

                                  CHECK (nota_obtida >= 0)
);
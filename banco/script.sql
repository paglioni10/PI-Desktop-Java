-- Mao na Roda - estrutura do banco de dados (SQLite)
-- Executado automaticamente pela classe Model.DAO.Conexao na primeira execucao.

CREATE TABLE IF NOT EXISTS usuario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    senha TEXT NOT NULL,
    telefone TEXT,
    data_cadastro TEXT NOT NULL,
    status TEXT NOT NULL,
    tipo TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS cliente (
    id_usuario INTEGER PRIMARY KEY,
    cpf TEXT,
    data_nascimento TEXT,
    FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS prestador (
    id_usuario INTEGER PRIMARY KEY,
    cpf_cnpj TEXT,
    descricao_profissional TEXT,
    raio_atendimento_km INTEGER DEFAULT 0,
    nota_media REAL DEFAULT 0,
    verificado INTEGER DEFAULT 0,
    FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS administrador (
    id_usuario INTEGER PRIMARY KEY,
    nivel_acesso TEXT,
    ultimo_acesso TEXT,
    FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS categoria (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    descricao TEXT,
    ativa INTEGER DEFAULT 1
);

CREATE TABLE IF NOT EXISTS servico (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    titulo TEXT NOT NULL,
    descricao TEXT,
    preco_base REAL NOT NULL,
    tempo_estimado_horas INTEGER,
    status TEXT NOT NULL,
    id_prestador INTEGER NOT NULL,
    id_categoria INTEGER NOT NULL,
    FOREIGN KEY (id_prestador) REFERENCES prestador (id_usuario),
    FOREIGN KEY (id_categoria) REFERENCES categoria (id)
);

CREATE TABLE IF NOT EXISTS solicitacao_servico (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    data_solicitacao TEXT NOT NULL,
    descricao_necessidade TEXT NOT NULL,
    status TEXT NOT NULL,
    valor_final REAL DEFAULT 0,
    id_cliente INTEGER NOT NULL,
    id_servico INTEGER NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente (id_usuario),
    FOREIGN KEY (id_servico) REFERENCES servico (id)
);

CREATE TABLE IF NOT EXISTS orcamento (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    valor REAL NOT NULL,
    prazo_execucao_dias INTEGER,
    validade TEXT,
    observacoes TEXT,
    status TEXT NOT NULL,
    id_solicitacao INTEGER NOT NULL,
    FOREIGN KEY (id_solicitacao) REFERENCES solicitacao_servico (id)
);

INSERT INTO categoria (id, nome, descricao, ativa) VALUES (1, 'Eletrica', 'Instalacoes e reparos eletricos', 1);
INSERT INTO categoria (id, nome, descricao, ativa) VALUES (2, 'Encanamento', 'Hidraulica em geral', 1);
INSERT INTO categoria (id, nome, descricao, ativa) VALUES (3, 'Limpeza', 'Limpeza residencial e pos-obra', 1);
INSERT INTO categoria (id, nome, descricao, ativa) VALUES (4, 'Reforma', 'Pequenas reformas e reparos', 1);

INSERT INTO usuario (id, nome, email, senha, telefone, data_cadastro, status, tipo)
    VALUES (1, 'Rafael Moraes', 'admin@maonaroda.com', '123', '48999110011', '2026-08-01', 'ATIVO', 'ADMINISTRADOR');
INSERT INTO administrador (id_usuario, nivel_acesso, ultimo_acesso) VALUES (1, 'ADMINISTRADOR', NULL);

INSERT INTO usuario (id, nome, email, senha, telefone, data_cadastro, status, tipo)
    VALUES (2, 'Maria Souza', 'maria@email.com', '123', '48999220022', '2026-09-10', 'ATIVO', 'CLIENTE');
INSERT INTO cliente (id_usuario, cpf, data_nascimento) VALUES (2, '012.345.678-90', '1992-04-17');

INSERT INTO usuario (id, nome, email, senha, telefone, data_cadastro, status, tipo)
    VALUES (3, 'Joao Pereira', 'joao@email.com', '123', '48999330033', '2026-09-22', 'ATIVO', 'CLIENTE');
INSERT INTO cliente (id_usuario, cpf, data_nascimento) VALUES (3, '987.654.321-00', '1985-11-03');

INSERT INTO usuario (id, nome, email, senha, telefone, data_cadastro, status, tipo)
    VALUES (4, 'Carlos Lima', 'carlos@email.com', '123', '48999440044', '2026-08-15', 'ATIVO', 'PRESTADOR');
INSERT INTO prestador (id_usuario, cpf_cnpj, descricao_profissional, raio_atendimento_km, nota_media, verificado)
    VALUES (4, '111.222.333-44', 'Eletricista com 12 anos de experiencia', 20, 4.8, 1);

INSERT INTO usuario (id, nome, email, senha, telefone, data_cadastro, status, tipo)
    VALUES (5, 'Ana Ribeiro', 'ana@email.com', '123', '48999550055', '2026-08-28', 'ATIVO', 'PRESTADOR');
INSERT INTO prestador (id_usuario, cpf_cnpj, descricao_profissional, raio_atendimento_km, nota_media, verificado)
    VALUES (5, '555.666.777-88', 'Encanadora e instaladora hidraulica', 15, 4.6, 1);

INSERT INTO usuario (id, nome, email, senha, telefone, data_cadastro, status, tipo)
    VALUES (6, 'Pedro Antunes', 'pedro@email.com', '123', '48999660066', '2026-11-18', 'AGUARDANDO_VERIFICACAO', 'PRESTADOR');
INSERT INTO prestador (id_usuario, cpf_cnpj, descricao_profissional, raio_atendimento_km, nota_media, verificado)
    VALUES (6, '999.888.777-66', 'Pintor e reparos em geral', 10, 0, 0);

INSERT INTO servico (id, titulo, descricao, preco_base, tempo_estimado_horas, status, id_prestador, id_categoria)
    VALUES (1, 'Troca de disjuntor', 'Substituicao de disjuntor e revisao do quadro', 180.0, 2, 'ATIVO', 4, 1);
INSERT INTO servico (id, titulo, descricao, preco_base, tempo_estimado_horas, status, id_prestador, id_categoria)
    VALUES (2, 'Instalacao de chuveiro', 'Instalacao de chuveiro eletrico com ajuste de fiacao', 150.0, 2, 'ATIVO', 4, 1);
INSERT INTO servico (id, titulo, descricao, preco_base, tempo_estimado_horas, status, id_prestador, id_categoria)
    VALUES (3, 'Conserto de vazamento', 'Localizacao e reparo de vazamento em tubulacao', 220.0, 3, 'ATIVO', 5, 2);

INSERT INTO solicitacao_servico (id, data_solicitacao, descricao_necessidade, status, valor_final, id_cliente, id_servico)
    VALUES (1, '2026-11-17', 'Disjuntor da cozinha desarma toda vez que ligo o forno', 'PENDENTE', 0, 2, 1);
INSERT INTO solicitacao_servico (id, data_solicitacao, descricao_necessidade, status, valor_final, id_cliente, id_servico)
    VALUES (2, '2026-11-19', 'Vazamento embaixo da pia do banheiro', 'PENDENTE', 0, 3, 3);

-- Carga de dados extra para a apresentacao.
-- Opcional: roda sobre um banco ja criado pelo script.sql para a tela de resumo
-- e a fila de solicitacoes ficarem com mais movimento na demonstracao.
-- Para aplicar, abra o arquivo banco/maonaroda.db em um cliente SQLite e execute.

INSERT INTO usuario (nome, email, senha, telefone, data_cadastro, status, tipo)
    VALUES ('Luciana Barros', 'luciana@email.com', '123', '48999770077', '2026-10-02', 'ATIVO', 'CLIENTE');
INSERT INTO cliente (id_usuario, cpf, data_nascimento)
    VALUES ((SELECT id FROM usuario WHERE email = 'luciana@email.com'), '321.654.987-00', '1996-07-21');

INSERT INTO usuario (nome, email, senha, telefone, data_cadastro, status, tipo)
    VALUES ('Marcos Tavares', 'marcos@email.com', '123', '48999880088', '2026-09-05', 'ATIVO', 'PRESTADOR');
INSERT INTO prestador (id_usuario, cpf_cnpj, descricao_profissional, raio_atendimento_km, nota_media, verificado)
    VALUES ((SELECT id FROM usuario WHERE email = 'marcos@email.com'), '12.345.678/0001-90',
            'Diarista e limpeza pos-obra', 25, 4.9, 1);

INSERT INTO servico (titulo, descricao, preco_base, tempo_estimado_horas, status, id_prestador, id_categoria)
    VALUES ('Limpeza pos-obra', 'Limpeza pesada apos reforma, com remocao de residuos', 350.0, 6, 'ATIVO',
            (SELECT id FROM usuario WHERE email = 'marcos@email.com'), 3);

INSERT INTO servico (titulo, descricao, preco_base, tempo_estimado_horas, status, id_prestador, id_categoria)
    VALUES ('Pintura de parede', 'Pintura de ambiente interno, duas demaos', 400.0, 8, 'ATIVO',
            (SELECT id FROM usuario WHERE email = 'ana@email.com'), 4);

INSERT INTO solicitacao_servico (data_solicitacao, descricao_necessidade, status, valor_final, id_cliente, id_servico)
    VALUES ('2026-11-20', 'Apartamento recem reformado precisa de limpeza completa', 'PENDENTE', 0,
            (SELECT id FROM usuario WHERE email = 'luciana@email.com'),
            (SELECT id FROM servico WHERE titulo = 'Limpeza pos-obra'));

INSERT INTO solicitacao_servico (data_solicitacao, descricao_necessidade, status, valor_final, id_cliente, id_servico)
    VALUES ('2026-11-10', 'Quarto de casal para pintar, parede com infiltracao ja corrigida', 'CONCLUIDO', 420.0,
            (SELECT id FROM usuario WHERE email = 'joao@email.com'),
            (SELECT id FROM servico WHERE titulo = 'Pintura de parede'));

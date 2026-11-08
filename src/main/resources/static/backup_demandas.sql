-- Arquivo de Backup - DemandaTech (Infraestrutura e Dados de Teste)

-- ==========================================
-- DDL - INFRAESTRUTURA
-- ==========================================
CREATE SCHEMA IF NOT EXISTS demanda_tech;

-- Sequências
CREATE SEQUENCE IF NOT EXISTS demanda_tech.departamentos_id_departamento_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS demanda_tech.funcionarios_matricula_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS demanda_tech.tipos_de_demanda_id_tipo_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS demanda_tech.demandas_id_demanda_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS demanda_tech.estorno_demanda_id_estorno_seq START WITH 1 INCREMENT BY 1;

-- Criação de Tabelas sem FK para evitar problemas de dependência circular imediata
CREATE TABLE IF NOT EXISTS demanda_tech.funcionarios (
    matricula BIGINT PRIMARY KEY DEFAULT nextval('demanda_tech.funcionarios_matricula_seq'),
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(60) NOT NULL,
    admin BOOLEAN NOT NULL DEFAULT false,
    id_departamento BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS demanda_tech.departamentos (
    id_departamento BIGINT PRIMARY KEY DEFAULT nextval('demanda_tech.departamentos_id_departamento_seq'),
    descricao VARCHAR(50) NOT NULL,
    gerente BIGINT REFERENCES demanda_tech.funcionarios(matricula)
);

-- Adicionando FK entre funcionário e departamento agora que ambas existem
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.table_constraints WHERE constraint_name='fk_func_depto' AND table_name='funcionarios' AND table_schema='demanda_tech') THEN
ALTER TABLE demanda_tech.funcionarios ADD CONSTRAINT fk_func_depto FOREIGN KEY (id_departamento) REFERENCES demanda_tech.departamentos(id_departamento);
END IF;
END $$;

CREATE TABLE IF NOT EXISTS demanda_tech.tipos_de_demanda (
    id_tipo BIGINT PRIMARY KEY DEFAULT nextval('demanda_tech.tipos_de_demanda_id_tipo_seq'),
    descricao       VARCHAR(50) NOT NULL,
    id_departamento BIGINT      NOT NULL REFERENCES demanda_tech.departamentos(id_departamento)
);

CREATE TABLE IF NOT EXISTS demanda_tech.demandas (
    id_demanda  BIGINT PRIMARY KEY DEFAULT nextval('demanda_tech.demandas_id_demanda_seq'),
    titulo      VARCHAR(30)  NOT NULL,
    data        DATE         NOT NULL,
    hora        TIME         NOT NULL,
    descricao   VARCHAR(250),
    prioridade  VARCHAR(20)  NOT NULL DEFAULT 'BAIXA' CHECK (prioridade IN ('BAIXA', 'MEDIA', 'ALTA', 'URGENTE')),
    id_tipo     BIGINT NOT NULL REFERENCES demanda_tech.tipos_de_demanda(id_tipo),
    responsavel BIGINT REFERENCES demanda_tech.funcionarios(matricula),
    criador     BIGINT NOT NULL REFERENCES demanda_tech.funcionarios(matricula),
    status      VARCHAR(20)  NOT NULL CHECK (status IN ('ABERTA', 'AGUARDANDO', 'RESOLVIDA', 'CANCELADA'))
);

CREATE TABLE IF NOT EXISTS demanda_tech.estorno_demanda (
    id_estorno BIGINT PRIMARY KEY DEFAULT nextval('demanda_tech.estorno_demanda_id_estorno_seq'),
    descricao  VARCHAR(250) NOT NULL,
    data       DATE         NOT NULL,
    id_demanda BIGINT       NOT NULL REFERENCES demanda_tech.demandas(id_demanda)
);

CREATE TABLE IF NOT EXISTS demanda_tech.funcionarios_envolvidos (
    matricula_funcionario BIGINT NOT NULL REFERENCES demanda_tech.funcionarios(matricula),
    id_demanda            BIGINT NOT NULL REFERENCES demanda_tech.demandas(id_demanda),
    PRIMARY KEY (matricula_funcionario, id_demanda)
);

-- Limpeza da base para inserção de testes limpos
TRUNCATE TABLE demanda_tech.funcionarios_envolvidos CASCADE;
TRUNCATE TABLE demanda_tech.estorno_demanda CASCADE;
TRUNCATE TABLE demanda_tech.demandas CASCADE;
TRUNCATE TABLE demanda_tech.tipos_de_demanda CASCADE;
ALTER TABLE demanda_tech.funcionarios DROP CONSTRAINT IF EXISTS fk_func_depto;
TRUNCATE TABLE demanda_tech.departamentos CASCADE;
TRUNCATE TABLE demanda_tech.funcionarios CASCADE;
ALTER TABLE demanda_tech.funcionarios ADD CONSTRAINT fk_func_depto FOREIGN KEY (id_departamento) REFERENCES demanda_tech.departamentos(id_departamento);

-- ==========================================
-- DML - DADOS DE TESTE
-- ==========================================

-- 4. Departamentos
INSERT INTO demanda_tech.departamentos (id_departamento, descricao) VALUES
(1, 'Tecnologia da Informação'),
(2, 'Recursos Humanos'),
(3, 'Financeiro');

-- 3. Funcionários (sem departamento inicialmente para evitar conflito FK)
INSERT INTO demanda_tech.funcionarios (matricula, nome, email, senha, id_departamento, admin) VALUES
(1,  'Admin Sistema',    'admin@demandatech.com.br',         'admin123',    1, true),
(2,  'João Silva',       'joao.silva@demandatech.com.br',    'joao123',     1, false),
(3,  'Maria Souza',      'maria.souza@demandatech.com.br',   'maria123',    1, false),
(4,  'Rebeca admin',     'rebeca.admin@demandatech.com.br',  'rebeca123',   2, true),
(5,  'Rebeca',           'rebeca@demandatech.com.br',        'rebeca123',   2, false),
(6,  'Carlos Ferreira',  'carlos.f@demandatech.com.br',      'carlos123',   3, false),
(7,  'Fernanda Costa',   'fernanda.c@demandatech.com.br',    'fernanda123', 1, false),
(8,  'Rafael Mendes',    'rafael.m@demandatech.com.br',      'rafael123',   1, false),
(9,  'Lucia Alves',      'lucia.a@demandatech.com.br',       'lucia123',    2, false);

UPDATE demanda_tech.departamentos SET id_departamento = 1 WHERE gerente = 1;
UPDATE demanda_tech.departamentos SET id_departamento = 2 WHERE gerente = 4;
UPDATE demanda_tech.departamentos SET id_departamento = 3 WHERE gerente = 3;

-- 5. Tipos de Demanda
INSERT INTO demanda_tech.tipos_de_demanda (id_tipo, descricao, id_departamento) VALUES
(1, 'Manutenção de Computador', 1),
(2, 'Acesso a Sistemas',        1),
(3, 'Dúvida Pagamento',         3),
(4, 'Contratação',              2),
(5, 'Suporte de Rede',          1),
(6, 'Reembolso de Despesa',     3);

-- 6. Demandas (33 registros)
INSERT INTO demanda_tech.demandas (id_demanda, titulo, data, hora, descricao, prioridade, id_tipo, responsavel, criador, status) VALUES
(1,  'Computador não liga',      '2026-04-20', '08:30:00', 'Meu PC está com tela preta desde ontem.',              'ALTA', 1, 1, 3, 'RESOLVIDA'),
(2,  'Acesso ao ERP',            '2026-04-20', '09:15:00', 'Preciso de acesso ao sistema de pagamentos.',          'MEDIA', 2, 2, 4, 'ABERTA'),
(3,  'Contratar Estagiário',     '2026-04-19', '14:00:00', 'Precisamos de um estagiário para TI.',                 'MEDIA', 4, 4, 1, 'CANCELADA'),
(4,  'Troca de teclado',         '2026-04-18', '10:00:00', 'Teclado com teclas travadas.',                         'BAIXA', 1, 2, 3, 'RESOLVIDA'),
(5,  'Reset de senha',           '2026-04-18', '10:45:00', 'Usuário bloqueado no sistema.',                        'ALTA', 2, 1, 6, 'ABERTA'),
(6,  'Problema no monitor',      '2026-04-17', '11:00:00', 'Monitor piscando constantemente.',                     'MEDIA', 1, 7, 2, 'ABERTA'),
(7,  'Impressora sem toner',     '2026-04-17', '13:30:00', 'Impressora do andar 2 sem toner.',                     'BAIXA', 1, 8, 5, 'RESOLVIDA'),
(8,  'Acesso ao Slack',          '2026-04-16', '09:00:00', 'Não consigo entrar no canal corporativo.',             'MEDIA', 2, 1, 9, 'ABERTA'),
(9,  'Fechar conta bancária',    '2026-04-16', '14:30:00', 'Conta corrente precisa ser encerrada.',                'ALTA', 3, 3, 6, 'CANCELADA'),
(10, 'Pagamento duplicado',      '2026-04-15', '15:00:00', 'Fornecedor foi pago duas vezes em março.',             'URGENTE', 3, 6, 3, 'ABERTA'),
(11, 'Vaga para Analista',       '2026-04-15', '16:00:00', 'Abrir vaga para analista de sistemas.',                'MEDIA', 4, 4, 1, 'ABERTA'),
(12, 'Notebook lento',           '2026-04-14', '08:00:00', 'Notebook demora 10 min para iniciar.',                 'MEDIA', 1, 2, 7, 'ABERTA'),
(13, 'Instalar VS Code',         '2026-04-14', '09:30:00', 'Preciso do editor instalado.',                         'BAIXA', 2, 1, 8, 'RESOLVIDA'),
(14, 'Configurar VPN',           '2026-04-13', '10:00:00', 'VPN não conecta em home office.',                      'ALTA', 5, 7, 2, 'ABERTA'),
(15, 'Suporte Wi-Fi sala 3',     '2026-04-13', '11:00:00', 'Sinal fraco na sala de reunião 3.',                    'MEDIA', 5, 8, 3, 'RESOLVIDA'),
(16, 'Erro no boleto',           '2026-04-12', '14:00:00', 'Valor do boleto gerado incorretamente.',               'URGENTE', 3, 3, 6, 'ABERTA'),
(17, 'Reembolso viagem SP',      '2026-04-12', '15:30:00', 'Despesas de viagem a São Paulo em março.',             'MEDIA', 6, 6, 5, 'ABERTA'),
(18, 'Selecionar candidato',     '2026-04-11', '09:00:00', 'Triagem de currículos para vaga de RH.',               'BAIXA', 4, 9, 4, 'RESOLVIDA'),
(19, 'Troca de fonte PC',        '2026-04-11', '10:00:00', 'Fonte queimada na estação 07.',                        'ALTA', 1, 2, 7, 'CANCELADA'),
(20, 'Acesso ao GitLab',         '2026-04-10', '11:00:00', 'Novo dev precisa de acesso ao repositório.',           'MEDIA', 2, 1, 1, 'RESOLVIDA'),
(21, 'Problema no roteador',     '2026-04-10', '13:00:00', 'Roteador do andar 3 reiniciando sozinho.',             'URGENTE', 5, 8, 2, 'ABERTA'),
(22, 'Conciliação bancária',     '2026-04-09', '14:00:00', 'Diferença de R$500 na conciliação de março.',          'ALTA', 3, 3, 6, 'ABERTA'),
(23, 'Reembolso home office',    '2026-04-09', '15:00:00', 'Reembolso de internet para trabalho remoto.',          'BAIXA', 6, 6, 9, 'RESOLVIDA'),
(24, 'Demissão voluntária',      '2026-04-08', '09:00:00', 'Processar pedido de demissão funcionário mat. 6.',     'ALTA', 4, 4, 5, 'ABERTA'),
(25, 'Mouse quebrado',           '2026-04-08', '10:30:00', 'Mouse da recepção não funciona.',                      'BAIXA', 1, 7, 3, 'RESOLVIDA'),
(26, 'Acesso ao Jira',           '2026-04-07', '11:00:00', 'Permissão para criar projetos no Jira.',               'MEDIA', 2, 2, 8, 'ABERTA'),
(27, 'Switch com defeito',       '2026-04-07', '13:30:00', 'Switch do rack principal piscando em laranja.',        'URGENTE', 5, 1, 7, 'ABERTA'),
(28, 'NF com valor errado',      '2026-04-06', '14:00:00', 'Nota fiscal emitida com CNPJ incorreto.',              'URGENTE', 3, 6, 3, 'CANCELADA'),
(29, 'Reembolso congresso',      '2026-04-06', '15:00:00', 'Inscrição no congresso de TI 2026.',                   'MEDIA', 6, 3, 2, 'ABERTA'),
(30, 'Vaga para Designer',       '2026-04-05', '09:00:00', 'Abertura de vaga para designer gráfico.',              'BAIXA', 4, 9, 4, 'RESOLVIDA'),
(31, 'HD cheio servidor',        '2026-04-05', '10:00:00', 'Servidor de arquivos com 98% de uso.',                 'URGENTE', 5, 8, 1, 'ABERTA'),
(32, 'Imposto retido errado',    '2026-04-04', '14:00:00', 'IR retido na fonte com alíquota incorreta.',           'ALTA', 3, 3, 6, 'ABERTA'),
(33, 'Backup não executou',      '2026-04-04', '08:00:00', 'Job de backup falhou na madrugada de sexta.',          'URGENTE', 5, 7, 1, 'ABERTA');

-- 7. Funcionários Envolvidos (14 registros)
INSERT INTO demanda_tech.funcionarios_envolvidos (matricula_funcionario, id_demanda) VALUES
(1, 1),  (2, 1),
(4, 3),  (1, 3),
(3, 10), (6, 10),
(7, 14), (8, 14),
(1, 21), (8, 21),
(3, 22), (6, 22),
(1, 27), (7, 31);

-- 8. Estornos (6 registros)
INSERT INTO demanda_tech.estorno_demanda (id_estorno, descricao, data, id_demanda) VALUES
(1, 'Acesso negado pelo gestor',          '2026-04-20', 2),
(2, 'CNPJ correto, ação indevida',        '2026-04-07', 28),
(3, 'Conta já havia sido encerrada',      '2026-04-16', 9),
(4, 'Fornecedor confirmou estorno duplo', '2026-04-15', 10),
(5, 'Fonte substituída, problema persiste','2026-04-12', 19),
(6, 'Backup foi executado manualmente',   '2026-04-05', 33);

-- Atualizando sequências para evitar conflitos ao adicionar novos registros pelo Java
SELECT setval('demanda_tech.departamentos_id_departamento_seq', 3);
SELECT setval('demanda_tech.funcionarios_matricula_seq', 9);
SELECT setval('demanda_tech.tipos_de_demanda_id_tipo_seq', 6);
SELECT setval('demanda_tech.demandas_id_demanda_seq', 33);
SELECT setval('demanda_tech.estorno_demanda_id_estorno_seq', 6);
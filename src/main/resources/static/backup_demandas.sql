-- Arquivo de Backup - DemandaTech (Infraestrutura e Dados de Teste)

-- ==========================================
-- DDL - INFRAESTRUTURA
-- ==========================================
CREATE SCHEMA IF NOT EXISTS demanda_tech;

-- Sequências
CREATE SEQUENCE IF NOT EXISTS demanda_tech.status_id_status_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS demanda_tech.prioridades_id_prioridade_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS demanda_tech.departamentos_id_departamento_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS demanda_tech.funcionarios_matricula_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS demanda_tech.tipos_de_demanda_id_tipo_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS demanda_tech.demandas_id_demanda_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS demanda_tech.estorno_demanda_id_estorno_seq START WITH 1 INCREMENT BY 1;

-- Criação de Tabelas sem FK para evitar problemas de dependência circular imediata
CREATE TABLE IF NOT EXISTS demanda_tech.status (
    id_status BIGINT PRIMARY KEY DEFAULT nextval('demanda_tech.status_id_status_seq'),
    descricao VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS demanda_tech.prioridades (
    id_prioridade BIGINT PRIMARY KEY DEFAULT nextval('demanda_tech.prioridades_id_prioridade_seq'),
    descricao VARCHAR(30) NOT NULL
);

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
    id_prioridade BIGINT NOT NULL DEFAULT 1 REFERENCES demanda_tech.prioridades(id_prioridade),
    id_tipo     BIGINT NOT NULL REFERENCES demanda_tech.tipos_de_demanda(id_tipo),
    responsavel BIGINT REFERENCES demanda_tech.funcionarios(matricula),
    criador     BIGINT NOT NULL REFERENCES demanda_tech.funcionarios(matricula),
    id_status   BIGINT NOT NULL REFERENCES demanda_tech.status(id_status)
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
TRUNCATE TABLE demanda_tech.prioridades CASCADE;
TRUNCATE TABLE demanda_tech.status CASCADE;

-- ==========================================
-- DML - DADOS DE TESTE
-- ==========================================

-- 1. Status
INSERT INTO demanda_tech.status (id_status, descricao) VALUES
(1, 'Ativo'), (2, 'Resolvido'), (3, 'Cancelado');

-- 2. Prioridades
INSERT INTO demanda_tech.prioridades (id_prioridade, descricao) VALUES
(1, 'Baixa'), (2, 'Média'), (3, 'Alta'), (4, 'Urgente');

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
INSERT INTO demanda_tech.demandas (id_demanda, titulo, data, hora, descricao, id_prioridade, id_tipo, responsavel, criador, id_status) VALUES
(1,  'Computador não liga',      '2026-04-20', '08:30:00', 'Meu PC está com tela preta desde ontem.',              3, 1, 1, 3, 2),
(2,  'Acesso ao ERP',            '2026-04-20', '09:15:00', 'Preciso de acesso ao sistema de pagamentos.',          2, 2, 2, 4, 1),
(3,  'Contratar Estagiário',     '2026-04-19', '14:00:00', 'Precisamos de um estagiário para TI.',                 2, 4, 4, 1, 3),
(4,  'Troca de teclado',         '2026-04-18', '10:00:00', 'Teclado com teclas travadas.',                         1, 1, 2, 3, 2),
(5,  'Reset de senha',           '2026-04-18', '10:45:00', 'Usuário bloqueado no sistema.',                        3, 2, 1, 6, 1),
(6,  'Problema no monitor',      '2026-04-17', '11:00:00', 'Monitor piscando constantemente.',                     2, 1, 7, 2, 1),
(7,  'Impressora sem toner',     '2026-04-17', '13:30:00', 'Impressora do andar 2 sem toner.',                     1, 1, 8, 5, 2),
(8,  'Acesso ao Slack',          '2026-04-16', '09:00:00', 'Não consigo entrar no canal corporativo.',             2, 2, 1, 9, 1),
(9,  'Fechar conta bancária',    '2026-04-16', '14:30:00', 'Conta corrente precisa ser encerrada.',                3, 3, 3, 6, 3),
(10, 'Pagamento duplicado',      '2026-04-15', '15:00:00', 'Fornecedor foi pago duas vezes em março.',             4, 3, 6, 3, 1),
(11, 'Vaga para Analista',       '2026-04-15', '16:00:00', 'Abrir vaga para analista de sistemas.',                2, 4, 4, 1, 1),
(12, 'Notebook lento',           '2026-04-14', '08:00:00', 'Notebook demora 10 min para iniciar.',                 2, 1, 2, 7, 1),
(13, 'Instalar VS Code',         '2026-04-14', '09:30:00', 'Preciso do editor instalado.',                         1, 2, 1, 8, 2),
(14, 'Configurar VPN',           '2026-04-13', '10:00:00', 'VPN não conecta em home office.',                      3, 5, 7, 2, 1),
(15, 'Suporte Wi-Fi sala 3',     '2026-04-13', '11:00:00', 'Sinal fraco na sala de reunião 3.',                    2, 5, 8, 3, 2),
(16, 'Erro no boleto',           '2026-04-12', '14:00:00', 'Valor do boleto gerado incorretamente.',               4, 3, 3, 6, 1),
(17, 'Reembolso viagem SP',      '2026-04-12', '15:30:00', 'Despesas de viagem a São Paulo em março.',             2, 6, 6, 5, 1),
(18, 'Selecionar candidato',     '2026-04-11', '09:00:00', 'Triagem de currículos para vaga de RH.',               1, 4, 9, 4, 2),
(19, 'Troca de fonte PC',        '2026-04-11', '10:00:00', 'Fonte queimada na estação 07.',                        3, 1, 2, 7, 3),
(20, 'Acesso ao GitLab',         '2026-04-10', '11:00:00', 'Novo dev precisa de acesso ao repositório.',           2, 2, 1, 1, 2),
(21, 'Problema no roteador',     '2026-04-10', '13:00:00', 'Roteador do andar 3 reiniciando sozinho.',             4, 5, 8, 2, 1),
(22, 'Conciliação bancária',     '2026-04-09', '14:00:00', 'Diferença de R$500 na conciliação de março.',          3, 3, 3, 6, 1),
(23, 'Reembolso home office',    '2026-04-09', '15:00:00', 'Reembolso de internet para trabalho remoto.',          1, 6, 6, 9, 2),
(24, 'Demissão voluntária',      '2026-04-08', '09:00:00', 'Processar pedido de demissão funcionário mat. 6.',     3, 4, 4, 5, 1),
(25, 'Mouse quebrado',           '2026-04-08', '10:30:00', 'Mouse da recepção não funciona.',                      1, 1, 7, 3, 2),
(26, 'Acesso ao Jira',           '2026-04-07', '11:00:00', 'Permissão para criar projetos no Jira.',               2, 2, 2, 8, 1),
(27, 'Switch com defeito',       '2026-04-07', '13:30:00', 'Switch do rack principal piscando em laranja.',        4, 5, 1, 7, 1),
(28, 'NF com valor errado',      '2026-04-06', '14:00:00', 'Nota fiscal emitida com CNPJ incorreto.',              4, 3, 6, 3, 3),
(29, 'Reembolso congresso',      '2026-04-06', '15:00:00', 'Inscrição no congresso de TI 2026.',                   2, 6, 3, 2, 1),
(30, 'Vaga para Designer',       '2026-04-05', '09:00:00', 'Abertura de vaga para designer gráfico.',              1, 4, 9, 4, 2),
(31, 'HD cheio servidor',        '2026-04-05', '10:00:00', 'Servidor de arquivos com 98% de uso.',                 4, 5, 8, 1, 1),
(32, 'Imposto retido errado',    '2026-04-04', '14:00:00', 'IR retido na fonte com alíquota incorreta.',           3, 3, 3, 6, 1),
(33, 'Backup não executou',      '2026-04-04', '08:00:00', 'Job de backup falhou na madrugada de sexta.',          4, 5, 7, 1, 1);

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
SELECT setval('demanda_tech.status_id_status_seq', 3);
SELECT setval('demanda_tech.prioridades_id_prioridade_seq', 4);
SELECT setval('demanda_tech.departamentos_id_departamento_seq', 3);
SELECT setval('demanda_tech.funcionarios_matricula_seq', 9);
SELECT setval('demanda_tech.tipos_de_demanda_id_tipo_seq', 6);
SELECT setval('demanda_tech.demandas_id_demanda_seq', 33);
SELECT setval('demanda_tech.estorno_demanda_id_estorno_seq', 6);